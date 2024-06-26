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

package org.eclipse.jifa.tda.model;

import java.util.Objects;

public class RawMonitor extends Identity
{
    private long address = -1;

    private boolean classInstance;

    private String clazz;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RawMonitor that = (RawMonitor)o;
        return address == that.address && classInstance == that.classInstance &&
                Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(address, classInstance, clazz);
    }
}

