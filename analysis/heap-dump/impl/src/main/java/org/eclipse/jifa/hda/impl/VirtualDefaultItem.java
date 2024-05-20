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

package org.eclipse.jifa.hda.impl;

import org.eclipse.jifa.common.annotation.UseAccessor;
import org.eclipse.jifa.hda.api.AnalysisException;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.query.Bytes;
import org.eclipse.mat.query.IStructuredResult;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IObject;

import static org.eclipse.jifa.hda.api.Model.DominatorTree;

@UseAccessor
public class VirtualDefaultItem extends DominatorTree.DefaultItem
{
    static final int COLUMN_LABEL = 0;
    static final int COLUMN_SHALLOW = 1;
    static final int COLUMN_RETAINED = 2;
    static final int COLUMN_PERCENT = 3;

    transient final ISnapshot snapshot;
    transient final IStructuredResult results;
    transient final Object e;

    transient final int parentObjectId;

    public VirtualDefaultItem(final ISnapshot snapshot, final IStructuredResult results, final Object e,
            final int parentObjectId)
    {
        this.snapshot = snapshot;
        this.results = results;
        this.e = e;
        this.objectId = results.getContext(e).getObjectId();
        this.parentObjectId = parentObjectId;
    }

    @Override
    public String getPrefix()
    {
        if (parentObjectId >= 0)
        {
            try
            {
                return Helper.prefix(snapshot, parentObjectId, objectId);
            }
            catch (SnapshotException se)
            {
                throw new AnalysisException(se);
            }
        }
        return null;
    }

    @Override
    public String getSuffix()
    {
        try
        {
            IObject object = snapshot.getObject(objectId);
            return Helper.suffix(object.getGCRootInfo());
        }
        catch (SnapshotException se)
        {
            throw new AnalysisException(se);
        }
    }

    @Override
    public int getObjectId()
    {
        return objectId;
    }

    @Override
    public int getObjectType()
    {
        try
        {
            return HeapDumpAnalyzerImpl.typeOf(snapshot.getObject(objectId));
        }
        catch (SnapshotException se)
        {
            throw new AnalysisException(se);
        }
    }

    public boolean isGCRoot()
    {
        return snapshot.isGCRoot(objectId);
    }

    @Override
    public String getLabel()
    {
        return EscapeUtil.unescapeLabel((String)results.getColumnValue(e, COLUMN_LABEL));
    }

    @Override
    public long getShallowSize()
    {
        return ((Bytes)results.getColumnValue(e, COLUMN_SHALLOW)).getValue();
    }

    @Override
    public long getRetainedSize()
    {
        return ((Bytes)results.getColumnValue(e, COLUMN_RETAINED)).getValue();
    }

    @Override
    public double getPercent()
    {
        return (Double)results.getColumnValue(e, COLUMN_PERCENT);
    }
}