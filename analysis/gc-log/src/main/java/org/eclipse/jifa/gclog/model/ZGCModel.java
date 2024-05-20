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

package org.eclipse.jifa.gclog.model;

import org.eclipse.jifa.gclog.event.GCEvent;
import org.eclipse.jifa.gclog.event.TimedEvent;
import org.eclipse.jifa.gclog.event.evnetInfo.MemoryArea;
import org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType;
import org.eclipse.jifa.gclog.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.eclipse.jifa.gclog.model.GCEventType.*;
import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_INT;

public class ZGCModel extends GCModel
{

    // key of maps here should include unit like
    // "Memory: Allocation Rate MB/s" to deduplicate
    private final List<ZStatistics> statistics = new ArrayList<>();
    private final List<GCEvent> allocationStalls = new ArrayList<>();
    private long recommendMaxHeapSize = UNKNOWN_INT;

    private static final GCCollectorType collector = GCCollectorType.ZGC;

    public ZGCModel()
    {
        super(collector);
        this.setMetaspaceCapacityReliable(true);
    }

    private static final List<GCEventType> allEventTypes = GCModel.calcAllEventTypes(collector);
    private static final List<GCEventType> pauseEventTypes = GCModel.calcPauseEventTypes(collector);
    private static final List<GCEventType> mainPauseEventTypes = GCModel.calcMainPauseEventTypes(collector);
    private static final List<GCEventType> parentEventTypes = GCModel.calcParentEventTypes(collector);
    private static final List<GCEventType> importantEventTypes = List.of(ZGC_GARBAGE_COLLECTION, ZGC_PAUSE_MARK_START,
            ZGC_PAUSE_MARK_END, ZGC_PAUSE_RELOCATE_START, ZGC_CONCURRENT_MARK, ZGC_CONCURRENT_NONREF,
            ZGC_CONCURRENT_SELECT_RELOC_SET, ZGC_CONCURRENT_PREPARE_RELOC_SET, ZGC_CONCURRENT_RELOCATE);

    @Override
    protected List<GCEventType> getAllEventTypes()
    {
        return allEventTypes;
    }

    @Override
    protected List<GCEventType> getPauseEventTypes()
    {
        return pauseEventTypes;
    }

    @Override
    protected List<GCEventType> getMainPauseEventTypes()
    {
        return mainPauseEventTypes;
    }

    @Override
    protected List<GCEventType> getImportantEventTypes()
    {
        return importantEventTypes;
    }

    @Override
    protected List<GCEventType> getParentEventTypes()
    {
        return parentEventTypes;
    }

    public List<GCEvent> getAllocationStalls()
    {
        return allocationStalls;
    }

    public void addAllocationStalls(GCEvent allocationStall)
    {
        this.allocationStalls.add(allocationStall);
    }

    public List<ZStatistics> getStatistics()
    {
        return statistics;
    }

    @Override
    public long getRecommendMaxHeapSize()
    {
        if (recommendMaxHeapSize == UNKNOWN_INT && !statistics.isEmpty())
        {
            // used at marking start + garbage collection cycle * allocation rate
            int statisticIndex = 0;
            for (GCEvent collection : getGcCollectionEvents())
            {
                if (collection.getEventType() != ZGC_GARBAGE_COLLECTION)
                {
                    continue;
                }
                if (collection.getMemoryItem(MemoryArea.HEAP).getPreUsed() == UNKNOWN_INT)
                {
                    continue;
                }
                while (statisticIndex < statistics.size() &&
                        statistics.get(statisticIndex).getStartTime() < collection.getEndTime())
                {
                    statisticIndex++;
                }
                if (statisticIndex >= statistics.size())
                {
                    break;
                }
                double collectionCycleMs = statistics.get(statisticIndex)
                        .get("Collector: Garbage Collection Cycle ms")
                        .getMax10s();
                double allocationRateMBps = statistics.get(statisticIndex)
                        .get("Memory: Allocation Rate MB/s")
                        .getMax10s();
                double size = collection.getMemoryItem(MemoryArea.HEAP).getPreUsed() +
                        (collectionCycleMs / Constant.MS2S) * (allocationRateMBps * Constant.KB2MB);
                recommendMaxHeapSize = Math.max(recommendMaxHeapSize, (long)size);
            }
        }
        return recommendMaxHeapSize;
    }

    public static class ZStatistics extends TimedEvent
    {
        private final Map<String, ZStatisticsItem> items = new HashMap<>();

        public ZStatisticsItem get(String key)
        {
            return items.getOrDefault(key, null);
        }

        public void put(String key, ZStatisticsItem item)
        {
            items.put(key, item);
        }

        public Map<String, ZStatisticsItem> getStatisticItems()
        {
            return items;
        }
    }

    public static class ZStatisticsItem
    {
        private double avg10s;
        private double max10s;
        private double avg10m;
        private double max10m;
        private double avg10h;
        private double max10h;
        private double avgTotal;
        private double maxTotal;

        public ZStatisticsItem(double avg10s, double max10s, double avg10m, double max10m, double avg10h, double max10h,
                double avgTotal, double maxTotal)
        {
            this.avg10s = avg10s;
            this.max10s = max10s;
            this.avg10m = avg10m;
            this.max10m = max10m;
            this.avg10h = avg10h;
            this.max10h = max10h;
            this.avgTotal = avgTotal;
            this.maxTotal = maxTotal;
        }

        public double getAvg10s()
        {
            return avg10s;
        }

        public void setAvg10s(double avg10s)
        {
            this.avg10s = avg10s;
        }

        public double getMax10s()
        {
            return max10s;
        }

        public void setMax10s(double max10s)
        {
            this.max10s = max10s;
        }

        public double getAvg10m()
        {
            return avg10m;
        }

        public void setAvg10m(double avg10m)
        {
            this.avg10m = avg10m;
        }

        public double getMax10m()
        {
            return max10m;
        }

        public void setMax10m(double max10m)
        {
            this.max10m = max10m;
        }

        public double getAvg10h()
        {
            return avg10h;
        }

        public void setAvg10h(double avg10h)
        {
            this.avg10h = avg10h;
        }

        public double getMax10h()
        {
            return max10h;
        }

        public void setMax10h(double max10h)
        {
            this.max10h = max10h;
        }

        public double getAvgTotal()
        {
            return avgTotal;
        }

        public void setAvgTotal(double avgTotal)
        {
            this.avgTotal = avgTotal;
        }

        public double getMaxTotal()
        {
            return maxTotal;
        }

        public void setMaxTotal(double maxTotal)
        {
            this.maxTotal = maxTotal;
        }
    }
}
