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

import org.eclipse.jifa.tda.enums.OSTreadState;
import org.eclipse.jifa.tda.enums.ThreadType;

import java.util.Objects;

public class Thread extends Identity
{
    private String name;

    private int osPriority;

    // ms, optional
    // -1 means unknown
    private double cpu = -1;

    // ms, optional
    // -1 means unknown
    private double elapsed = -1;

    private long tid;

    private long nid;

    private OSTreadState osThreadState;

    private ThreadType type;

    private int lineStart;

    // include
    private int lineEnd;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOsPriority()
    {
        return osPriority;
    }

    public void setOsPriority(int osPriority)
    {
        this.osPriority = osPriority;
    }

    public double getCpu()
    {
        return cpu;
    }

    public void setCpu(double cpu)
    {
        this.cpu = cpu;
    }

    public double getElapsed()
    {
        return elapsed;
    }

    public void setElapsed(double elapsed)
    {
        this.elapsed = elapsed;
    }

    public long getTid()
    {
        return tid;
    }

    public void setTid(long tid)
    {
        this.tid = tid;
    }

    public long getNid()
    {
        return nid;
    }

    public void setNid(long nid)
    {
        this.nid = nid;
    }

    public OSTreadState getOsThreadState()
    {
        return osThreadState;
    }

    public void setOsThreadState(OSTreadState osThreadState)
    {
        this.osThreadState = osThreadState;
    }

    public ThreadType getType()
    {
        return type;
    }

    public void setType(ThreadType type)
    {
        this.type = type;
    }

    public int getLineStart()
    {
        return lineStart;
    }

    public void setLineStart(int lineStart)
    {
        this.lineStart = lineStart;
    }

    public int getLineEnd()
    {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd)
    {
        this.lineEnd = lineEnd;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Thread thread = (Thread)o;
        return osPriority == thread.osPriority && Double.compare(thread.cpu, cpu) == 0 &&
                Double.compare(thread.elapsed, elapsed) == 0 && tid == thread.tid && nid == thread.nid &&
                lineStart == thread.lineStart && lineEnd == thread.lineEnd && Objects.equals(name, thread.name) &&
                osThreadState == thread.osThreadState && type == thread.type;
    }

    @Override
    public int hashCode()
    {
        return Objects
                .hash(name, osPriority, cpu, elapsed, tid, nid, osThreadState, type, lineStart, lineEnd);
    }
}
