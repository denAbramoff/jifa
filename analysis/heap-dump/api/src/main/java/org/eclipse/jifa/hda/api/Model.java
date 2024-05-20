/********************************************************************************
 * Copyright (c) 2021, 2023 Contributors to the Eclipse Foundation
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

import org.eclipse.jifa.common.domain.exception.ShouldNotReachHereException;
import org.eclipse.jifa.common.domain.vo.PageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface Model
{
    interface DominatorTree
    {
        interface ItemType
        {
            int CLASS = 1;
            int CLASS_LOADER = 2;
            int SUPER_CLASS = 5;
            int PACKAGE = 6;
        }

        enum Grouping
        {

            NONE,

            BY_CLASS,

            BY_CLASSLOADER,

            BY_PACKAGE;
        }

        class Item
        {
            public String label;

            public String prefix;

            public String suffix;

            public int objectId;

            public int objectType;

            public boolean gCRoot;

            public long shallowSize;

            public long retainedSize;

            public double percent;

            public boolean isObjType = true;

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public String getPrefix()
            {
                return prefix;
            }

            public void setPrefix(String prefix)
            {
                this.prefix = prefix;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public int getObjectType()
            {
                return objectType;
            }

            public void setObjectType(int objectType)
            {
                this.objectType = objectType;
            }

            public boolean isgCRoot()
            {
                return gCRoot;
            }

            public void setgCRoot(boolean gCRoot)
            {
                this.gCRoot = gCRoot;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }

            public double getPercent()
            {
                return percent;
            }

            public void setPercent(double percent)
            {
                this.percent = percent;
            }

            public boolean isObjType()
            {
                return isObjType;
            }

            public void setObjType(boolean objType)
            {
                isObjType = objType;
            }
        }

        class ClassLoaderItem extends Item implements Searchable
        {
            private static Map<String, Comparator<ClassLoaderItem>> sortTable =
                    new SortTableGenerator<ClassLoaderItem>()
                            .add("id", ClassLoaderItem::getObjectId)
                            .add("shallowHeap", ClassLoaderItem::getShallowSize)
                            .add("retainedHeap", ClassLoaderItem::getRetainedSize)
                            .add("percent", ClassLoaderItem::getPercent)
                            .add("Objects", ClassLoaderItem::getObjects)
                            .build();

            public long objects;
            private int[] objectIds;

            public static Map<String, Comparator<ClassLoaderItem>> getSortTable()
            {
                return sortTable;
            }

            public static void setSortTable(
                    Map<String, Comparator<ClassLoaderItem>> sortTable)
            {
                ClassLoaderItem.sortTable = sortTable;
            }

            public long getObjects()
            {
                return objects;
            }

            public void setObjects(long objects)
            {
                this.objects = objects;
            }

            public int[] getObjectIds()
            {
                return objectIds;
            }

            public void setObjectIds(int[] objectIds)
            {
                this.objectIds = objectIds;
            }

            public static Comparator<ClassLoaderItem> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_PERCENT -> getPercent();
                    case BY_OBJ_NUM -> getObjects();
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }

        class ClassItem extends Item implements Searchable
        {
            private static Map<String, Comparator<ClassItem>> sortTable = new SortTableGenerator<ClassItem>()
                    .add("id", ClassItem::getObjectId)
                    .add("shallowHeap", ClassItem::getShallowSize)
                    .add("retainedHeap", ClassItem::getRetainedSize)
                    .add("percent", ClassItem::getPercent)
                    .add("Objects", ClassItem::getObjects)
                    .build();
            private int objects;
            private int[] objectIds;

            public static Map<String, Comparator<ClassItem>> getSortTable()
            {
                return sortTable;
            }

            public static void setSortTable(
                    Map<String, Comparator<ClassItem>> sortTable)
            {
                ClassItem.sortTable = sortTable;
            }

            public int getObjects()
            {
                return objects;
            }

            public void setObjects(int objects)
            {
                this.objects = objects;
            }

            public int[] getObjectIds()
            {
                return objectIds;
            }

            public void setObjectIds(int[] objectIds)
            {
                this.objectIds = objectIds;
            }

            public static Comparator<ClassItem> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_PERCENT -> getPercent();
                    case BY_OBJ_NUM -> getObjects();
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }

        class DefaultItem extends Item implements Searchable
        {
            private static Map<String, Comparator<DefaultItem>> sortTable = new SortTableGenerator<DefaultItem>()
                    .add("id", DefaultItem::getObjectId)
                    .add("shallowHeap", DefaultItem::getShallowSize)
                    .add("retainedHeap", DefaultItem::getRetainedSize)
                    .add("percent", DefaultItem::getPercent)
                    .build();

            public static Comparator<DefaultItem> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_PERCENT -> getPercent();
                    case BY_OBJ_NUM -> null;
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }

        class PackageItem extends Item implements Searchable
        {
            private static Map<String, Comparator<PackageItem>> sortTable = new SortTableGenerator<PackageItem>()
                    .add("id", PackageItem::getObjectId)
                    .add("shallowHeap", PackageItem::getShallowSize)
                    .add("retainedHeap", PackageItem::getRetainedSize)
                    .add("percent", PackageItem::getPercent)
                    .add("Objects", PackageItem::getObjects)
                    .build();
            private long objects;
            private int[] objectIds;

            public static Map<String, Comparator<PackageItem>> getSortTable()
            {
                return sortTable;
            }

            public static void setSortTable(
                    Map<String, Comparator<PackageItem>> sortTable)
            {
                PackageItem.sortTable = sortTable;
            }

            public long getObjects()
            {
                return objects;
            }

            public void setObjects(long objects)
            {
                this.objects = objects;
            }

            public int[] getObjectIds()
            {
                return objectIds;
            }

            public void setObjectIds(int[] objectIds)
            {
                this.objectIds = objectIds;
            }

            public static Comparator<PackageItem> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_PERCENT -> getPercent();
                    case BY_OBJ_NUM -> getObjects();
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }
    }

    interface Histogram
    {
        enum Grouping
        {
            BY_CLASS,
            BY_SUPERCLASS,
            BY_CLASSLOADER,
            BY_PACKAGE;
        }

        interface ItemType
        {
            int CLASS = 1;
            int CLASS_LOADER = 2;
            int SUPER_CLASS = 5;
            int PACKAGE = 6;
        }

        class Item implements Searchable
        {
            private static Map<String, Comparator<Item>> sortTable = new SortTableGenerator<Item>()
                    .add("id", Item::getObjectId)
                    .add("numberOfObjects", Item::getNumberOfObjects)
                    .add("shallowSize", Item::getShallowSize)
                    .add("retainedSize", i -> Math.abs(i.getRetainedSize()))
                    .build();
            public long numberOfObjects;
            public long shallowSize;
            public long retainedSize;
            public String label;
            public int objectId;
            public int type;

            public Item()
            {
            }

            public Item(int objectId, String label, int type, long numberOfObjects, long shallowSize,
                    long retainedSize)
            {
                this.objectId = objectId;
                this.label = label;
                this.type = type;
                this.numberOfObjects = numberOfObjects;
                this.shallowSize = shallowSize;
                this.retainedSize = retainedSize;
            }

            public static Map<String, Comparator<Item>> getSortTable()
            {
                return sortTable;
            }

            public static void setSortTable(
                    Map<String, Comparator<Item>> sortTable)
            {
                Item.sortTable = sortTable;
            }

            public long getNumberOfObjects()
            {
                return numberOfObjects;
            }

            public void setNumberOfObjects(long numberOfObjects)
            {
                this.numberOfObjects = numberOfObjects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public static Comparator<Item> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_OBJ_NUM -> getNumberOfObjects();
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }
    }

    interface DuplicatedClass
    {

        class ClassItem implements Searchable
        {
            public String label;

            public int count;

            public int index;

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getCount()
            {
                return count;
            }

            public void setCount(int count)
            {
                this.count = count;
            }

            public int getIndex()
            {
                return index;
            }

            public void setIndex(int index)
            {
                this.index = index;
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getLabel();
                    case BY_CLASSLOADER_COUNT -> (long)getCount();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }

        class ClassLoaderItem
        {
            public String label;
            public String suffix;
            public int definedClassesCount;
            public int instantiatedObjectsCount;
            public int objectId;
            public boolean gCRoot;

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public int getDefinedClassesCount()
            {
                return definedClassesCount;
            }

            public void setDefinedClassesCount(int definedClassesCount)
            {
                this.definedClassesCount = definedClassesCount;
            }

            public int getInstantiatedObjectsCount()
            {
                return instantiatedObjectsCount;
            }

            public void setInstantiatedObjectsCount(int instantiatedObjectsCount)
            {
                this.instantiatedObjectsCount = instantiatedObjectsCount;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public boolean isgCRoot()
            {
                return gCRoot;
            }

            public void setgCRoot(boolean gCRoot)
            {
                this.gCRoot = gCRoot;
            }
        }
    }

    interface Thread
    {
        class Summary
        {
            public long totalSize;
            public long shallowHeap;
            public long retainedHeap;

            public long getTotalSize()
            {
                return totalSize;
            }

            public void setTotalSize(long totalSize)
            {
                this.totalSize = totalSize;
            }

            public long getShallowHeap()
            {
                return shallowHeap;
            }

            public void setShallowHeap(long shallowHeap)
            {
                this.shallowHeap = shallowHeap;
            }

            public long getRetainedHeap()
            {
                return retainedHeap;
            }

            public void setRetainedHeap(long retainedHeap)
            {
                this.retainedHeap = retainedHeap;
            }
        }

        class Item implements Searchable
        {
            public static Map<String, Comparator<Item>> sortTable = new SortTableGenerator<Item>()
                    .add("id", Item::getObjectId)
                    .add("shallowHeap", Item::getShallowSize)
                    .add("retainedHeap", Item::getRetainedSize)
                    .add("daemon", Item::isDaemon)
                    .add("contextClassLoader", Item::getContextClassLoader)
                    .add("name", Item::getName)
                    .build();
            public int objectId;
            public String object;
            public String name;
            public long shallowSize;
            public long retainedSize;
            public String contextClassLoader;
            public boolean hasStack;
            public boolean daemon;

            public Item(int objectId, String object, String name, long shallowSize, long retainedSize,
                    String contextClassLoader, boolean hasStack, boolean daemon)
            {
                this.objectId = objectId;
                this.object = object;
                this.name = name;
                this.shallowSize = shallowSize;
                this.retainedSize = retainedSize;
                this.contextClassLoader = contextClassLoader;
                this.hasStack = hasStack;
                this.daemon = daemon;
            }

            public Item()
            {
            }

            public static Map<String, Comparator<Item>> getSortTable()
            {
                return sortTable;
            }

            public static void setSortTable(
                    Map<String, Comparator<Item>> sortTable)
            {
                Item.sortTable = sortTable;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getObject()
            {
                return object;
            }

            public void setObject(String object)
            {
                this.object = object;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }

            public String getContextClassLoader()
            {
                return contextClassLoader;
            }

            public void setContextClassLoader(String contextClassLoader)
            {
                this.contextClassLoader = contextClassLoader;
            }

            public boolean isHasStack()
            {
                return hasStack;
            }

            public void setHasStack(boolean hasStack)
            {
                this.hasStack = hasStack;
            }

            public boolean isDaemon()
            {
                return daemon;
            }

            public void setDaemon(boolean daemon)
            {
                this.daemon = daemon;
            }

            public static Comparator<Item> sortBy(String field, boolean ascendingOrder)
            {
                return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
            }

            @Override
            public Object getBySearchType(SearchType type)
            {
                return switch (type)
                {
                    case BY_NAME -> getName();
                    case BY_SHALLOW_SIZE -> getShallowSize();
                    case BY_RETAINED_SIZE -> getRetainedSize();
                    case BY_CONTEXT_CLASSLOADER_NAME -> getContextClassLoader();
                    default -> throw new ShouldNotReachHereException();
                };
            }
        }

        class LocalVariable extends JavaObject
        {
        }

        class StackFrame
        {
            public String stack;
            public boolean hasLocal;
            public boolean firstNonNativeFrame;
            public long maxLocalsRetainedSize;

            public StackFrame(String stack, boolean hasLocal, long maxLocalsRetainedSize)
            {
                this.stack = stack;
                this.hasLocal = hasLocal;
                this.maxLocalsRetainedSize = maxLocalsRetainedSize;
            }

            public String getStack()
            {
                return stack;
            }

            public void setStack(String stack)
            {
                this.stack = stack;
            }

            public boolean isHasLocal()
            {
                return hasLocal;
            }

            public void setHasLocal(boolean hasLocal)
            {
                this.hasLocal = hasLocal;
            }

            public boolean isFirstNonNativeFrame()
            {
                return firstNonNativeFrame;
            }

            public void setFirstNonNativeFrame(boolean firstNonNativeFrame)
            {
                this.firstNonNativeFrame = firstNonNativeFrame;
            }

            public long getMaxLocalsRetainedSize()
            {
                return maxLocalsRetainedSize;
            }

            public void setMaxLocalsRetainedSize(long maxLocalsRetainedSize)
            {
                this.maxLocalsRetainedSize = maxLocalsRetainedSize;
            }
        }
    }

    interface CalciteSQLResult
    {
        int TREE = 1;

        int TABLE = 2;

        int TEXT = 3;

        int getType();

        class TableResult implements CalciteSQLResult
        {
            public int type = TABLE;

            public List<String> columns;

            public PageView<Entry> pv;

            public TableResult(List<String> columns, PageView<Entry> pv)
            {
                this.columns = columns;
                this.pv = pv;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public List<String> getColumns()
            {
                return columns;
            }

            public void setColumns(List<String> columns)
            {
                this.columns = columns;
            }

            public PageView<Entry> getPv()
            {
                return pv;
            }

            public void setPv(
                    PageView<Entry> pv)
            {
                this.pv = pv;
            }

            public static class Entry
            {
                public int objectId;

                public List<Object> values;

                public Entry(int objectId, List<Object> values)
                {
                    this.objectId = objectId;
                    this.values = values;
                }

                public int getObjectId()
                {
                    return objectId;
                }

                public void setObjectId(int objectId)
                {
                    this.objectId = objectId;
                }

                public List<Object> getValues()
                {
                    return values;
                }

                public void setValues(List<Object> values)
                {
                    this.values = values;
                }
            }

        }

        class TextResult implements CalciteSQLResult
        {
            public int type = CalciteSQLResult.TEXT;

            public String text;

            public TextResult(String text)
            {
                this.text = text;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public String getText()
            {
                return text;
            }

            public void setText(String text)
            {
                this.text = text;
            }
        }

        class TreeResult implements CalciteSQLResult
        {
            public PageView<JavaObject> pv;

            public int type = TREE;

            public TreeResult(PageView<JavaObject> pv)
            {
                this.pv = pv;
            }

            public PageView<JavaObject> getPv()
            {
                return pv;
            }

            public void setPv(PageView<JavaObject> pv)
            {
                this.pv = pv;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }
        }

    }

    interface OQLResult
    {
        int TREE = 1;

        int TABLE = 2;

        int TEXT = 3;

        int getType();

        class TableResult implements OQLResult
        {
            public int type = TABLE;

            public List<String> columns;

            public PageView<Entry> pv;

            public TableResult(List<String> columns, PageView<Entry> pv)
            {
                this.columns = columns;
                this.pv = pv;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public List<String> getColumns()
            {
                return columns;
            }

            public void setColumns(List<String> columns)
            {
                this.columns = columns;
            }

            public PageView<Entry> getPv()
            {
                return pv;
            }

            public void setPv(PageView<Entry> pv)
            {
                this.pv = pv;
            }

            public static class Entry
            {
                public int objectId;

                public List<Object> values;

                public Entry(int objectId, List<Object> values)
                {
                    this.objectId = objectId;
                    this.values = values;
                }

                public int getObjectId()
                {
                    return objectId;
                }

                public void setObjectId(int objectId)
                {
                    this.objectId = objectId;
                }

                public List<Object> getValues()
                {
                    return values;
                }

                public void setValues(List<Object> values)
                {
                    this.values = values;
                }
            }

        }

        class TextResult implements OQLResult
        {
            public int type = OQLResult.TEXT;

            public String text;

            public TextResult(String text)
            {
                this.text = text;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public String getText()
            {
                return text;
            }

            public void setText(String text)
            {
                this.text = text;
            }
        }

        class TreeResult implements OQLResult
        {
            public PageView<JavaObject> pv;

            public int type = TREE;

            public TreeResult(PageView<JavaObject> pv)
            {
                this.pv = pv;
            }

            public PageView<JavaObject> getPv()
            {
                return pv;
            }

            public void setPv(PageView<JavaObject> pv)
            {
                this.pv = pv;
            }

            @Override
            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }
        }

    }

    interface GCRootPath
    {
        List<String> EXCLUDES = List.of("java.lang.ref.WeakReference:referent",
                "java.lang.ref.SoftReference:referent");

        enum Grouping
        {
            FROM_GC_ROOTS,
            FROM_GC_ROOTS_BY_CLASS,
            FROM_OBJECTS_BY_CLASS
        }

        class MergePathToGCRootsTreeNode
        {
            public int objectId;
            public String className;
            public int refObjects;
            public long shallowHeap;
            public long refShallowHeap;
            public long retainedHeap;
            public String suffix;
            public int objectType;
            public boolean gCRoot;

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getClassName()
            {
                return className;
            }

            public void setClassName(String className)
            {
                this.className = className;
            }

            public int getRefObjects()
            {
                return refObjects;
            }

            public void setRefObjects(int refObjects)
            {
                this.refObjects = refObjects;
            }

            public long getShallowHeap()
            {
                return shallowHeap;
            }

            public void setShallowHeap(long shallowHeap)
            {
                this.shallowHeap = shallowHeap;
            }

            public long getRefShallowHeap()
            {
                return refShallowHeap;
            }

            public void setRefShallowHeap(long refShallowHeap)
            {
                this.refShallowHeap = refShallowHeap;
            }

            public long getRetainedHeap()
            {
                return retainedHeap;
            }

            public void setRetainedHeap(long retainedHeap)
            {
                this.retainedHeap = retainedHeap;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public int getObjectType()
            {
                return objectType;
            }

            public void setObjectType(int objectType)
            {
                this.objectType = objectType;
            }

            public boolean isgCRoot()
            {
                return gCRoot;
            }

            public void setgCRoot(boolean gCRoot)
            {
                this.gCRoot = gCRoot;
            }
        }

        class Item
        {

            public Node tree;

            public int count;

            public boolean hasMore;

            public Node getTree()
            {
                return tree;
            }

            public void setTree(Node tree)
            {
                this.tree = tree;
            }

            public int getCount()
            {
                return count;
            }

            public void setCount(int count)
            {
                this.count = count;
            }

            public boolean isHasMore()
            {
                return hasMore;
            }

            public void setHasMore(boolean hasMore)
            {
                this.hasMore = hasMore;
            }
        }

        class Node extends JavaObject
        {
            public boolean origin;

            public List<Node> children = new ArrayList<>();

            public void addChild(Node child)
            {
                children.add(child);
            }

            public Node getChild(int objectId)
            {
                for (Node child : children)
                {
                    if (child.getObjectId() == objectId)
                    {
                        return child;
                    }
                }
                return null;
            }

            public boolean isOrigin()
            {
                return origin;
            }

            public void setOrigin(boolean origin)
            {
                this.origin = origin;
            }

            public List<Node> getChildren()
            {
                return children;
            }

            public void setChildren(List<Node> children)
            {
                this.children = children;
            }
        }
    }

    interface ClassReferrer
    {
        interface Type
        {
            int NEW = 0;
            int MIXED = 1;
            int OLD_FAD = 2;
        }

        class Item
        {

            public String label;

            public int objects;

            public long shallowSize;

            public int objectId;

            public int[] objectIds;

            public int type;

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getObjects()
            {
                return objects;
            }

            public void setObjects(int objects)
            {
                this.objects = objects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public int[] getObjectIds()
            {
                return objectIds;
            }

            public void setObjectIds(int[] objectIds)
            {
                this.objectIds = objectIds;
            }

            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }
        }
    }

    interface Comparison
    {

        class Summary
        {

            public int totalSize;

            public long objects;

            public long shallowSize;

            public int getTotalSize()
            {
                return totalSize;
            }

            public void setTotalSize(int totalSize)
            {
                this.totalSize = totalSize;
            }

            public long getObjects()
            {
                return objects;
            }

            public void setObjects(long objects)
            {
                this.objects = objects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }
        }

        class Item
        {

            public String className;

            public long objects;

            public long shallowSize;

            public String getClassName()
            {
                return className;
            }

            public void setClassName(String className)
            {
                this.className = className;
            }

            public long getObjects()
            {
                return objects;
            }

            public void setObjects(long objects)
            {
                this.objects = objects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }
        }
    }

    interface TheString
    {

        class Item
        {
            public int objectId;
            public String label;
            public long shallowSize;
            public long retainedSize;

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }
        }
    }

    interface GCRoot
    {

        class Item
        {

            public String className;

            public int objects;

            public int objectId;

            public long shallowSize;

            public long retainedSize;

            public String getClassName()
            {
                return className;
            }

            public void setClassName(String className)
            {
                this.className = className;
            }

            public int getObjects()
            {
                return objects;
            }

            public void setObjects(int objects)
            {
                this.objects = objects;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }
        }
    }

    interface DirectByteBuffer
    {

        class Item
        {

            public int objectId;

            public String label;

            public int position;

            public int limit;

            public int capacity;

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getPosition()
            {
                return position;
            }

            public void setPosition(int position)
            {
                this.position = position;
            }

            public int getLimit()
            {
                return limit;
            }

            public void setLimit(int limit)
            {
                this.limit = limit;
            }

            public int getCapacity()
            {
                return capacity;
            }

            public void setCapacity(int capacity)
            {
                this.capacity = capacity;
            }
        }

        class Summary
        {

            public int totalSize;

            public long position;

            public long limit;

            public long capacity;

            public int getTotalSize()
            {
                return totalSize;
            }

            public void setTotalSize(int totalSize)
            {
                this.totalSize = totalSize;
            }

            public long getPosition()
            {
                return position;
            }

            public void setPosition(long position)
            {
                this.position = position;
            }

            public long getLimit()
            {
                return limit;
            }

            public void setLimit(long limit)
            {
                this.limit = limit;
            }

            public long getCapacity()
            {
                return capacity;
            }

            public void setCapacity(long capacity)
            {
                this.capacity = capacity;
            }
        }
    }

    interface UnreachableObject
    {

        class Item
        {

            public int objectId;

            public String className;

            public int objects;

            public long shallowSize;

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getClassName()
            {
                return className;
            }

            public void setClassName(String className)
            {
                this.className = className;
            }

            public int getObjects()
            {
                return objects;
            }

            public void setObjects(int objects)
            {
                this.objects = objects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }
        }

        class Summary
        {

            public int totalSize;

            public int objects;

            public long shallowSize;

            public int getTotalSize()
            {
                return totalSize;
            }

            public void setTotalSize(int totalSize)
            {
                this.totalSize = totalSize;
            }

            public int getObjects()
            {
                return objects;
            }

            public void setObjects(int objects)
            {
                this.objects = objects;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }
        }
    }

    interface Overview
    {

        class BigObject
        {

            public String label;

            public int objectId;

            public double value;

            public String description;

            public BigObject(String label, int objectId, double value, String description)
            {
                this.label = label;
                this.objectId = objectId;
                this.value = value;
                this.description = description;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public double getValue()
            {
                return value;
            }

            public void setValue(double value)
            {
                this.value = value;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }
        }

        class Details
        {

            public String jvmInfo;

            public int identifierSize;

            public long creationDate;

            public int numberOfObjects;

            public int numberOfGCRoots;

            public int numberOfClasses;

            public int numberOfClassLoaders;

            public long usedHeapSize;

            public boolean generationInfoAvailable;

            public Details(String jvmInfo, int identifierSize, long creationDate, int numberOfObjects,
                    int numberOfGCRoots,
                    int numberOfClasses, int numberOfClassLoaders, long usedHeapSize,
                    boolean generationInfoAvailable)
            {
                this.jvmInfo = jvmInfo;
                this.identifierSize = identifierSize;
                this.creationDate = creationDate;
                this.numberOfObjects = numberOfObjects;
                this.numberOfGCRoots = numberOfGCRoots;
                this.numberOfClasses = numberOfClasses;
                this.numberOfClassLoaders = numberOfClassLoaders;
                this.usedHeapSize = usedHeapSize;
                this.generationInfoAvailable = generationInfoAvailable;
            }

            public String getJvmInfo()
            {
                return jvmInfo;
            }

            public void setJvmInfo(String jvmInfo)
            {
                this.jvmInfo = jvmInfo;
            }

            public int getIdentifierSize()
            {
                return identifierSize;
            }

            public void setIdentifierSize(int identifierSize)
            {
                this.identifierSize = identifierSize;
            }

            public long getCreationDate()
            {
                return creationDate;
            }

            public void setCreationDate(long creationDate)
            {
                this.creationDate = creationDate;
            }

            public int getNumberOfObjects()
            {
                return numberOfObjects;
            }

            public void setNumberOfObjects(int numberOfObjects)
            {
                this.numberOfObjects = numberOfObjects;
            }

            public int getNumberOfGCRoots()
            {
                return numberOfGCRoots;
            }

            public void setNumberOfGCRoots(int numberOfGCRoots)
            {
                this.numberOfGCRoots = numberOfGCRoots;
            }

            public int getNumberOfClasses()
            {
                return numberOfClasses;
            }

            public void setNumberOfClasses(int numberOfClasses)
            {
                this.numberOfClasses = numberOfClasses;
            }

            public int getNumberOfClassLoaders()
            {
                return numberOfClassLoaders;
            }

            public void setNumberOfClassLoaders(int numberOfClassLoaders)
            {
                this.numberOfClassLoaders = numberOfClassLoaders;
            }

            public long getUsedHeapSize()
            {
                return usedHeapSize;
            }

            public void setUsedHeapSize(long usedHeapSize)
            {
                this.usedHeapSize = usedHeapSize;
            }

            public boolean isGenerationInfoAvailable()
            {
                return generationInfoAvailable;
            }

            public void setGenerationInfoAvailable(boolean generationInfoAvailable)
            {
                this.generationInfoAvailable = generationInfoAvailable;
            }
        }
    }

    interface ClassLoader
    {

        class Item
        {

            public int objectId;

            public String prefix;

            public String label;

            public boolean classLoader;

            public boolean hasParent;

            public int definedClasses;

            public int numberOfInstances;

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public String getPrefix()
            {
                return prefix;
            }

            public void setPrefix(String prefix)
            {
                this.prefix = prefix;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public boolean isClassLoader()
            {
                return classLoader;
            }

            public void setClassLoader(boolean classLoader)
            {
                this.classLoader = classLoader;
            }

            public boolean isHasParent()
            {
                return hasParent;
            }

            public void setHasParent(boolean hasParent)
            {
                this.hasParent = hasParent;
            }

            public int getDefinedClasses()
            {
                return definedClasses;
            }

            public void setDefinedClasses(int definedClasses)
            {
                this.definedClasses = definedClasses;
            }

            public int getNumberOfInstances()
            {
                return numberOfInstances;
            }

            public void setNumberOfInstances(int numberOfInstances)
            {
                this.numberOfInstances = numberOfInstances;
            }
        }

        class Summary
        {

            public int totalSize;

            public int definedClasses;

            public int numberOfInstances;

            public int getTotalSize()
            {
                return totalSize;
            }

            public void setTotalSize(int totalSize)
            {
                this.totalSize = totalSize;
            }

            public int getDefinedClasses()
            {
                return definedClasses;
            }

            public void setDefinedClasses(int definedClasses)
            {
                this.definedClasses = definedClasses;
            }

            public int getNumberOfInstances()
            {
                return numberOfInstances;
            }

            public void setNumberOfInstances(int numberOfInstances)
            {
                this.numberOfInstances = numberOfInstances;
            }
        }
    }

    class LeakReport
    {

        public boolean useful;

        public String info;

        public String name;

        public List<Slice> slices;

        public List<Record> records;

        public boolean isUseful()
        {
            return useful;
        }

        public void setUseful(boolean useful)
        {
            this.useful = useful;
        }

        public String getInfo()
        {
            return info;
        }

        public void setInfo(String info)
        {
            this.info = info;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public List<Slice> getSlices()
        {
            return slices;
        }

        public void setSlices(List<Slice> slices)
        {
            this.slices = slices;
        }

        public List<Record> getRecords()
        {
            return records;
        }

        public void setRecords(List<Record> records)
        {
            this.records = records;
        }

        public static class Slice
        {

            public String label;

            public int objectId;

            public double value;

            public String desc;

            public Slice(String label, int objectId, double value, String desc)
            {
                this.label = label;
                this.objectId = objectId;
                this.value = value;
                this.desc = desc;
            }

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public double getValue()
            {
                return value;
            }

            public void setValue(double value)
            {
                this.value = value;
            }

            public String getDesc()
            {
                return desc;
            }

            public void setDesc(String desc)
            {
                this.desc = desc;
            }
        }

        public static class Record
        {

            public String name;

            public String desc;

            public int index;

            public List<ShortestPath> paths;

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getDesc()
            {
                return desc;
            }

            public void setDesc(String desc)
            {
                this.desc = desc;
            }

            public int getIndex()
            {
                return index;
            }

            public void setIndex(int index)
            {
                this.index = index;
            }

            public List<ShortestPath> getPaths()
            {
                return paths;
            }

            public void setPaths(List<ShortestPath> paths)
            {
                this.paths = paths;
            }
        }

        public static class ShortestPath
        {

            public String label;

            public long shallowSize;

            public long retainedSize;

            public int objectId;

            public int objectType;

            public boolean gCRoot;

            public List<ShortestPath> children;

            public String getLabel()
            {
                return label;
            }

            public void setLabel(String label)
            {
                this.label = label;
            }

            public long getShallowSize()
            {
                return shallowSize;
            }

            public void setShallowSize(long shallowSize)
            {
                this.shallowSize = shallowSize;
            }

            public long getRetainedSize()
            {
                return retainedSize;
            }

            public void setRetainedSize(long retainedSize)
            {
                this.retainedSize = retainedSize;
            }

            public int getObjectId()
            {
                return objectId;
            }

            public void setObjectId(int objectId)
            {
                this.objectId = objectId;
            }

            public int getObjectType()
            {
                return objectType;
            }

            public void setObjectType(int objectType)
            {
                this.objectType = objectType;
            }

            public boolean isgCRoot()
            {
                return gCRoot;
            }

            public void setgCRoot(boolean gCRoot)
            {
                this.gCRoot = gCRoot;
            }

            public List<ShortestPath> getChildren()
            {
                return children;
            }

            public void setChildren(List<ShortestPath> children)
            {
                this.children = children;
            }
        }
    }

    class JavaObject
    {
        public static final int CLASS_TYPE = 1;

        public static final int CLASS_LOADER_TYPE = 2;

        public static final int ARRAY_TYPE = 3;

        public static final int NORMAL_TYPE = 4;
        // FIXME: can we generate these code automatically?
        public static Map<String, Comparator<JavaObject>> sortTable = new SortTableGenerator<JavaObject>()
                .add("id", JavaObject::getObjectId)
                .add("shallowHeap", JavaObject::getShallowSize)
                .add("retainedHeap", JavaObject::getRetainedSize)
                .add("label", JavaObject::getLabel)
                .build();
        public int objectId;
        public String prefix;
        public String label;
        public String suffix;
        public long shallowSize;
        public long retainedSize;
        public boolean hasInbound;
        public boolean hasOutbound;
        public int objectType;
        public boolean gCRoot;

        public static Map<String, Comparator<JavaObject>> getSortTable()
        {
            return sortTable;
        }

        public static void setSortTable(
                Map<String, Comparator<JavaObject>> sortTable)
        {
            JavaObject.sortTable = sortTable;
        }

        public int getObjectId()
        {
            return objectId;
        }

        public void setObjectId(int objectId)
        {
            this.objectId = objectId;
        }

        public String getPrefix()
        {
            return prefix;
        }

        public void setPrefix(String prefix)
        {
            this.prefix = prefix;
        }

        public String getLabel()
        {
            return label;
        }

        public void setLabel(String label)
        {
            this.label = label;
        }

        public String getSuffix()
        {
            return suffix;
        }

        public void setSuffix(String suffix)
        {
            this.suffix = suffix;
        }

        public long getShallowSize()
        {
            return shallowSize;
        }

        public void setShallowSize(long shallowSize)
        {
            this.shallowSize = shallowSize;
        }

        public long getRetainedSize()
        {
            return retainedSize;
        }

        public void setRetainedSize(long retainedSize)
        {
            this.retainedSize = retainedSize;
        }

        public boolean isHasInbound()
        {
            return hasInbound;
        }

        public void setHasInbound(boolean hasInbound)
        {
            this.hasInbound = hasInbound;
        }

        public boolean isHasOutbound()
        {
            return hasOutbound;
        }

        public void setHasOutbound(boolean hasOutbound)
        {
            this.hasOutbound = hasOutbound;
        }

        public int getObjectType()
        {
            return objectType;
        }

        public void setObjectType(int objectType)
        {
            this.objectType = objectType;
        }

        public boolean isgCRoot()
        {
            return gCRoot;
        }

        public void setgCRoot(boolean gCRoot)
        {
            this.gCRoot = gCRoot;
        }

        public static Comparator<JavaObject> sortBy(String field, boolean ascendingOrder)
        {
            return ascendingOrder ? sortTable.get(field) : sortTable.get(field).reversed();
        }
    }

    class InspectorView
    {

        public long objectAddress;

        public String name;

        public boolean gCRoot;

        public int objectType;

        public String classLabel;

        public boolean classGCRoot;

        public String superClassName;

        public String classLoaderLabel;

        public boolean classLoaderGCRoot;

        public long shallowSize;

        public long retainedSize;

        public String gcRootInfo;

        public long getObjectAddress()
        {
            return objectAddress;
        }

        public void setObjectAddress(long objectAddress)
        {
            this.objectAddress = objectAddress;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public boolean isgCRoot()
        {
            return gCRoot;
        }

        public void setgCRoot(boolean gCRoot)
        {
            this.gCRoot = gCRoot;
        }

        public int getObjectType()
        {
            return objectType;
        }

        public void setObjectType(int objectType)
        {
            this.objectType = objectType;
        }

        public String getClassLabel()
        {
            return classLabel;
        }

        public void setClassLabel(String classLabel)
        {
            this.classLabel = classLabel;
        }

        public boolean isClassGCRoot()
        {
            return classGCRoot;
        }

        public void setClassGCRoot(boolean classGCRoot)
        {
            this.classGCRoot = classGCRoot;
        }

        public String getSuperClassName()
        {
            return superClassName;
        }

        public void setSuperClassName(String superClassName)
        {
            this.superClassName = superClassName;
        }

        public String getClassLoaderLabel()
        {
            return classLoaderLabel;
        }

        public void setClassLoaderLabel(String classLoaderLabel)
        {
            this.classLoaderLabel = classLoaderLabel;
        }

        public boolean isClassLoaderGCRoot()
        {
            return classLoaderGCRoot;
        }

        public void setClassLoaderGCRoot(boolean classLoaderGCRoot)
        {
            this.classLoaderGCRoot = classLoaderGCRoot;
        }

        public long getShallowSize()
        {
            return shallowSize;
        }

        public void setShallowSize(long shallowSize)
        {
            this.shallowSize = shallowSize;
        }

        public long getRetainedSize()
        {
            return retainedSize;
        }

        public void setRetainedSize(long retainedSize)
        {
            this.retainedSize = retainedSize;
        }

        public String getGcRootInfo()
        {
            return gcRootInfo;
        }

        public void setGcRootInfo(String gcRootInfo)
        {
            this.gcRootInfo = gcRootInfo;
        }
    }

    class FieldView
    {

        public int fieldType;

        public String name;

        public String value;

        public int objectId;

        public FieldView(int fieldType, String name, String value)
        {
            this.fieldType = fieldType;
            this.name = name;
            this.value = value;
        }

        public FieldView(int fieldType, String name, String value, int objectId)
        {
            this(fieldType, name, value);
            this.objectId = objectId;
        }

        public FieldView()
        {

        }

        public int getFieldType()
        {
            return fieldType;
        }

        public void setFieldType(int fieldType)
        {
            this.fieldType = fieldType;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public int getObjectId()
        {
            return objectId;
        }

        public void setObjectId(int objectId)
        {
            this.objectId = objectId;
        }
    }
}
