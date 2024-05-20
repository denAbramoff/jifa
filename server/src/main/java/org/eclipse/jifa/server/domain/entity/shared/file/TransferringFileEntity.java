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
package org.eclipse.jifa.server.domain.entity.shared.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.eclipse.jifa.server.enums.FileTransferState;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "transferring_files")
@Entity
public class TransferringFileEntity extends BaseFileEntity
{
    public static final int MAX_FAILURE_MESSAGE_LENGTH = 1024;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileTransferState transferState;

    @Column(nullable = false)
    private long totalSize;

    @Column(nullable = false)
    private long transferredSize;

    @Column(length = MAX_FAILURE_MESSAGE_LENGTH)
    private String failureMessage;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastModifiedTime;

    public FileTransferState getTransferState()
    {
        return transferState;
    }

    public void setTransferState(FileTransferState transferState)
    {
        this.transferState = transferState;
    }

    public long getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(long totalSize)
    {
        this.totalSize = totalSize;
    }

    public long getTransferredSize()
    {
        return transferredSize;
    }

    public void setTransferredSize(long transferredSize)
    {
        this.transferredSize = transferredSize;
    }

    public String getFailureMessage()
    {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage)
    {
        this.failureMessage = failureMessage;
    }

    public LocalDateTime getLastModifiedTime()
    {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime)
    {
        this.lastModifiedTime = lastModifiedTime;
    }
}
