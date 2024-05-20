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
package org.eclipse.jifa.gclog.vo;

public class MemoryStatistics
{
    private MemoryStatisticsItem young;
    private MemoryStatisticsItem old;
    private MemoryStatisticsItem humongous;
    private MemoryStatisticsItem heap;
    private MemoryStatisticsItem metaspace;

    public MemoryStatisticsItem getYoung()
    {
        return young;
    }

    public void setYoung(MemoryStatisticsItem young)
    {
        this.young = young;
    }

    public MemoryStatisticsItem getOld()
    {
        return old;
    }

    public void setOld(MemoryStatisticsItem old)
    {
        this.old = old;
    }

    public MemoryStatisticsItem getHumongous()
    {
        return humongous;
    }

    public void setHumongous(MemoryStatisticsItem humongous)
    {
        this.humongous = humongous;
    }

    public MemoryStatisticsItem getHeap()
    {
        return heap;
    }

    public void setHeap(MemoryStatisticsItem heap)
    {
        this.heap = heap;
    }

    public MemoryStatisticsItem getMetaspace()
    {
        return metaspace;
    }

    public void setMetaspace(MemoryStatisticsItem metaspace)
    {
        this.metaspace = metaspace;
    }

    public static class MemoryStatisticsItem
    {
        private long capacityAvg;
        private long usedMax;
        private long usedAvgAfterFullGC;
        private long usedAvgAfterOldGC;

        public MemoryStatisticsItem(long capacityAvg, long usedMax, long usedAvgAfterFullGC, long usedAvgAfterOldGC)
        {
            this.capacityAvg = capacityAvg;
            this.usedMax = usedMax;
            this.usedAvgAfterFullGC = usedAvgAfterFullGC;
            this.usedAvgAfterOldGC = usedAvgAfterOldGC;
        }

        public long getCapacityAvg()
        {
            return capacityAvg;
        }

        public void setCapacityAvg(long capacityAvg)
        {
            this.capacityAvg = capacityAvg;
        }

        public long getUsedMax()
        {
            return usedMax;
        }

        public void setUsedMax(long usedMax)
        {
            this.usedMax = usedMax;
        }

        public long getUsedAvgAfterFullGC()
        {
            return usedAvgAfterFullGC;
        }

        public void setUsedAvgAfterFullGC(long usedAvgAfterFullGC)
        {
            this.usedAvgAfterFullGC = usedAvgAfterFullGC;
        }

        public long getUsedAvgAfterOldGC()
        {
            return usedAvgAfterOldGC;
        }

        public void setUsedAvgAfterOldGC(long usedAvgAfterOldGC)
        {
            this.usedAvgAfterOldGC = usedAvgAfterOldGC;
        }
    }
}
