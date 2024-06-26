/********************************************************************************
 * Copyright (c) 2023, 2024 Contributors to the Eclipse Foundation
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
package org.eclipse.jifa.server.domain.entity.cluster;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import org.eclipse.jifa.server.domain.entity.shared.BaseEntity;
import org.eclipse.jifa.server.domain.entity.shared.file.FileEntity;

@Entity(name = "file_static_worker_binds")
public class FileStaticWorkerBindEntity extends BaseEntity
{
    @OneToOne
    @MapsId
    private FileEntity file;

    @ManyToOne(optional = false)
    private StaticWorkerEntity staticWorker;

    public FileEntity getFile()
    {
        return file;
    }

    public void setFile(FileEntity file)
    {
        this.file = file;
    }

    public StaticWorkerEntity getStaticWorker()
    {
        return staticWorker;
    }

    public void setStaticWorker(StaticWorkerEntity staticWorker)
    {
        this.staticWorker = staticWorker;
    }
}
