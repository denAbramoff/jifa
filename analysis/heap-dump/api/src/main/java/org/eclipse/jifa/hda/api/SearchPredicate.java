/********************************************************************************
 * Copyright (c) 2020, 2023 Contributors to the Eclipse Foundation
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

package org.eclipse.jifa.hda.api;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SearchPredicate
{
    private static final Logger LOG = LoggerFactory.getLogger(SearchPredicate.class);

    public static <T extends Searchable> Predicate<T> createPredicate(String searchText, SearchType searchType)
    {
        if (searchText == null || searchType == null || searchText.isEmpty())
        {
            return (T record) -> true;
        }

        Predicate<T> pred;

        try
        {
            switch (searchType)
            {
            case BY_NAME:
            case BY_CONTEXT_CLASSLOADER_NAME:
            {
                Pattern p = Pattern.compile(searchText);
                pred = (T record) -> p.matcher(((String)record.getBySearchType(searchType))).matches();
                break;
            }

            case BY_PERCENT:
            {
                String prefix = extractPrefix(searchText);
                double num = Double.parseDouble(extractNumberText(searchText)) / 100.0;
                pred = switch (prefix)
                {
                    case "==" -> (T record) -> Double.compare((double)record.getBySearchType(searchType), num) == 0;
                    case ">=" -> (T record) -> (double)record.getBySearchType(searchType) >= num;
                    case "<=" -> (T record) -> (double)record.getBySearchType(searchType) <= num;
                    case ">" -> (T record) -> (double)record.getBySearchType(searchType) > num;
                    case "<" -> (T record) -> (double)record.getBySearchType(searchType) < num;
                    case "!=" -> (T record) -> Double.compare((double)record.getBySearchType(searchType), num) != 0;
                    default -> (T record) -> false;
                };
                break;
            }

            default:
            {
                final String prefix = extractPrefix(searchText);
                final long num = Long.parseLong(extractNumberText(searchText));
                pred = switch (prefix)
                {
                    case "==" -> (T record) -> (long)record.getBySearchType(searchType) == num;
                    case ">=" -> (T record) -> (long)record.getBySearchType(searchType) >= num;
                    case "<=" -> (T record) -> (long)record.getBySearchType(searchType) <= num;
                    case ">" -> (T record) -> (long)record.getBySearchType(searchType) > num;
                    case "<" -> (T record) -> (long)record.getBySearchType(searchType) < num;
                    case "!=" -> (T record) -> (long)record.getBySearchType(searchType) != num;
                    default -> (T record) -> false;
                };
                break;
            }
            }
        }
        catch (RuntimeException ignored)
        {
            LOG.debug("unexpected exception generating search `" + searchText + "` with type " + searchType.name());
            pred = (T record) -> false;
        }

        // wrap for error handling
        final Predicate<T> unwrapped = pred;
        return (T record) ->
        {
            try
            {
                return unwrapped.test(record);
            }
            catch (Throwable ignored)
            {
                LOG.debug("unexpected exception when search `" + searchText + "` with type " + searchType.name());
                return false;
            }
        };
    }

    private static String extractPrefix(String text)
    {
        if (StringUtils.isNumeric(text))
        {
            return "==";
        }

        String prefix = "";
        prefix += text.charAt(0);
        if (text.charAt(1) == '=')
        {
            prefix += "=";
            return prefix;
        }

        return prefix;
    }

    private static String extractNumberText(String text)
    {
        for (int i = 0; i < 3; i++)
        {
            if (Character.isDigit(text.charAt(i)))
            {
                return text.substring(i);
            }
        }
        return "";
    }
}
