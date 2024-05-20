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

package org.eclipse.jifa.gclog.model.modeInfo;

import org.eclipse.jifa.gclog.diagnoser.AnalysisConfig;

import java.util.List;

import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_DOUBLE;
import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_INT;

/**
 * This class provides some necessary information to the frontend.
 */
public class GCLogMetadata
{
    private String collector;
    private String logStyle;
    private double startTime = UNKNOWN_DOUBLE;
    private double endTime = UNKNOWN_DOUBLE;
    private double timestamp = UNKNOWN_DOUBLE;
    private boolean generational = true;
    private boolean pauseless = false;
    private boolean metaspaceCapacityReliable = false;
    private int parallelGCThreads = UNKNOWN_INT;
    private int concurrentGCThreads = UNKNOWN_INT;
    private List<String> parentEventTypes;
    private List<String> importantEventTypes;
    private List<String> pauseEventTypes;
    private List<String> mainPauseEventTypes;
    private List<String> allEventTypes;
    private List<String> causes;
    private AnalysisConfig analysisConfig;

    public String getCollector()
    {
        return collector;
    }

    public void setCollector(String collector)
    {
        this.collector = collector;
    }

    public String getLogStyle()
    {
        return logStyle;
    }

    public void setLogStyle(String logStyle)
    {
        this.logStyle = logStyle;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

    public double getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(double timestamp)
    {
        this.timestamp = timestamp;
    }

    public boolean isGenerational()
    {
        return generational;
    }

    public void setGenerational(boolean generational)
    {
        this.generational = generational;
    }

    public boolean isPauseless()
    {
        return pauseless;
    }

    public void setPauseless(boolean pauseless)
    {
        this.pauseless = pauseless;
    }

    public boolean isMetaspaceCapacityReliable()
    {
        return metaspaceCapacityReliable;
    }

    public void setMetaspaceCapacityReliable(boolean metaspaceCapacityReliable)
    {
        this.metaspaceCapacityReliable = metaspaceCapacityReliable;
    }

    public int getParallelGCThreads()
    {
        return parallelGCThreads;
    }

    public void setParallelGCThreads(int parallelGCThreads)
    {
        this.parallelGCThreads = parallelGCThreads;
    }

    public int getConcurrentGCThreads()
    {
        return concurrentGCThreads;
    }

    public void setConcurrentGCThreads(int concurrentGCThreads)
    {
        this.concurrentGCThreads = concurrentGCThreads;
    }

    public List<String> getParentEventTypes()
    {
        return parentEventTypes;
    }

    public void setParentEventTypes(List<String> parentEventTypes)
    {
        this.parentEventTypes = parentEventTypes;
    }

    public List<String> getImportantEventTypes()
    {
        return importantEventTypes;
    }

    public void setImportantEventTypes(List<String> importantEventTypes)
    {
        this.importantEventTypes = importantEventTypes;
    }

    public List<String> getPauseEventTypes()
    {
        return pauseEventTypes;
    }

    public void setPauseEventTypes(List<String> pauseEventTypes)
    {
        this.pauseEventTypes = pauseEventTypes;
    }

    public List<String> getMainPauseEventTypes()
    {
        return mainPauseEventTypes;
    }

    public void setMainPauseEventTypes(List<String> mainPauseEventTypes)
    {
        this.mainPauseEventTypes = mainPauseEventTypes;
    }

    public List<String> getAllEventTypes()
    {
        return allEventTypes;
    }

    public void setAllEventTypes(List<String> allEventTypes)
    {
        this.allEventTypes = allEventTypes;
    }

    public List<String> getCauses()
    {
        return causes;
    }

    public void setCauses(List<String> causes)
    {
        this.causes = causes;
    }

    public AnalysisConfig getAnalysisConfig()
    {
        return analysisConfig;
    }

    public void setAnalysisConfig(AnalysisConfig analysisConfig)
    {
        this.analysisConfig = analysisConfig;
    }
}
