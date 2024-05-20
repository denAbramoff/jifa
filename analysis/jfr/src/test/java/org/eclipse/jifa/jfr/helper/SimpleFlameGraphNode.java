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
package org.eclipse.jifa.jfr.helper;

import java.math.BigDecimal;
import java.util.List;

public class SimpleFlameGraphNode
{
    private String name;
    private BigDecimal value;

    private SimpleFlameGraphNode parent;
    private List<SimpleFlameGraphNode> children;

    private boolean print = false;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getValue()
    {
        return value;
    }

    public void setValue(BigDecimal value)
    {
        this.value = value;
    }

    public SimpleFlameGraphNode getParent()
    {
        return parent;
    }

    public void setParent(SimpleFlameGraphNode parent)
    {
        this.parent = parent;
    }

    public List<SimpleFlameGraphNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<SimpleFlameGraphNode> children)
    {
        this.children = children;
    }

    public boolean isPrint()
    {
        return print;
    }

    public void setPrint(boolean print)
    {
        this.print = print;
    }
}
