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
package org.eclipse.jifa.gclog.event.evnetInfo;

import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_DOUBLE;
import static org.eclipse.jifa.gclog.util.Constant.UNKNOWN_INT;

public class ReferenceGC
{
    private double softReferenceStartTime = UNKNOWN_DOUBLE;
    private int softReferenceCount = UNKNOWN_INT;
    private double softReferencePauseTime = UNKNOWN_DOUBLE;

    private double weakReferenceStartTime = UNKNOWN_DOUBLE;
    private int weakReferenceCount = UNKNOWN_INT;
    private double weakReferencePauseTime = UNKNOWN_DOUBLE;

    private double finalReferenceStartTime = UNKNOWN_DOUBLE;
    private int finalReferenceCount = UNKNOWN_INT;
    private double finalReferencePauseTime = UNKNOWN_DOUBLE;

    private double phantomReferenceStartTime = UNKNOWN_DOUBLE;
    private int phantomReferenceCount = UNKNOWN_INT;
    private int phantomReferenceFreedCount;
    private double phantomReferencePauseTime = UNKNOWN_DOUBLE;

    private double jniWeakReferenceStartTime = UNKNOWN_DOUBLE;
    private double jniWeakReferencePauseTime = UNKNOWN_DOUBLE;

    public double getSoftReferenceStartTime()
    {
        return softReferenceStartTime;
    }

    public void setSoftReferenceStartTime(double softReferenceStartTime)
    {
        this.softReferenceStartTime = softReferenceStartTime;
    }

    public int getSoftReferenceCount()
    {
        return softReferenceCount;
    }

    public void setSoftReferenceCount(int softReferenceCount)
    {
        this.softReferenceCount = softReferenceCount;
    }

    public double getSoftReferencePauseTime()
    {
        return softReferencePauseTime;
    }

    public void setSoftReferencePauseTime(double softReferencePauseTime)
    {
        this.softReferencePauseTime = softReferencePauseTime;
    }

    public double getWeakReferenceStartTime()
    {
        return weakReferenceStartTime;
    }

    public void setWeakReferenceStartTime(double weakReferenceStartTime)
    {
        this.weakReferenceStartTime = weakReferenceStartTime;
    }

    public int getWeakReferenceCount()
    {
        return weakReferenceCount;
    }

    public void setWeakReferenceCount(int weakReferenceCount)
    {
        this.weakReferenceCount = weakReferenceCount;
    }

    public double getWeakReferencePauseTime()
    {
        return weakReferencePauseTime;
    }

    public void setWeakReferencePauseTime(double weakReferencePauseTime)
    {
        this.weakReferencePauseTime = weakReferencePauseTime;
    }

    public double getFinalReferenceStartTime()
    {
        return finalReferenceStartTime;
    }

    public void setFinalReferenceStartTime(double finalReferenceStartTime)
    {
        this.finalReferenceStartTime = finalReferenceStartTime;
    }

    public int getFinalReferenceCount()
    {
        return finalReferenceCount;
    }

    public void setFinalReferenceCount(int finalReferenceCount)
    {
        this.finalReferenceCount = finalReferenceCount;
    }

    public double getFinalReferencePauseTime()
    {
        return finalReferencePauseTime;
    }

    public void setFinalReferencePauseTime(double finalReferencePauseTime)
    {
        this.finalReferencePauseTime = finalReferencePauseTime;
    }

    public double getPhantomReferenceStartTime()
    {
        return phantomReferenceStartTime;
    }

    public void setPhantomReferenceStartTime(double phantomReferenceStartTime)
    {
        this.phantomReferenceStartTime = phantomReferenceStartTime;
    }

    public int getPhantomReferenceCount()
    {
        return phantomReferenceCount;
    }

    public void setPhantomReferenceCount(int phantomReferenceCount)
    {
        this.phantomReferenceCount = phantomReferenceCount;
    }

    public int getPhantomReferenceFreedCount()
    {
        return phantomReferenceFreedCount;
    }

    public void setPhantomReferenceFreedCount(int phantomReferenceFreedCount)
    {
        this.phantomReferenceFreedCount = phantomReferenceFreedCount;
    }

    public double getPhantomReferencePauseTime()
    {
        return phantomReferencePauseTime;
    }

    public void setPhantomReferencePauseTime(double phantomReferencePauseTime)
    {
        this.phantomReferencePauseTime = phantomReferencePauseTime;
    }

    public double getJniWeakReferenceStartTime()
    {
        return jniWeakReferenceStartTime;
    }

    public void setJniWeakReferenceStartTime(double jniWeakReferenceStartTime)
    {
        this.jniWeakReferenceStartTime = jniWeakReferenceStartTime;
    }

    public double getJniWeakReferencePauseTime()
    {
        return jniWeakReferencePauseTime;
    }

    public void setJniWeakReferencePauseTime(double jniWeakReferencePauseTime)
    {
        this.jniWeakReferencePauseTime = jniWeakReferencePauseTime;
    }
}
