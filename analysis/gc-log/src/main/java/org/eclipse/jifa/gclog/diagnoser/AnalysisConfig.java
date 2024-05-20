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
package org.eclipse.jifa.gclog.diagnoser;


import org.eclipse.jifa.gclog.model.GCModel;
import org.eclipse.jifa.gclog.vo.TimeRange;

import java.util.Objects;

public class AnalysisConfig
{
    private TimeRange timeRange;
    private double longPauseThreshold;
    private double longConcurrentThreshold;
    private double youngGCFrequentIntervalThreshold;
    private double oldGCFrequentIntervalThreshold;
    private double fullGCFrequentIntervalThreshold;
    private double highOldUsageThreshold;
    private double highHumongousUsageThreshold;
    private double highHeapUsageThreshold;
    private double highMetaspaceUsageThreshold;
    private double smallGenerationThreshold;
    private double highPromotionThreshold;
    private double badThroughputThreshold;
    private double tooManyOldGCThreshold;
    private double highSysThreshold;
    private double lowUsrThreshold;

    public TimeRange getTimeRange()
    {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange)
    {
        this.timeRange = timeRange;
    }

    public double getLongPauseThreshold()
    {
        return longPauseThreshold;
    }

    public void setLongPauseThreshold(double longPauseThreshold)
    {
        this.longPauseThreshold = longPauseThreshold;
    }

    public double getLongConcurrentThreshold()
    {
        return longConcurrentThreshold;
    }

    public void setLongConcurrentThreshold(double longConcurrentThreshold)
    {
        this.longConcurrentThreshold = longConcurrentThreshold;
    }

    public double getYoungGCFrequentIntervalThreshold()
    {
        return youngGCFrequentIntervalThreshold;
    }

    public void setYoungGCFrequentIntervalThreshold(double youngGCFrequentIntervalThreshold)
    {
        this.youngGCFrequentIntervalThreshold = youngGCFrequentIntervalThreshold;
    }

    public double getOldGCFrequentIntervalThreshold()
    {
        return oldGCFrequentIntervalThreshold;
    }

    public void setOldGCFrequentIntervalThreshold(double oldGCFrequentIntervalThreshold)
    {
        this.oldGCFrequentIntervalThreshold = oldGCFrequentIntervalThreshold;
    }

    public double getFullGCFrequentIntervalThreshold()
    {
        return fullGCFrequentIntervalThreshold;
    }

    public void setFullGCFrequentIntervalThreshold(double fullGCFrequentIntervalThreshold)
    {
        this.fullGCFrequentIntervalThreshold = fullGCFrequentIntervalThreshold;
    }

    public double getHighOldUsageThreshold()
    {
        return highOldUsageThreshold;
    }

    public void setHighOldUsageThreshold(double highOldUsageThreshold)
    {
        this.highOldUsageThreshold = highOldUsageThreshold;
    }

    public double getHighHumongousUsageThreshold()
    {
        return highHumongousUsageThreshold;
    }

    public void setHighHumongousUsageThreshold(double highHumongousUsageThreshold)
    {
        this.highHumongousUsageThreshold = highHumongousUsageThreshold;
    }

    public double getHighHeapUsageThreshold()
    {
        return highHeapUsageThreshold;
    }

    public void setHighHeapUsageThreshold(double highHeapUsageThreshold)
    {
        this.highHeapUsageThreshold = highHeapUsageThreshold;
    }

    public double getHighMetaspaceUsageThreshold()
    {
        return highMetaspaceUsageThreshold;
    }

    public void setHighMetaspaceUsageThreshold(double highMetaspaceUsageThreshold)
    {
        this.highMetaspaceUsageThreshold = highMetaspaceUsageThreshold;
    }

    public double getSmallGenerationThreshold()
    {
        return smallGenerationThreshold;
    }

    public void setSmallGenerationThreshold(double smallGenerationThreshold)
    {
        this.smallGenerationThreshold = smallGenerationThreshold;
    }

    public double getHighPromotionThreshold()
    {
        return highPromotionThreshold;
    }

    public void setHighPromotionThreshold(double highPromotionThreshold)
    {
        this.highPromotionThreshold = highPromotionThreshold;
    }

    public double getBadThroughputThreshold()
    {
        return badThroughputThreshold;
    }

    public void setBadThroughputThreshold(double badThroughputThreshold)
    {
        this.badThroughputThreshold = badThroughputThreshold;
    }

    public double getTooManyOldGCThreshold()
    {
        return tooManyOldGCThreshold;
    }

    public void setTooManyOldGCThreshold(double tooManyOldGCThreshold)
    {
        this.tooManyOldGCThreshold = tooManyOldGCThreshold;
    }

    public double getHighSysThreshold()
    {
        return highSysThreshold;
    }

    public void setHighSysThreshold(double highSysThreshold)
    {
        this.highSysThreshold = highSysThreshold;
    }

    public double getLowUsrThreshold()
    {
        return lowUsrThreshold;
    }

    public void setLowUsrThreshold(double lowUsrThreshold)
    {
        this.lowUsrThreshold = lowUsrThreshold;
    }

    public static AnalysisConfig defaultConfig(GCModel model)
    {
        AnalysisConfig config = new AnalysisConfig();
        config.setTimeRange(new TimeRange(model.getStartTime(), model.getEndTime()));
        config.setLongPauseThreshold(model.isPauseless() ? 30 : 400);
        config.setLongConcurrentThreshold(30000);
        config.setYoungGCFrequentIntervalThreshold(1000);
        config.setOldGCFrequentIntervalThreshold(15000);
        config.setFullGCFrequentIntervalThreshold(model.isGenerational() ? 60000 : 2000);
        config.setHighOldUsageThreshold(80);
        config.setHighHumongousUsageThreshold(50);
        config.setHighHeapUsageThreshold(60);
        config.setHighMetaspaceUsageThreshold(80);
        config.setSmallGenerationThreshold(10);
        config.setHighPromotionThreshold(3);
        config.setBadThroughputThreshold(90);
        config.setTooManyOldGCThreshold(20);
        config.setHighSysThreshold(50);
        config.setLowUsrThreshold(100);
        return config;
    }

    // time range is ignored here

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        AnalysisConfig config = (AnalysisConfig)o;
        return Double.compare(config.longPauseThreshold, longPauseThreshold) == 0
                && Double.compare(config.longConcurrentThreshold, longConcurrentThreshold) == 0
                && Double.compare(config.youngGCFrequentIntervalThreshold, youngGCFrequentIntervalThreshold) == 0
                && Double.compare(config.oldGCFrequentIntervalThreshold, oldGCFrequentIntervalThreshold) == 0
                && Double.compare(config.fullGCFrequentIntervalThreshold, fullGCFrequentIntervalThreshold) == 0
                && Double.compare(config.highOldUsageThreshold, highOldUsageThreshold) == 0
                && Double.compare(config.highHumongousUsageThreshold, highHumongousUsageThreshold) == 0
                && Double.compare(config.highHeapUsageThreshold, highHeapUsageThreshold) == 0
                && Double.compare(config.highMetaspaceUsageThreshold, highMetaspaceUsageThreshold) == 0
                && Double.compare(config.smallGenerationThreshold, smallGenerationThreshold) == 0
                && Double.compare(config.highPromotionThreshold, highPromotionThreshold) == 0
                && Double.compare(config.badThroughputThreshold, badThroughputThreshold) == 0
                && Double.compare(config.tooManyOldGCThreshold, tooManyOldGCThreshold) == 0
                && Double.compare(config.highSysThreshold, highSysThreshold) == 0
                && Double.compare(config.lowUsrThreshold, lowUsrThreshold) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(longPauseThreshold, longConcurrentThreshold, youngGCFrequentIntervalThreshold,
                oldGCFrequentIntervalThreshold, fullGCFrequentIntervalThreshold, highOldUsageThreshold,
                highHumongousUsageThreshold, highHeapUsageThreshold, highMetaspaceUsageThreshold,
                smallGenerationThreshold, highPromotionThreshold, badThroughputThreshold, tooManyOldGCThreshold,
                highSysThreshold, lowUsrThreshold);
    }
}
