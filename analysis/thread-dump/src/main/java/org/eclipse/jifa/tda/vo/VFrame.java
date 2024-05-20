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

import com.google.gson.annotations.SerializedName;

import org.eclipse.jifa.tda.enums.SourceType;

import java.util.List;

public class VFrame
{
    private int id;

    @SerializedName("class")
    private String clazz;

    private String method;

    private String module;

    private SourceType sourceType;

    private String source;

    private int line;

    private int weight;

    private VMonitor wait;

    private List<VMonitor> monitors;

    private boolean end;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public SourceType getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType)
    {
        this.sourceType = sourceType;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public int getLine()
    {
        return line;
    }

    public void setLine(int line)
    {
        this.line = line;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public VMonitor getWait()
    {
        return wait;
    }

    public void setWait(VMonitor wait)
    {
        this.wait = wait;
    }

    public List<VMonitor> getMonitors()
    {
        return monitors;
    }

    public void setMonitors(List<VMonitor> monitors)
    {
        this.monitors = monitors;
    }

    public boolean isEnd()
    {
        return end;
    }

    public void setEnd(boolean end)
    {
        this.end = end;
    }
}
