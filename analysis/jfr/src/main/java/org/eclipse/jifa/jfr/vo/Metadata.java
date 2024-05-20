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

import org.eclipse.jifa.jfr.model.PerfDimension;

public class Metadata
{
    private PerfDimension[] perfDimensions;

    public PerfDimension[] getPerfDimensions()
    {
        return perfDimensions;
    }

    public void setPerfDimensions(PerfDimension[] perfDimensions)
    {
        this.perfDimensions = perfDimensions;
    }
}
