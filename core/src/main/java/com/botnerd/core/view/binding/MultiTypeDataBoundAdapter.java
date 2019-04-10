/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.botnerd.core.view.binding;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import com.botnerd.core.BR;
import com.botnerd.core.BuildConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An abstraction over {@link BaseDataBoundAdapter} that keeps the list of children and can
 * support multiple item types.
 * <p>
 * This class relies on all layouts using the "data" variable to represent the item.
 * <p>
 * Although this class by itself just exists for demonstration purposes, it might be a useful idea
 * for an application to have a generic naming convention for their items to scale lists with
 * many view types.
 * <p>
 * Note that, by using this, you lose the compile time type checking for mapping your items to
 * layout files but at run time, it will still be checked. See
 * {@link androidx.databinding.ViewDataBinding#setVariable(int, Object)} for details.
 *
 * Adapted from: https://github.com/google/android-ui-toolkit-demos
 */
public class MultiTypeDataBoundAdapter extends BaseDataBoundAdapter {
    private List<Object> mItems = new ArrayList<>();
    private ActionCallback mActionCallback;

    public MultiTypeDataBoundAdapter(ActionCallback actionCallback, Object... items) {
        mActionCallback = actionCallback;
        if (null != items) {
            Collections.addAll(mItems, items);
        }
    }

    @Override
    protected void bindItem(DataBoundViewHolder holder, int position, List payloads) {
        Object item = mItems.get(position);
        holder.binding.setVariable(BR.data, mItems.get(position));
        // this will work even if the layout does not have a callback parameter
        holder.binding.setVariable(BR.callback, mActionCallback);
        if (item instanceof DynamicBinding) {
            ((DynamicBinding) item).bind(holder);
        }
    }

    @Override
    public @LayoutRes
    int getItemLayoutId(int position) {
        // use layout ids as types
        Object item = getItem(position);

        if (item instanceof LayoutBinding) {
            return ((LayoutBinding) item).getLayoutId();
        }
        if (BuildConfig.DEBUG) {
            throw new IllegalArgumentException("unknown item type " + item);
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected final List<Object> getItems() {
        return mItems;
    }

    @Nullable
    final Object getItem(int position) {
        return position < mItems.size() ? mItems.get(position) : null;
    }

    public final int indexOf(Object item) {
        return mItems.indexOf(item);
    }

    public final void addItem(Object item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public final void addItem(int position, Object item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public final void addItems(Object... items) {
        if (null != items) {
            int start = mItems.size();
            Collections.addAll(mItems, items);
            notifyItemRangeChanged(start, items.length);
        }
    }

    public final void setItems(Object... items) {
        int size = mItems.size();
        mItems.clear();
        if (null != items) {
            Collections.addAll(mItems, items);
        }
        if (mItems.size() > size) {
            size = mItems.size();
        }
        notifyItemRangeChanged(0, size);
    }

    public final void removeItem(Object item) {
        int position = mItems.indexOf(item);
        if (position >= 0) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final void removeItems(Object... items) {
        if (null != items) {
            int size = mItems.size();
            mItems.removeAll(Arrays.asList(items));
            notifyItemRangeChanged(0, size);
        }
    }

    public void clear() {
        int size = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * Class that all action callbacks must extend for the adapter callback.
     */
    public interface ActionCallback {}

}