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

import org.eclipse.jifa.jfr.util.StackTraceUtil;
import org.eclipse.jifa.jfr.model.TaskData;
import org.eclipse.jifa.jfr.model.jfr.RecordedEvent;
import org.eclipse.jifa.jfr.model.jfr.RecordedStackTrace;
import org.eclipse.jifa.jfr.model.jfr.RecordedThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jifa.jfr.model.Task;
import org.eclipse.jifa.jfr.model.TaskSum;

public abstract class SumExtractor extends Extractor
{
    SumExtractor(JFRAnalysisContext context, List<String> interested)
    {
        super(context, interested);
    }

    public static class TaskSumData extends TaskData
    {
        TaskSumData(RecordedThread thread)
        {
            super(thread);
        }

        long sum;
    }

    protected final Map<Long, TaskSumData> data = new HashMap<>();

    TaskSumData getTaskSumData(RecordedThread thread)
    {
        return data.computeIfAbsent(thread.getJavaThreadId(), i -> new TaskSumData(thread));
    }

    protected void visitEvent(RecordedEvent event, long eventValue)
    {
        RecordedStackTrace stackTrace = event.getStackTrace();
        if (stackTrace == null)
        {
            return;
        }

        TaskSumData data = getTaskSumData(event.getThread());
        if (data.getSamples() == null)
        {
            data.setSamples(new HashMap<>());
        }

        data.getSamples().compute(stackTrace, (k, tmp) -> tmp == null ? eventValue : tmp + eventValue);
        data.sum += eventValue;
    }

    public List<TaskSum> buildTaskSums()
    {
        List<TaskSum> sums = new ArrayList<>();
        for (TaskSumData data : this.data.values())
        {
            if (data.sum == 0)
            {
                continue;
            }

            TaskSum ts = new TaskSum();
            Task ta = new Task();
            ta.setId(data.getThread().getJavaThreadId());
            ta.setName(context.getThread(data.getThread()).getName());
            ts.setTask(ta);

            if (data.getSamples() != null)
            {
                ts.setSum(data.sum);
                ts.setSamples(data.getSamples().entrySet().stream().collect(
                        Collectors.toMap(
                                e -> StackTraceUtil.build(e.getKey(), context.getSymbols()),
                                Map.Entry::getValue,
                                Long::sum)
                ));
            }

            sums.add(ts);
        }

        sums.sort((o1, o2) ->
        {
            long delta = o2.getSum() - o1.getSum();
            return delta > 0 ? 1 : (delta == 0 ? 0 : -1);
        });

        return sums;
    }
}
