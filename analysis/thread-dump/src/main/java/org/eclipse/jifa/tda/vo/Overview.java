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

import org.eclipse.jifa.tda.enums.JavaThreadState;
import org.eclipse.jifa.tda.enums.OSTreadState;

import java.util.HashMap;
import java.util.Map;

public class Overview
{
    private long timestamp;

    private String vmInfo;

    private int jniRefs;

    private int jniWeakRefs;

    private int deadLockCount;

    private int errorCount;

    private ThreadStat threadStat = new ThreadStat();

    private JavaThreadStat javaThreadStat = new JavaThreadStat();

    private ThreadStat jitThreadStat = new ThreadStat();

    private ThreadStat gcThreadStat = new ThreadStat();

    private ThreadStat otherThreadStat = new ThreadStat();

    private Map<String, ThreadStat> threadGroupStat = new HashMap<>();

    private final OSTreadState[] states = OSTreadState.values();

    private final JavaThreadState[] javaStates = JavaThreadState.values();

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getVmInfo()
    {
        return vmInfo;
    }

    public void setVmInfo(String vmInfo)
    {
        this.vmInfo = vmInfo;
    }

    public int getJniRefs()
    {
        return jniRefs;
    }

    public void setJniRefs(int jniRefs)
    {
        this.jniRefs = jniRefs;
    }

    public int getJniWeakRefs()
    {
        return jniWeakRefs;
    }

    public void setJniWeakRefs(int jniWeakRefs)
    {
        this.jniWeakRefs = jniWeakRefs;
    }

    public int getDeadLockCount()
    {
        return deadLockCount;
    }

    public void setDeadLockCount(int deadLockCount)
    {
        this.deadLockCount = deadLockCount;
    }

    public int getErrorCount()
    {
        return errorCount;
    }

    public void setErrorCount(int errorCount)
    {
        this.errorCount = errorCount;
    }

    public ThreadStat getThreadStat()
    {
        return threadStat;
    }

    public void setThreadStat(ThreadStat threadStat)
    {
        this.threadStat = threadStat;
    }

    public JavaThreadStat getJavaThreadStat()
    {
        return javaThreadStat;
    }

    public void setJavaThreadStat(JavaThreadStat javaThreadStat)
    {
        this.javaThreadStat = javaThreadStat;
    }

    public ThreadStat getJitThreadStat()
    {
        return jitThreadStat;
    }

    public void setJitThreadStat(ThreadStat jitThreadStat)
    {
        this.jitThreadStat = jitThreadStat;
    }

    public ThreadStat getGcThreadStat()
    {
        return gcThreadStat;
    }

    public void setGcThreadStat(ThreadStat gcThreadStat)
    {
        this.gcThreadStat = gcThreadStat;
    }

    public ThreadStat getOtherThreadStat()
    {
        return otherThreadStat;
    }

    public void setOtherThreadStat(ThreadStat otherThreadStat)
    {
        this.otherThreadStat = otherThreadStat;
    }

    public Map<String, ThreadStat> getThreadGroupStat()
    {
        return threadGroupStat;
    }

    public void setThreadGroupStat(Map<String, ThreadStat> threadGroupStat)
    {
        this.threadGroupStat = threadGroupStat;
    }

    public OSTreadState[] getStates()
    {
        return states;
    }

    public JavaThreadState[] getJavaStates()
    {
        return javaStates;
    }

    public static class ThreadStat
    {
        private final int[] counts = new int[OSTreadState.COUNT];

        public void inc(OSTreadState state)
        {
            counts[state.ordinal()]++;
        }

        public int[] getCounts()
        {
            return counts;
        }
    }

    public static class JavaThreadStat extends ThreadStat
    {
        private final int[] javaCounts = new int[JavaThreadState.COUNT];

        private int daemonCount;

        public void inc(JavaThreadState state)
        {
            javaCounts[state.ordinal()]++;
        }

        public void incDaemon()
        {
            daemonCount++;
        }

        public int[] getJavaCounts()
        {
            return javaCounts;
        }

        public int getDaemonCount()
        {
            return daemonCount;
        }

        public void setDaemonCount(int daemonCount)
        {
            this.daemonCount = daemonCount;
        }


    }
}
