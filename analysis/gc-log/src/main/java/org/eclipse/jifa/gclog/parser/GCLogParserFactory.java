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

package org.eclipse.jifa.gclog.parser;

import org.eclipse.jifa.common.domain.exception.CommonException;
import org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType;
import org.eclipse.jifa.gclog.model.modeInfo.GCLogStyle;

import java.io.BufferedReader;

import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.CMS;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.EPSILON;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.G1;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.GENZ;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.PARALLEL;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.SERIAL;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.SHENANDOAH;
import static org.eclipse.jifa.gclog.model.modeInfo.GCCollectorType.ZGC;
import static org.eclipse.jifa.gclog.model.modeInfo.GCLogStyle.PRE_UNIFIED;
import static org.eclipse.jifa.gclog.model.modeInfo.GCLogStyle.UNIFIED;

public class GCLogParserFactory
{
    // When -Xlog:gc*=trace is used, a single gc produces at most about 5000 lines of LOG.
    // 20000 lines should be enough to cover at least one gc.
    public static final int MAX_ATTEMPT_LINE = 20000;

    private static final ParserMetadataRule[] rules = {
            // style
            new ParserMetadataRule("[Times:", PRE_UNIFIED, GCCollectorType.UNKNOWN),
            new ParserMetadataRule(": [GC", PRE_UNIFIED, GCCollectorType.UNKNOWN),
            new ParserMetadataRule("[info]", UNIFIED, GCCollectorType.UNKNOWN),
            new ParserMetadataRule("[gc]", UNIFIED, GCCollectorType.UNKNOWN),
            new ParserMetadataRule("] GC(", UNIFIED, GCCollectorType.UNKNOWN),

            // collector
            new ParserMetadataRule("PSYoungGen", GCLogStyle.UNKNOWN, PARALLEL),
            new ParserMetadataRule("DefNew", GCLogStyle.UNKNOWN, SERIAL),
            new ParserMetadataRule("ParNew", GCLogStyle.UNKNOWN, CMS),
            new ParserMetadataRule("CMS", GCLogStyle.UNKNOWN, CMS),

            new ParserMetadataRule("Pre Evacuate Collection Set", UNIFIED, G1),
            new ParserMetadataRule("G1 Evacuation Pause", GCLogStyle.UNKNOWN, G1),
            new ParserMetadataRule("Eden regions", UNIFIED, G1),
            new ParserMetadataRule("[GC Worker Start (ms): ", GCLogStyle.UNKNOWN, G1),
            new ParserMetadataRule("[concurrent-root-region-scan-start", GCLogStyle.UNKNOWN, G1),
            new ParserMetadataRule("Concurrent Scan Root Regions", GCLogStyle.UNKNOWN, G1),

            new ParserMetadataRule(") Garbage Collection", UNIFIED, ZGC),
            new ParserMetadataRule("Collector: Garbage Collection Cycle", UNIFIED, ZGC),
            new ParserMetadataRule(") Minor Garbage Collection", UNIFIED, GENZ),
            new ParserMetadataRule("Young Pause: Pause Mark End", UNIFIED, GENZ),

            new ParserMetadataRule("Pause Init Update Refs", UNIFIED, SHENANDOAH),

            new ParserMetadataRule("Using Epsilon", UNIFIED, EPSILON),
            new ParserMetadataRule("Using Concurrent Mark Sweep", UNIFIED, CMS),
            new ParserMetadataRule("Using G1", UNIFIED, G1),
            new ParserMetadataRule("Using Parallel", UNIFIED, PARALLEL),
            new ParserMetadataRule("Using Serial", UNIFIED, SERIAL),
            new ParserMetadataRule("Using Shenandoah", UNIFIED, SHENANDOAH),
    };

    public static GCLogParser getParser(BufferedReader br)
    {
        GCLogParsingMetadata metadata = getMetadata(br);
        return createParser(metadata);
    }

    private static GCLogParsingMetadata getMetadata(BufferedReader br)
    {
        GCLogParsingMetadata result = new GCLogParsingMetadata(GCCollectorType.UNKNOWN, GCLogStyle.UNKNOWN);
        try
        {
            complete:
            for (int i = 0; i < MAX_ATTEMPT_LINE; i++)
            {
                String line = br.readLine();
                if (line == null)
                {
                    break;
                }
                // Don't read this line in case users are using wrong arguments
                if (line.startsWith("CommandLine flags: "))
                {
                    continue;
                }
                for (ParserMetadataRule rule : rules)
                {
                    if (!line.contains(rule.text))
                    {
                        continue;
                    }
                    if (result.style() == GCLogStyle.UNKNOWN && result.collector() == GCCollectorType.UNKNOWN)
                    {
                        result = new GCLogParsingMetadata(rule.collector, rule.style);
                    }
                    if (result.collector() != GCCollectorType.UNKNOWN && result.style() != GCLogStyle.UNKNOWN)
                    {
                        break complete;
                    }
                }
            }
        }
        catch (Exception e)
        {
            // do nothing, hopefully we have got enough information
        }
        return result;
    }

    private static GCLogParser createParser(GCLogParsingMetadata metadata)
    {
        AbstractGCLogParser parser = null;
        if (metadata.style() == PRE_UNIFIED)
        {
            parser = switch (metadata.collector())
            {
                case SERIAL, PARALLEL, CMS, UNKNOWN -> new PreUnifiedGenerationalGCLogParser();
                case G1 -> new PreUnifiedG1GCLogParser();
                default -> parser;
            };
        }
        else if (metadata.style() == UNIFIED)
        {
            parser = switch (metadata.collector())
            {
                case SERIAL, PARALLEL, CMS, UNKNOWN -> new UnifiedGenerationalGCLogParser();
                case G1 -> new UnifiedG1GCLogParser();
                case ZGC -> new UnifiedZGCLogParser();
                case SHENANDOAH, GENSHEN, GENZ, EPSILON ->
                        throw new CommonException("GC type not supported: " + metadata.collector().getName());
            };
        }
        if (parser == null)
        {
            throw new CommonException("Can not recognize file format. Please check if the file is a gc LOG.");
        }
        parser.setMetadata(metadata);
        return parser;
    }

    private record ParserMetadataRule(String text, GCLogStyle style, GCCollectorType collector)
    {
    }
}
