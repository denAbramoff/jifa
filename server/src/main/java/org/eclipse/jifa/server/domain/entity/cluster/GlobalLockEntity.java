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
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.eclipse.jifa.server.domain.entity.shared.BaseEntity;

import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "global_locks")
@Entity
public class GlobalLockEntity extends BaseEntity
{
    @Column(unique = true, nullable = false, updatable = false, length = 64)
    private String uniqueName;

    @Column(nullable = false)
    private LocalDateTime expiryAt;

    public String getUniqueName()
    {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName)
    {
        this.uniqueName = uniqueName;
    }

    public LocalDateTime getExpiryAt()
    {
        return expiryAt;
    }

    public void setExpiryAt(LocalDateTime expiryAt)
    {
        this.expiryAt = expiryAt;
    }
}
