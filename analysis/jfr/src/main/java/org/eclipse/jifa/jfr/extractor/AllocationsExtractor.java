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
package org.eclipse.jifa.jfr.extractor;

import org.eclipse.jifa.jfr.common.EventConstant;
import org.eclipse.jifa.jfr.model.TaskData;
import org.eclipse.jifa.jfr.model.jfr.RecordedEvent;
import org.eclipse.jifa.jfr.model.jfr.RecordedStackTrace;
import org.eclipse.jifa.jfr.model.jfr.RecordedThread;
import org.eclipse.jifa.jfr.util.StackTraceUtil;
import org.eclipse.jifa.jfr.model.AnalysisResult;
import org.eclipse.jifa.jfr.model.DimensionResult;
import org.eclipse.jifa.jfr.model.Task;
import org.eclipse.jifa.jfr.model.TaskAllocations;

import java.util.*;
import java.util.stream.Collectors;

import static org.eclipse.jifa.jfr.common.EventConstant.OBJECT_ALLOCATION_SAMPLE;

public class AllocationsExtractor extends Extractor
{
    protected boolean useObjectAllocationSample;

    protected static final List<String> INTERESTED = List.of(EventConstant.OBJECT_ALLOCATION_IN_NEW_TLAB,
            EventConstant.OBJECT_ALLOCATION_OUTSIDE_TLAB, OBJECT_ALLOCATION_SAMPLE);

    protected static class AllocTaskData extends TaskData
    {
        AllocTaskData(RecordedThread thread)
        {
            super(thread);
        }

        public long allocations;

        public long allocatedMemory;
    }

    protected final Map<Long, AllocTaskData> data = new HashMap<>();

    public AllocationsExtractor(JFRAnalysisContext context)
    {
        super(context, INTERESTED);
        try
        {
            this.useObjectAllocationSample = this.context.getActiveSettingBool(OBJECT_ALLOCATION_SAMPLE, "enabled");
        }
        catch (Exception e)
        {
            this.useObjectAllocationSample = false;
        }
    }

    AllocTaskData getThreadData(RecordedThread thread)
    {
        return data.computeIfAbsent(thread.getJavaThreadId(), i -> new AllocTaskData(thread));
    }

    @Override
    void visitObjectAllocationInNewTLAB(RecordedEvent event)
    {
        if (useObjectAllocationSample)
        {
            return;
        }
        visitEvent(event);
    }

    @Override
    void visitObjectAllocationOutsideTLAB(RecordedEvent event)
    {
        if (useObjectAllocationSample)
        {
            return;
        }
        this.visitEvent(event);
    }

    @Override
    void visitObjectAllocationSample(RecordedEvent event)
    {
        this.visitEvent(event);
    }

    void visitEvent(RecordedEvent event)
    {
        RecordedStackTrace stackTrace = event.getStackTrace();
        if (stackTrace == null)
        {
            stackTrace = StackTraceUtil.DUMMY_STACK_TRACE;
        }

        AllocTaskData allocThreadData = getThreadData(event.getThread());
        if (allocThreadData.getSamples() == null)
        {
            allocThreadData.setSamples(new HashMap<>());
        }

        allocThreadData.getSamples().compute(stackTrace, (k, count) -> count == null ? 1 : count + 1);
        allocThreadData.allocations += 1;
    }

    private List<TaskAllocations> buildThreadAllocations()
    {
        List<TaskAllocations> taskAllocations = new ArrayList<>();
        for (AllocTaskData data : this.data.values())
        {
            if (data.allocations == 0)
            {
                continue;
            }

            TaskAllocations threadAllocation = new TaskAllocations();
            Task ta = new Task();
            ta.setId(data.getThread().getJavaThreadId());
            ta.setName(data.getThread().getJavaName());
            threadAllocation.setTask(ta);

            if (data.getSamples() != null)
            {
                threadAllocation.setAllocations(data.allocations);
                threadAllocation.setSamples(data.getSamples().entrySet().stream().collect(
                        Collectors.toMap(
                                e -> StackTraceUtil.build(e.getKey(), context.getSymbols()),
                                Map.Entry::getValue,
                                Long::sum)
                ));
            }

            taskAllocations.add(threadAllocation);
        }

        taskAllocations.sort((o1, o2) ->
        {
            long delta = o2.getAllocations() - o1.getAllocations();
            return delta > 0 ? 1 : (delta == 0 ? 0 : -1);
        });

        return taskAllocations;
    }

    @Override
    public void fillResult(AnalysisResult result)
    {
        DimensionResult<TaskAllocations> allocResult = new DimensionResult<>();
        allocResult.setList(buildThreadAllocations());
        result.setAllocations(allocResult);
    }
}
