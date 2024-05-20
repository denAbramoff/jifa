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

public class RecordedMethod extends SymbolBase
{
    private RecordedClass type;
    private String name;
    private String descriptor;
    private int modifiers;
    private boolean hidden;

    public RecordedClass getType()
    {
        return type;
    }

    public void setType(RecordedClass type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(String descriptor)
    {
        this.descriptor = descriptor;
    }

    public int getModifiers()
    {
        return modifiers;
    }

    public void setModifiers(int modifiers)
    {
        this.modifiers = modifiers;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    public boolean isEquals(Object b)
    {
        if (!(b instanceof RecordedMethod m2))
        {
            return false;
        }

        return Objects.equals(descriptor, m2.getDescriptor())
                && Objects.equals(name, m2.getName())
                && modifiers == m2.getModifiers()
                && type.equals(m2.getType())
                && hidden == m2.isHidden();
    }

    public int genHashCode()
    {
        return Objects.hash(type, name, descriptor, modifiers, hidden);
    }
}
