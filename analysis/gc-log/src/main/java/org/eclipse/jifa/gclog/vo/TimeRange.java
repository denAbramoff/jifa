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

import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_DOUBLE;

public class TimeRange
{
    //unit is ms
    private double start = UNKNOWN_DOUBLE;
    private double end = UNKNOWN_DOUBLE;

    public TimeRange(double start, double end)
    {
        this.start = start;
        this.end = end;
    }

    public double getStart()
    {
        return start;
    }

    public void setStart(double start)
    {
        this.start = start;
    }

    public double getEnd()
    {
        return end;
    }

    public void setEnd(double end)
    {
        this.end = end;
    }

    public boolean isValid()
    {
        return start >= 0 && end >= 0 && start < end;
    }

    public double length()
    {
        if (isValid())
        {
            return end - start;
        }
        else
        {
            return UNKNOWN_DOUBLE;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TimeRange timeRange = (TimeRange)o;

        if (Double.compare(start, timeRange.start) != 0)
            return false;
        return Double.compare(end, timeRange.end) == 0;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(start);
        result = (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(end);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return start + " ~ " + end;
    }
}
