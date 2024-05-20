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

import java.util.Arrays;

public class Trace
{
    private Frame[] frames;

    private ConcurrentLock[] concurrentLocks;

    public Frame[] getFrames()
    {
        return frames;
    }

    public void setFrames(Frame[] frames)
    {
        this.frames = frames;
    }

    public ConcurrentLock[] getConcurrentLocks()
    {
        return concurrentLocks;
    }

    public void setConcurrentLocks(ConcurrentLock[] concurrentLocks)
    {
        this.concurrentLocks = concurrentLocks;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Trace trace = (Trace)o;
        return Arrays.equals(frames, trace.frames) &&
                Arrays.equals(concurrentLocks, trace.concurrentLocks);
    }

    @Override
    public int hashCode()
    {
        int result = Arrays.hashCode(frames);
        result = 31 * result + Arrays.hashCode(concurrentLocks);
        return result;
    }
}
