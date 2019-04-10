package com.botnerd.core.view.binding;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * @author tjudkins
 * @since 3/28/17
 */
public class GridLayoutSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    MultiTypeDataBoundAdapter mAdapter;

    public GridLayoutSpanSizeLookup(MultiTypeDataBoundAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getSpanSize(int position) {
        Object item = mAdapter.getItem(position);

        if (item instanceof GridSpannable) {
            return ((GridSpannable) item).getSpanSize();
        }
        return 1;
    }
}
