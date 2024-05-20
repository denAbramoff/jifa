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

import org.eclipse.jifa.jfr.model.Task;

public class JavaThread extends Task
{
    private long javaId;
    private long osId;

    public long getJavaId()
    {
        return javaId;
    }

    public void setJavaId(long javaId)
    {
        this.javaId = javaId;
    }

    public long getOsId()
    {
        return osId;
    }

    public void setOsId(long osId)
    {
        this.osId = osId;
    }
}
