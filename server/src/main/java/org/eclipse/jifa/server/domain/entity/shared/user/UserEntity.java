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
package org.eclipse.jifa.server.domain.entity.shared.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.eclipse.jifa.server.domain.entity.shared.BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "users")

public class UserEntity extends BaseEntity
{
    @Column(length = 64)
    private String name;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastModifiedTime;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
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
