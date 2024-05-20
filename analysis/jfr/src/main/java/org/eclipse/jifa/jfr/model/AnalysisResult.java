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
package org.eclipse.jifa.jfr.model;

import org.eclipse.jifa.jfr.model.*;

import java.util.List;

public class AnalysisResult
{
    private long processingTimeMillis;

    private DimensionResult<TaskCPUTime> cpuTime;

    private DimensionResult<TaskCount> cpuSample;

    private DimensionResult<TaskSum> wallClock;

    private DimensionResult<TaskAllocations> allocations;

    private DimensionResult<TaskAllocatedMemory> allocatedMemory;

    private DimensionResult<TaskCount> nativeExecutionSamples;

    private DimensionResult<TaskSum> fileIOTime;

    private DimensionResult<TaskSum> fileReadSize;

    private DimensionResult<TaskSum> fileWriteSize;

    private DimensionResult<TaskSum> socketReadSize;

    private DimensionResult<TaskSum> socketReadTime;

    private DimensionResult<TaskSum> socketWriteSize;

    private DimensionResult<TaskSum> socketWriteTime;

    private DimensionResult<TaskSum> synchronization;

    private DimensionResult<TaskSum> threadPark;

    private DimensionResult<TaskCount> classLoadCount;

    private DimensionResult<TaskSum> classLoadWallTime;

    private DimensionResult<TaskSum> threadSleepTime;

    private List<Problem> problems;

    public long getProcessingTimeMillis()
    {
        return processingTimeMillis;
    }

    public void setProcessingTimeMillis(long processingTimeMillis)
    {
        this.processingTimeMillis = processingTimeMillis;
    }

    public DimensionResult<TaskCPUTime> getCpuTime()
    {
        return cpuTime;
    }

    public void setCpuTime(DimensionResult<TaskCPUTime> cpuTime)
    {
        this.cpuTime = cpuTime;
    }

    public DimensionResult<TaskCount> getCpuSample()
    {
        return cpuSample;
    }

    public void setCpuSample(DimensionResult<TaskCount> cpuSample)
    {
        this.cpuSample = cpuSample;
    }

    public DimensionResult<TaskSum> getWallClock()
    {
        return wallClock;
    }

    public void setWallClock(DimensionResult<TaskSum> wallClock)
    {
        this.wallClock = wallClock;
    }

    public DimensionResult<TaskAllocations> getAllocations()
    {
        return allocations;
    }

    public void setAllocations(DimensionResult<TaskAllocations> allocations)
    {
        this.allocations = allocations;
    }

    public DimensionResult<TaskAllocatedMemory> getAllocatedMemory()
    {
        return allocatedMemory;
    }

    public void setAllocatedMemory(
            DimensionResult<TaskAllocatedMemory> allocatedMemory)
    {
        this.allocatedMemory = allocatedMemory;
    }

    public DimensionResult<TaskCount> getNativeExecutionSamples()
    {
        return nativeExecutionSamples;
    }

    public void setNativeExecutionSamples(
            DimensionResult<TaskCount> nativeExecutionSamples)
    {
        this.nativeExecutionSamples = nativeExecutionSamples;
    }

    public DimensionResult<TaskSum> getFileIOTime()
    {
        return fileIOTime;
    }

    public void setFileIOTime(DimensionResult<TaskSum> fileIOTime)
    {
        this.fileIOTime = fileIOTime;
    }

    public DimensionResult<TaskSum> getFileReadSize()
    {
        return fileReadSize;
    }

    public void setFileReadSize(DimensionResult<TaskSum> fileReadSize)
    {
        this.fileReadSize = fileReadSize;
    }

    public DimensionResult<TaskSum> getFileWriteSize()
    {
        return fileWriteSize;
    }

    public void setFileWriteSize(DimensionResult<TaskSum> fileWriteSize)
    {
        this.fileWriteSize = fileWriteSize;
    }

    public DimensionResult<TaskSum> getSocketReadSize()
    {
        return socketReadSize;
    }

    public void setSocketReadSize(DimensionResult<TaskSum> socketReadSize)
    {
        this.socketReadSize = socketReadSize;
    }

    public DimensionResult<TaskSum> getSocketReadTime()
    {
        return socketReadTime;
    }

    public void setSocketReadTime(DimensionResult<TaskSum> socketReadTime)
    {
        this.socketReadTime = socketReadTime;
    }

    public DimensionResult<TaskSum> getSocketWriteSize()
    {
        return socketWriteSize;
    }

    public void setSocketWriteSize(DimensionResult<TaskSum> socketWriteSize)
    {
        this.socketWriteSize = socketWriteSize;
    }

    public DimensionResult<TaskSum> getSocketWriteTime()
    {
        return socketWriteTime;
    }

    public void setSocketWriteTime(DimensionResult<TaskSum> socketWriteTime)
    {
        this.socketWriteTime = socketWriteTime;
    }

    public DimensionResult<TaskSum> getSynchronization()
    {
        return synchronization;
    }

    public void setSynchronization(DimensionResult<TaskSum> synchronization)
    {
        this.synchronization = synchronization;
    }

    public DimensionResult<TaskSum> getThreadPark()
    {
        return threadPark;
    }

    public void setThreadPark(DimensionResult<TaskSum> threadPark)
    {
        this.threadPark = threadPark;
    }

    public DimensionResult<TaskCount> getClassLoadCount()
    {
        return classLoadCount;
    }

    public void setClassLoadCount(DimensionResult<TaskCount> classLoadCount)
    {
        this.classLoadCount = classLoadCount;
    }

    public DimensionResult<TaskSum> getClassLoadWallTime()
    {
        return classLoadWallTime;
    }

    public void setClassLoadWallTime(
            DimensionResult<TaskSum> classLoadWallTime)
    {
        this.classLoadWallTime = classLoadWallTime;
    }

    public DimensionResult<TaskSum> getThreadSleepTime()
    {
        return threadSleepTime;
    }

    public void setThreadSleepTime(DimensionResult<TaskSum> threadSleepTime)
    {
        this.threadSleepTime = threadSleepTime;
    }

    public List<Problem> getProblems()
    {
        return problems;
    }

    public void setProblems(List<Problem> problems)
    {
        this.problems = problems;
    }
}
