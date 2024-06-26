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
package org.eclipse.jifa.jfr.common;

public enum ProfileDimension
{
    CPU(1, "CPU Time"),
    CPU_SAMPLE(1 << 1, "CPU Sample"),
    WALL_CLOCK(1 << 2, "Wall Clock"),
    NATIVE_EXECUTION_SAMPLES(1 << 3, "Native Execution Samples"),
    ALLOC(1 << 4, "Allocation Count"),
    MEM(1 << 5, "Allocated Memory"),

    FILE_IO_TIME(1 << 6, "File IO Time"),
    FILE_READ_SIZE(1 << 7, "File Read Size"),
    FILE_WRITE_SIZE(1 << 8, "File Write Size"),

    SOCKET_READ_SIZE(1 << 9, "Socket Read Size"),
    SOCKET_WRITE_SIZE(1 << 10, "Socket Write Size"),
    SOCKET_READ_TIME(1 << 11, "Socket Read Time"),
    SOCKET_WRITE_TIME(1 << 12, "Socket Write Time"),

    SYNCHRONIZATION(1 << 13, "Synchronization"),
    THREAD_PARK(1 << 14, "Thread Park"),

    CLASS_LOAD_COUNT(1 << 15, "Class Load Count"),
    CLASS_LOAD_WALL_TIME(1 << 16, "Class Load Wall Time"),

    THREAD_SLEEP(1 << 17, "Thread Sleep Time"),

    PROBLEMS(1 << 20, "Problem");

    private final int value;

    private final String key;

    private final String desc;

    ProfileDimension(int v, String key)
    {
        this.value = v;
        this.key = key;
        this.desc = key;
    }

    public int getValue()
    {
        return value;
    }

    public String getKey()
    {
        return key;
    }

    public String getDesc()
    {
        return desc;
    }

    public static ProfileDimension of(String key)
    {
        for (ProfileDimension f : ProfileDimension.values())
        {
            if (f.key.equalsIgnoreCase(key))
            {
                return f;
            }
        }
        throw new RuntimeException("invalid profile dimension key [" + key + "]");
    }

    public boolean active(int dimensions)
    {
        return (dimensions & this.value) != 0;
    }

    public static int of(ProfileDimension... dimensions)
    {
        int r = 0;
        for (ProfileDimension dimension : dimensions)
        {
            r |= dimension.value;
        }
        return r;
    }
}
