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

package org.eclipse.jifa.gclog.vo;

import java.util.List;

public record PhaseStatistics(List<ParentStatisticsInfo> parents)
{
    public static record ParentStatisticsInfo(PhaseStatisticItem self, List<PhaseStatisticItem> phases, List<PhaseStatisticItem> causes)
    {
    }

    public static record PhaseStatisticItem(String name, int count, double intervalAvg, double intervalMin,
                                            double durationAvg, double durationMax, double durationTotal)
    {
    }
}
