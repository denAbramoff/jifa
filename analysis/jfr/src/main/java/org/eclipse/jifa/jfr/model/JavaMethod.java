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

import org.eclipse.jifa.jfr.model.Method;

import java.util.Objects;

public class JavaMethod extends Method
{
    private int modifiers;
    private boolean hidden;

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

    public int genHashCode()
    {
        return Objects.hash(modifiers, hidden, getPackageName(), getType(), getName(), getDescriptor());
    }

    @Override
    public int hashCode()
    {
        return genHashCode();
    }

    public boolean equals(Object b)
    {
        if (this == b)
        {
            return true;
        }

        if (b == null)
        {
            return false;
        }

        if (!(b instanceof JavaMethod m2))
        {
            return false;
        }

        return modifiers == m2.modifiers && hidden == m2.hidden && super.equals(m2);
    }
}
