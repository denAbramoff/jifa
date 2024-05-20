/********************************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
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

package org.eclipse.jifa.tda.vo;

import com.google.gson.annotations.SerializedName;

import org.eclipse.jifa.tda.enums.MonitorState;

public class VMonitor
{
    private int id;

    private long address;

    private boolean classInstance;

    @SerializedName("class")
    private String clazz;

    private MonitorState state;

    public VMonitor(int id, long address, boolean classInstance, String clazz, MonitorState state)
    {
        this.id = id;
        this.address = address;
        this.classInstance = classInstance;
        this.clazz = clazz;
        this.state = state;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getAddress()
    {
        return address;
    }

    public void setAddress(long address)
    {
        this.address = address;
    }

    public boolean isClassInstance()
    {
        return classInstance;
    }

    public void setClassInstance(boolean classInstance)
    {
        this.classInstance = classInstance;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public MonitorState getState()
    {
        return state;
    }

    public void setState(MonitorState state)
    {
        this.state = state;
    }
}
