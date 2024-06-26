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
package org.eclipse.jifa.jfr.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBase
{
    private List<String> threads = new ArrayList<>();
    private Map<String, Long> threadSplit = new HashMap<>();
    private Map<Integer, String> symbolTable = new HashMap<>();

    public List<String> getThreads()
    {
        return threads;
    }

    public void setThreads(List<String> threads)
    {
        this.threads = threads;
    }

    public Map<String, Long> getThreadSplit()
    {
        return threadSplit;
    }

    public void setThreadSplit(Map<String, Long> threadSplit)
    {
        this.threadSplit = threadSplit;
    }

    public Map<Integer, String> getSymbolTable()
    {
        return symbolTable;
    }

    public void setSymbolTable(Map<Integer, String> symbolTable)
    {
        this.symbolTable = symbolTable;
    }
}
