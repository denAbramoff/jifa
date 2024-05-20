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
package org.eclipse.jifa.jfr.model;

import org.eclipse.jifa.jfr.model.symbol.SymbolBase;

import java.util.Objects;

public class Frame extends SymbolBase
{
    private Method method;
    private int line;
    private String string;

    public Method getMethod()
    {
        return method;
    }

    public int getLine()
    {
        return line;
    }

    public String getString()
    {
        return string;
    }

    public void setMethod(Method method)
    {
        this.method = method;
    }

    public void setLine(int line)
    {
        this.line = line;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public String toString()
    {
        if (this.string != null)
        {
            return string;
        }

        if (this.line == 0)
        {
            this.string = method.toString();
        }
        else
        {
            this.string = String.format("%s:%d", method, line);
        }

        return this.string;
    }

    public int genHashCode()
    {
        return Objects.hash(method, line);
    }

    public boolean isEquals(Object b)
    {
        if (!(b instanceof Frame f2))
        {
            return false;
        }

        return line == f2.getLine() && method.equals(f2.getMethod());
    }
}
