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
import org.eclipse.jifa.jfr.model.symbol.SymbolTable;

import org.eclipse.jifa.jfr.common.EventConstant;
import org.openjdk.jmc.common.*;
import org.openjdk.jmc.common.item.*;
import org.openjdk.jmc.common.unit.*;
import org.openjdk.jmc.common.util.FormatToolkit;
import org.openjdk.jmc.common.util.LabeledIdentifier;

import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.eclipse.jifa.jfr.common.EventConstant.ACTIVE_SETTING;

public class RecordedEvent
{
    private static final long NANOS_PER_SECOND = 1000_000_000L;

    private final IItem item;

    private long startTime;
    private long endTime = -1;

    private RecordedStackTrace stackTrace;

    private RecordedThread thread;

    private EventType eventType;

    private ActiveSetting activeSetting = null;

    public static RecordedEvent newInstance(IItem item, SymbolTable<SymbolBase> symbols)
    {
        RecordedEvent event = new RecordedEvent(item);
        event.init(symbols);
        return event;
    }

    private RecordedEvent(IItem item)
    {
        this.item = item;
    }

    public IItem getItem()
    {
        return item;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    public RecordedStackTrace getStackTrace()
    {
        return stackTrace;
    }

    public void setStackTrace(RecordedStackTrace stackTrace)
    {
        this.stackTrace = stackTrace;
    }

    public RecordedThread getThread()
    {
        return thread;
    }

    public void setThread(RecordedThread thread)
    {
        this.thread = thread;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public void setEventType(EventType eventType)
    {
        this.eventType = eventType;
    }

    public ActiveSetting getActiveSetting()
    {
        return activeSetting;
    }

    public void setActiveSetting(ActiveSetting activeSetting)
    {
        this.activeSetting = activeSetting;
    }

    private void init(SymbolTable<SymbolBase> symbols)
    {
        IMCThread imcThread = getValue("eventThread");
        if (imcThread == null)
        {
            imcThread = getValue("sampledThread");
        }

        if (imcThread != null)
        {
            thread = new RecordedThread(imcThread);
        }

        Object value = getValue("startTime");
        if (value instanceof IQuantity v)
        {
            startTime = toNanos(v, UnitLookup.EPOCH_NS);
        }

        IType<IItem> itemType = ItemToolkit.getItemType(item);
        String itemTypeId = itemType.getIdentifier();

        // fix for JDK Mission Control lib
        if ((itemTypeId.startsWith(EventConstant.EXECUTION_SAMPLE) && !itemTypeId.equals(
                EventConstant.EXECUTION_SAMPLE)))
        {
            itemTypeId = EventConstant.EXECUTION_SAMPLE;
        }
        else if (itemTypeId.startsWith(EventConstant.OBJECT_ALLOCATION_OUTSIDE_TLAB)
                && !itemTypeId.equals(EventConstant.OBJECT_ALLOCATION_OUTSIDE_TLAB))
        {
            itemTypeId = EventConstant.OBJECT_ALLOCATION_OUTSIDE_TLAB;
        }
        else if (itemTypeId.startsWith(EventConstant.OBJECT_ALLOCATION_IN_NEW_TLAB)
                && !itemTypeId.equals(EventConstant.OBJECT_ALLOCATION_IN_NEW_TLAB))
        {
            itemTypeId = EventConstant.OBJECT_ALLOCATION_IN_NEW_TLAB;
        }

        this.eventType = new EventType(itemTypeId);

        IMCStackTrace s = getValue("stackTrace");
        if (s != null)
        {
            List<? extends IMCFrame> frames = s.getFrames();
            RecordedStackTrace st = new RecordedStackTrace();
            List<RecordedFrame> list = new ArrayList<>();
            frames.forEach(frame ->
            {
                IMCMethod method = frame.getMethod();

                RecordedMethod m = new RecordedMethod();
                m.setDescriptor(method.getFormalDescriptor());
                m.setModifiers(method.getModifier() == null ? 0 : method.getModifier());

                IMCType type = method.getType();
                RecordedClass c = new RecordedClass();
                c.setName(type.getTypeName());
                c.setPackageName(type.getPackage().getName());
                if (symbols.isContains(c))
                {
                    c = (RecordedClass)symbols.get(c);
                }
                else
                {
                    symbols.put(c);
                }
                m.setType(c);
                m.setName(method.getMethodName());
                if (symbols.isContains(m))
                {
                    m = (RecordedMethod)symbols.get(m);
                }
                else
                {
                    symbols.put(m);
                }

                RecordedFrame f = new RecordedFrame();
                f.setMethod(m);
                f.setBytecodeIndex(frame.getBCI());
                f.setType(frame.getType().getName());

                if (symbols.isContains(f))
                {
                    f = (RecordedFrame)symbols.get(f);
                }
                else
                {
                    symbols.put(f);
                }

                list.add(f);
            });
            st.setFrames(list);
            if (symbols.isContains(st))
            {
                st = (RecordedStackTrace)symbols.get(st);
            }
            else
            {
                symbols.put(st);
            }
            stackTrace = st;
        }

        if (ACTIVE_SETTING.equals(itemType.getIdentifier()))
        {
            String eventName = null;
            long eventId = -1;
            String settingName = null;
            for (Map.Entry<IAccessorKey<?>, ? extends IDescribable> entry : itemType.getAccessorKeys().entrySet())
            {
                if (entry.getKey().getIdentifier().equals("settingFor"))
                {
                    IMemberAccessor<?, IItem> accessor = itemType.getAccessor(entry.getKey());
                    LabeledIdentifier id = (LabeledIdentifier)accessor.getMember(item);
                    eventName = id.getInterfaceId();
                    eventId = id.getImplementationId();
                    continue;
                }
                if (entry.getKey().getIdentifier().equals("name"))
                {
                    IMemberAccessor<?, IItem> accessor = itemType.getAccessor(entry.getKey());
                    settingName = (String)accessor.getMember(item);
                }
                if (eventName != null && settingName != null && eventId >= 0)
                {
                    break;
                }
            }
            if (eventName != null && settingName != null && eventId >= 0)
            {
                this.activeSetting = new ActiveSetting(eventName, eventId, settingName);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public final <T> T getValue(String name)
    {
        IType<IItem> itemType = ItemToolkit.getItemType(item);
        for (Map.Entry<IAccessorKey<?>, ? extends IDescribable> entry : itemType.getAccessorKeys().entrySet())
        {
            IMemberAccessor<?, IItem> accessor = itemType.getAccessor(entry.getKey());
            if (entry.getKey().getIdentifier().equals(name))
            {
                return (T)accessor.getMember(item);
            }
        }
        return null;
    }

    public Duration getDuration()
    {
        return Duration.ofNanos(getDurationNano());
    }

    public long getDurationNano()
    {
        return getEndTimeNanos() - startTime;
    }

    public String getString(String name)
    {
        return getValue(name);
    }

    public int getInt(String name)
    {
        Number n = getValue(name);
        if (n != null)
        {
            return n.intValue();
        }
        else
        {
            return 0;
        }
    }

    public float getFloat(String name)
    {
        Number n = getValue(name);
        if (n != null)
        {
            return n.floatValue();
        }
        else
        {
            return 0;
        }
    }

    public long getLong(String name)
    {
        Number n = getValue(name);
        if (n != null)
        {
            return n.longValue();
        }
        else
        {
            return 0;
        }
    }

    public RecordedThread getThread(String key)
    {
        IMCThread imcThread = getValue(key);
        return imcThread == null ? null : new RecordedThread(imcThread);
    }

    public Instant getStartTime()
    {
        return Instant.ofEpochSecond(startTime / NANOS_PER_SECOND, startTime % NANOS_PER_SECOND);
    }

    public Instant getEndTime()
    {
        long endTime = getEndTimeNanos();
        return Instant.ofEpochSecond(endTime / NANOS_PER_SECOND, endTime % NANOS_PER_SECOND);
    }

    public long getStartTimeNanos()
    {
        return startTime;
    }

    private long getEndTimeNanos()
    {
        if (endTime < 0)
        {
            Object value = getValue("duration");
            if (value instanceof IQuantity quantity)
            {
                endTime = startTime + toNanos(quantity, UnitLookup.NANOSECOND);
            }
            else
            {
                throw new RuntimeException("should not reach here");
            }
        }

        return endTime;
    }

    private static long toNanos(IQuantity value, IUnit targetUnit)
    {
        IScalarAffineTransform t = value.getUnit().valueTransformTo(targetUnit);
        return t.targetValue(value.longValue());
    }

    private static String stringify(String indent, Object value)
    {
        if (value instanceof IMCMethod imcMethod)
        {
            return indent + stringifyMethod(imcMethod);
        }
        if (value instanceof IMCType imcType)
        {
            return indent + stringifyType(imcType);
        }
        if (value instanceof IQuantity iQuantity)
        {
            return iQuantity.persistableString();
        }

        if (value instanceof IDescribable iDescribable)
        {
            String name = iDescribable.getName();
            return (name != null) ? name : value.toString();
        }
        if (value == null)
        {
            return "null";
        }
        if (value.getClass().isArray())
        {
            StringBuilder buffer = new StringBuilder();
            Object[] values = (Object[])value;
            buffer.append(" [").append(values.length).append("]");
            for (Object o : values)
            {
                buffer.append(indent);
                buffer.append(stringify(indent + "  ", o));
            }
            return buffer.toString();
        }
        return value.toString();
    }

    private static String stringifyType(IMCType type)
    {
        return type.getPackage() == null ?
                type.getTypeName() : formatPackage(type.getPackage()) + "." + type.getTypeName();
    }

    private static String stringifyMethod(IMCMethod method)
    {
        StringBuilder buffer = new StringBuilder();
        Integer modifier = method.getModifier();
        buffer.append(formatPackage(method.getType().getPackage()));
        buffer.append(".");
        buffer.append(method.getType().getTypeName());
        buffer.append("#");
        buffer.append(method.getMethodName());
        buffer.append(method.getFormalDescriptor());
        buffer.append("\"");
        if (modifier != null)
        {
            buffer.append(" modifier=\"");
            buffer.append(Modifier.toString(method.getModifier()));
            buffer.append("\"");
        }
        return buffer.toString();
    }

    private static String formatPackage(IMCPackage mcPackage)
    {
        return FormatToolkit.getPackage(mcPackage);
    }

    public record ActiveSetting(String eventType, Long eventId, String settingName)
    {
        @Override
        public boolean equals(Object b)
        {
            if (!(b instanceof ActiveSetting other))
            {
                return false;
            }

            return Objects.equals(eventType, other.eventType())
                    && Objects.equals(eventId, other.eventId())
                    && Objects.equals(settingName, other.settingName());
        }

        @Override
        public int hashCode()
        {
            int result = eventType != null ? eventType.hashCode() : 0;
            result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
            result = 31 * result + (settingName != null ? settingName.hashCode() : 0);
            return result;
        }
    }
}
