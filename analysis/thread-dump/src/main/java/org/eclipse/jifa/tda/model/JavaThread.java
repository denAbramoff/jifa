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

import org.eclipse.jifa.tda.enums.JavaThreadState;

import java.util.Objects;

public class JavaThread extends Thread
{
    private long jid;

    private boolean daemon;

    private int priority;

    private long lastJavaSP;

    private JavaThreadState javaThreadState;

    private Trace trace;

    public long getJid()
    {
        return jid;
    }

    public void setJid(long jid)
    {
        this.jid = jid;
    }

    public boolean isDaemon()
    {
        return daemon;
    }

    public void setDaemon(boolean daemon)
    {
        this.daemon = daemon;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public long getLastJavaSP()
    {
        return lastJavaSP;
    }

    public void setLastJavaSP(long lastJavaSP)
    {
        this.lastJavaSP = lastJavaSP;
    }

    public JavaThreadState getJavaThreadState()
    {
        return javaThreadState;
    }

    public void setJavaThreadState(JavaThreadState javaThreadState)
    {
        this.javaThreadState = javaThreadState;
    }

    public Trace getTrace()
    {
        return trace;
    }

    public void setTrace(Trace trace)
    {
        this.trace = trace;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        JavaThread that = (JavaThread)o;
        return jid == that.jid && daemon == that.daemon && priority == that.priority && lastJavaSP == that.lastJavaSP &&
                javaThreadState == that.javaThreadState && Objects.equals(trace, that.trace);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), jid, daemon, priority, lastJavaSP, javaThreadState, trace);
    }
}
