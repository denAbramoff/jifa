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
package org.eclipse.jifa.jfr.model.jfr;

import org.eclipse.jifa.jfr.model.symbol.SymbolBase;

import java.util.Objects;

public class RecordedFrame extends SymbolBase
{
    private boolean javaFrame;
    private String type;
    private int bytecodeIndex;
    private RecordedMethod method;
    private int lineNumber;

    public RecordedFrame(boolean javaFrame, String type, int bytecodeIndex, int lineNumber, RecordedMethod method)
    {
        this.javaFrame = javaFrame;
        this.type = type;
        this.bytecodeIndex = bytecodeIndex;
        this.lineNumber = lineNumber;
        this.method = method;
    }

    public RecordedFrame()
    {
    }

    public boolean isJavaFrame()
    {
        return javaFrame;
    }

    public void setJavaFrame(boolean javaFrame)
    {
        this.javaFrame = javaFrame;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getBytecodeIndex()
    {
        return bytecodeIndex;
    }

    public void setBytecodeIndex(int bytecodeIndex)
    {
        this.bytecodeIndex = bytecodeIndex;
    }

    public RecordedMethod getMethod()
    {
        return method;
    }

    public void setMethod(RecordedMethod method)
    {
        this.method = method;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public boolean isEquals(Object b)
    {
        if (!(b instanceof RecordedFrame f2))
        {
            return false;
        }

        return bytecodeIndex == f2.getBytecodeIndex()
                && lineNumber == f2.getLineNumber()
                && javaFrame == f2.isJavaFrame()
                && this.method.equals(f2.method)
                && Objects.equals(type, f2.getType());
    }

    public int genHashCode()
    {
        return Objects.hash(javaFrame, type, bytecodeIndex, method, lineNumber);
    }
}
