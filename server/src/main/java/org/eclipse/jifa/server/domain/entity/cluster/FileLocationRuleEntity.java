/********************************************************************************
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.eclipse.jifa.server.domain.entity.shared.BaseEntity;
import org.eclipse.jifa.server.domain.entity.shared.user.UserEntity;
import org.eclipse.jifa.server.enums.FileLocationRule;
import org.eclipse.jifa.server.enums.FileType;

@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "file_location_rules",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "fileType" }) })
@Entity
public class FileLocationRuleEntity extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileLocationRule rule;

    private String label;

    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(UserEntity user)
    {
        this.user = user;
    }

    public FileType getFileType()
    {
        return fileType;
    }

    public void setFileType(FileType fileType)
    {
        this.fileType = fileType;
    }

    public FileLocationRule getRule()
    {
        return rule;
    }

    public void setRule(FileLocationRule rule)
    {
        this.rule = rule;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }
}
