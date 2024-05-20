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

import org.eclipse.jifa.tda.enums.SourceType;

import java.util.Arrays;
import java.util.Objects;

public class Frame
{
    private String clazz;

    private String method;

    private String module;

    private SourceType sourceType;

    // -1 means unknown
    private String source;

    // -1 means unknown
    private int line = -1;

    private Monitor[] monitors;

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

    public Monitor[] getMonitors()
    {
        return monitors;
    }

    public void setMonitors(Monitor[] monitors)
    {
        this.monitors = monitors;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Frame frame = (Frame)o;
        return line == frame.line && Objects.equals(clazz, frame.clazz) &&
                Objects.equals(method, frame.method) && Objects.equals(module, frame.module) &&
                sourceType == frame.sourceType && Objects.equals(source, frame.source) &&
                Arrays.equals(monitors, frame.monitors);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(clazz, method, module, sourceType, source, line);
        result = 31 * result + Arrays.hashCode(monitors);
        return result;
    }
}
