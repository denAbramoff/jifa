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
package org.eclipse.jifa.server.domain.entity.cluster;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.eclipse.jifa.server.domain.entity.shared.BaseEntity;

@MappedSuperclass

public class WorkerEntity extends BaseEntity
{
    @Column(length = 128)
    private String hostAddress;

    @Column(nullable = false)
    private int port;

    public String getHostAddress()
    {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress)
    {
        this.hostAddress = hostAddress;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
}
