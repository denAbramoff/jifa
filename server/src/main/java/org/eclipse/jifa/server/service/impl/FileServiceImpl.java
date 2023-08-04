/********************************************************************************
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.jifa.server.service.impl;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jifa.common.domain.vo.PageView;
import org.eclipse.jifa.common.util.Validate;
import org.eclipse.jifa.server.ConfigurationAccessor;
import org.eclipse.jifa.server.component.CurrentStaticWorker;
import org.eclipse.jifa.server.domain.converter.EntityConverter;
import org.eclipse.jifa.server.domain.converter.FileViewConverter;
import org.eclipse.jifa.server.domain.dto.FileTransferProgress;
import org.eclipse.jifa.server.domain.dto.FileTransferRequest;
import org.eclipse.jifa.server.domain.dto.FileView;
import org.eclipse.jifa.server.domain.dto.WrappedResource;
import org.eclipse.jifa.server.domain.entity.shared.file.DeletedFileEntity;
import org.eclipse.jifa.server.domain.entity.shared.file.FileEntity;
import org.eclipse.jifa.server.domain.entity.shared.file.TransferringFileEntity;
import org.eclipse.jifa.server.domain.entity.shared.user.UserEntity;
import org.eclipse.jifa.server.domain.entity.static_cluster.FileStaticWorkerBind;
import org.eclipse.jifa.server.domain.entity.static_cluster.StaticWorkerEntity;
import org.eclipse.jifa.server.enums.FileTransferState;
import org.eclipse.jifa.server.enums.FileType;
import org.eclipse.jifa.server.enums.SchedulingStrategy;
import org.eclipse.jifa.server.repository.DeletedFileRepo;
import org.eclipse.jifa.server.repository.FileRepo;
import org.eclipse.jifa.server.repository.FileStaticWorkerBindRepo;
import org.eclipse.jifa.server.repository.TransferringFileRepo;
import org.eclipse.jifa.server.service.FileService;
import org.eclipse.jifa.server.service.StorageService;
import org.eclipse.jifa.server.service.UserService;
import org.eclipse.jifa.server.service.WorkerService;
import org.eclipse.jifa.server.support.FileTransferListener;
import org.eclipse.jifa.server.util.FileTransferUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.eclipse.jifa.common.domain.exception.CommonException.CE;
import static org.eclipse.jifa.common.enums.CommonErrorCode.INTERNAL_ERROR;
import static org.eclipse.jifa.server.domain.dto.HttpRequestToWorker.createDeleteRequest;
import static org.eclipse.jifa.server.domain.dto.HttpRequestToWorker.createPostRequest;
import static org.eclipse.jifa.server.enums.Role.ELASTIC_WORKER;
import static org.eclipse.jifa.server.enums.Role.MASTER;
import static org.eclipse.jifa.server.enums.Role.STANDALONE_WORKER;
import static org.eclipse.jifa.server.enums.ServerErrorCode.ACCESS_DENIED;
import static org.eclipse.jifa.server.enums.ServerErrorCode.FILE_NOT_FOUND;
import static org.eclipse.jifa.server.enums.ServerErrorCode.FILE_TYPE_MISMATCH;
import static org.eclipse.jifa.server.enums.ServerErrorCode.UNAVAILABLE;

@Component
public class FileServiceImpl extends ConfigurationAccessor implements FileService {

    private final TransactionTemplate transactionTemplate;

    private final UserService userService;

    private final FileRepo fileRepo;

    private final TransferringFileRepo transferringFileRepo;

    private final DeletedFileRepo deletedFileRepo;

    private final CurrentStaticWorker currentStaticWorker;

    private final FileStaticWorkerBindRepo fileStaticWorkerBindRepo;

    private final StorageService storageService;

    private final WorkerService workerService;

    private final TaskScheduler taskScheduler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FileServiceImpl(TransactionTemplate transactionTemplate,
                           UserService userService,
                           FileRepo fileRepo,
                           TransferringFileRepo transferringFileRepo,
                           DeletedFileRepo deletedFileRepo,
                           @Nullable CurrentStaticWorker currentStaticWorker,
                           @Nullable FileStaticWorkerBindRepo fileStaticWorkerBindRepo,
                           @Nullable StorageService storageService,
                           @Nullable WorkerService workerService,
                           TaskScheduler taskScheduler) {
        this.transactionTemplate = transactionTemplate;
        this.userService = userService;
        this.fileRepo = fileRepo;
        this.transferringFileRepo = transferringFileRepo;
        this.deletedFileRepo = deletedFileRepo;
        this.currentStaticWorker = currentStaticWorker;
        this.fileStaticWorkerBindRepo = fileStaticWorkerBindRepo;
        this.storageService = storageService;
        this.workerService = workerService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public PageView<FileView> getUserFileViews(FileType type, int page, int pageSize) {
        mustBe(MASTER, STANDALONE_WORKER);

        Page<FileEntity> files;
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        files = fileRepo.findByUserIdOrderByCreatedTimeDesc(userService.getCurrentUserId(), pageRequest);
        List<FileView> fileViews = files.getContent().stream().map(FileViewConverter::convert).toList();
        return new PageView<>(page, pageSize, (int) files.getTotalElements(), fileViews);
    }

    @Override
    public FileView getFileViewById(long fileId) {
        mustBe(MASTER, STANDALONE_WORKER);

        return FileViewConverter.convert(getFileEntityByIdAndCheckAuthority(fileId));
    }

    @Override
    public void deleteById(long fileId) {
        mustNotBe(ELASTIC_WORKER);

        FileEntity file = getFileEntityByIdAndCheckAuthority(fileId);

        if (isMaster() && getSchedulingStrategy() == SchedulingStrategy.STATIC) {
            // forward the request to the static worker
            FileStaticWorkerBind bind = fileStaticWorkerBindRepo.findByFileId(file.getId()).orElseThrow(() -> CE(INTERNAL_ERROR));
            workerService.sendRequestAndBlock(bind.getStaticWorker(),
                                              createDeleteRequest("/files/" + fileId, null, Void.class));
            return;
        }
        assert storageService != null;

        doDelete(file);
    }

    @Override
    public FileEntity getFileByUniqueName(String uniqueName, FileType expectedFileType) {
        FileEntity file = getFileEntityByUniqueNameAndCheckAuthority(uniqueName);
        Validate.isTrue(expectedFileType == null || file.getType() == expectedFileType, FILE_TYPE_MISMATCH);
        return file;
    }

    @Override
    public long handleTransferRequest(FileTransferRequest request) {
        mustNotBe(ELASTIC_WORKER);

        if (isMaster() && getSchedulingStrategy() == SchedulingStrategy.STATIC) {
            StaticWorkerEntity worker = workerService.asStaticWorkerService().selectForFileTransferRequest(request);
            return workerService.sendRequestAndBlock(worker,
                                                     createPostRequest("/files/transfer", request, Long.class));
        }

        TransferringFileEntity transferringFile = new TransferringFileEntity();
        transferringFile.setUniqueName(generateFileUniqueName());
        transferringFile.setUser(userService.getCurrentUser());
        transferringFile.setOriginalName(FileTransferUtil.extractOriginalName(request));
        transferringFile.setType(request.getType());
        transferringFile.setTransferState(FileTransferState.IN_PROGRESS);
        transferringFileRepo.save(transferringFile);

        storageService.handleTransfer(request, transferringFile.getUniqueName(),
                                      new FileTransferListenerImpl(transferringFile));
        return transferringFile.getId();
    }

    @Override
    public FileTransferProgress getTransferProgress(long transferringFileId) {
        mustBe(MASTER, STANDALONE_WORKER);
        TransferringFileEntity transferringFile = transferringFileRepo.findById(transferringFileId).orElseThrow(() -> CE(UNAVAILABLE));
        return new FileTransferProgress(transferringFile.getTransferState(),
                                        transferringFile.getTotalSize(),
                                        transferringFile.getTransferredSize(),
                                        transferringFile.getFailureMessage());

    }

    @Override
    public void handleUploadRequest(FileType type, MultipartFile file) throws Throwable {
        mustNotBe(ELASTIC_WORKER);

        if (isMaster() && getSchedulingStrategy() == SchedulingStrategy.STATIC) {
            StaticWorkerEntity worker = workerService.asStaticWorkerService().selectForFileUpload(type, file);
            workerService.asStaticWorkerService().handleUploadRequest(worker, type, file);
            return;
        }

        String uniqueName = generateFileUniqueName();
        String originalName = file.getOriginalFilename();
        long size = storageService.handleUpload(type, file, uniqueName);

        FileStaticWorkerBind bind = isStaticWorker() ? new FileStaticWorkerBind() : null;
        if (bind != null) {
            bind.setStaticWorker(currentStaticWorker.getEntity());
        }

        transactionTemplate.executeWithoutResult(status -> {
            FileEntity newFile = new FileEntity();
            newFile.setUniqueName(uniqueName);
            newFile.setUser(userService.getCurrentUser());
            newFile.setOriginalName(originalName);
            newFile.setType(type);
            newFile.setSize(size);
            FileEntity savedFile = fileRepo.save(newFile);
            if (bind != null) {
                bind.setFile(savedFile);
                fileStaticWorkerBindRepo.save(bind);
            }
        });

    }

    @Override
    public WrappedResource handleDownloadRequest(long fileId, HttpServletResponse response) throws Throwable {
        mustNotBe(ELASTIC_WORKER);

        FileEntity file = getFileEntityByIdAndCheckAuthority(fileId);
        if (isMaster() && getSchedulingStrategy() == SchedulingStrategy.STATIC) {
            // forward the request to the static worker
            FileStaticWorkerBind bind = fileStaticWorkerBindRepo.findByFileId(file.getId()).orElseThrow(() -> CE(INTERNAL_ERROR));
            workerService.asStaticWorkerService().handleDownloadRequest(bind.getStaticWorker(), file.getId(), response);
            return new WrappedResource(null, null);
        }

        UrlResource resource = new UrlResource(storageService.locationOf(file.getType(), file.getUniqueName()).toUri());
        Validate.isTrue(resource.exists());
        return new WrappedResource(file.getOriginalName(), resource);
    }

    @Override
    public void deleteOldestFile() {
        fileRepo.findFirstByOrderByCreatedTimeDesc().ifPresent(this::doDelete);
    }

    private FileEntity getFileEntityByIdAndCheckAuthority(long id) {
        FileEntity file = fileRepo.findById(id).orElseThrow((() -> CE(FILE_NOT_FOUND)));
        checkAuthority(file);
        return file;
    }

    private FileEntity getFileEntityByUniqueNameAndCheckAuthority(String uniqueName) {
        FileEntity file = fileRepo.findByUniqueName(uniqueName).orElseThrow((() -> CE(FILE_NOT_FOUND)));
        checkAuthority(file);
        return file;
    }

    private void checkAuthority(FileEntity file) {
        UserEntity user = file.getUser();
        Validate.isTrue(user == null
                        || user.getId().equals(userService.getCurrentUserId())
                        || userService.isCurrentUserAdmin(),
                        ACCESS_DENIED);
    }

    private String generateFileUniqueName() {
        return UUID.randomUUID().toString();
    }

    private void doDelete(FileEntity file) {
        assert isWorker() || (isMaster() && getSchedulingStrategy() == SchedulingStrategy.STATIC);

        FileStaticWorkerBind staticBind = isStaticWorker() ? fileStaticWorkerBindRepo.findByFileId(file.getId()).orElseThrow(() -> CE(INTERNAL_ERROR)) : null;

        DeletedFileEntity deletedFile = EntityConverter.convert(file);
        transactionTemplate.executeWithoutResult(status -> {
            if (staticBind != null) {
                fileStaticWorkerBindRepo.deleteById(staticBind.getId());
            }
            fileRepo.deleteById(file.getId());
            deletedFileRepo.save(deletedFile);
        });

        storageService.scavenge(file.getType(), file.getUniqueName());
    }

    private class FileTransferListenerImpl implements FileTransferListener {

        private static final int DELETION_DELAY = 180;

        private TransferringFileEntity transferringFile;

        private volatile long transferredSize;

        private volatile boolean transferring;

        public FileTransferListenerImpl(TransferringFileEntity transferringFile) {
            this.transferringFile = transferringFile;
        }

        @Override
        public void onStart() {
            transferring = true;

            new Thread(() -> {
                long currentTransferredSize = 0;
                while (transferring) {
                    synchronized (this) {
                        if (!transferring) {
                            break;
                        }
                        if (currentTransferredSize < transferredSize) {
                            currentTransferredSize = transferredSize;
                            try {
                                transferringFile.setTransferredSize(currentTransferredSize);
                                transferringFile = transferringFileRepo.save(transferringFile);
                            } catch (Throwable ignored) {
                            }
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }, "TransferredSize Updater - " + transferringFile.getId()).start();
        }

        @Override
        public void fireTotalSize(long totalSize) {
            if (totalSize > 0) {
                transferringFile.setTotalSize(totalSize);
                transferringFile = transferringFileRepo.save(transferringFile);
            }
        }

        @Override
        public void fireTransferredSize(long transferredSize) {
            assert transferredSize >= this.transferredSize;
            this.transferredSize = transferredSize;
        }

        @Override
        public synchronized void onSuccess(long totalSize) {
            transferring = false;
            transferringFile.setTransferState(FileTransferState.SUCCESS);
            transferringFile.setTotalSize(totalSize);
            transferringFile.setTransferredSize(totalSize);

            FileEntity file = EntityConverter.convert(transferringFile);

            FileStaticWorkerBind bind = isStaticWorker() ? new FileStaticWorkerBind() : null;

            if (bind != null) {
                bind.setStaticWorker(currentStaticWorker.getEntity());
            }

            try {
                transactionTemplate.executeWithoutResult(status -> {
                    transferringFile = transferringFileRepo.save(transferringFile);
                    FileEntity savedFile = fileRepo.save(file);
                    if (bind != null) {
                        bind.setFile(savedFile);
                        fileStaticWorkerBindRepo.save(bind);
                    }
                });
            } finally {
                taskScheduler.schedule(() -> transferringFileRepo.deleteById(transferringFile.getId()),
                                       Instant.now().plusSeconds(DELETION_DELAY));
            }
        }

        @Override
        public synchronized void onError(Throwable t) {
            transferring = false;

            transferringFile.setTransferState(FileTransferState.FAILURE);
            String failureMessage = t.getMessage();
            if (failureMessage.length() > TransferringFileEntity.MAX_FAILURE_MESSAGE_LENGTH) {
                failureMessage = failureMessage.substring(0, TransferringFileEntity.MAX_FAILURE_MESSAGE_LENGTH);
            }
            transferringFile.setFailureMessage(failureMessage);
            try {
                transferringFile = transferringFileRepo.save(transferringFile);
            } finally {
                taskScheduler.schedule(() -> transferringFileRepo.deleteById(transferringFile.getId()),
                                       Instant.now().plusSeconds(DELETION_DELAY));
            }
        }
    }
}