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
package org.eclipse.jifa.jfr.model.jfr;

import org.openjdk.jmc.common.IMCThread;
import org.openjdk.jmc.common.unit.IQuantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class RecordedThread
{
    private static final Logger LOG = LoggerFactory.getLogger(RecordedThread.class);
    private long javaThreadId;
    private String javaName;
    private long osThreadId;

    public RecordedThread(String javaName, long javaThreadId, long osThreadId)
    {
        this.javaName = javaName;
        this.javaThreadId = javaThreadId;
        this.osThreadId = osThreadId;
    }

    public RecordedThread(IMCThread imcThread)
    {
        this.javaThreadId = imcThread.getThreadId();
        this.javaName = imcThread.getThreadName();
        try
        {
            Field f = imcThread.getClass().getDeclaredField("osThreadId");
            f.setAccessible(true);
            Object value = f.get(imcThread);
            if (value instanceof IQuantity iQuantity)
            {
                this.osThreadId = iQuantity.longValue();
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
        }
        if (this.javaThreadId == 0 && this.osThreadId > 0)
        {
            this.javaThreadId = -this.osThreadId;
        }
    }

    public long getJavaThreadId()
    {
        return javaThreadId;
    }

    public void setJavaThreadId(long javaThreadId)
    {
        this.javaThreadId = javaThreadId;
    }

    public String getJavaName()
    {
        return javaName;
    }

    public void setJavaName(String javaName)
    {
        this.javaName = javaName;
    }

    public long getOsThreadId()
    {
        return osThreadId;
    }

    public void setOsThreadId(long osThreadId)
    {
        this.osThreadId = osThreadId;
    }

    public long getOSThreadId()
    {
        return osThreadId;
    }
}
