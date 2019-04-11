package com.botnerd.core.ui.view.binding;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;
import com.botnerd.core.ui.BR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tjudkins
 * @since 3/9/17
 */
public class MultiTypeDataBoundPagerAdapter<T extends ViewDataBinding> extends PagerAdapter {

    private List<Object> mItems = new ArrayList<>();
    private ActionCallback mActionCallback;

    public MultiTypeDataBoundPagerAdapter(ActionCallback actionCallback, Object... items) {
        mActionCallback = actionCallback;
        if (null != items) {
            Collections.addAll(mItems, items);
        }
    }
 
    @Override
    public int getCount() {
        return mItems.size();
    }
 
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        if (object instanceof DataBoundViewHolder) {
            return view == ((DataBoundViewHolder) object).binding.getRoot();
        } else {
            return false;
        }
    }
 
    @Override
    @NonNull
    public final Object instantiateItem(@NonNull ViewGroup parent, int position) {
        DataBoundViewHolder<T> vh = DataBoundViewHolder.create(parent, getItemLayoutId(position));
        bindItem(vh, position);
        parent.addView(vh.binding.getRoot());
        return vh;

    }
 
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (object instanceof DataBoundViewHolder) {
            container.removeView(((DataBoundViewHolder) object).binding.getRoot());
        }
    }

    protected void bindItem(DataBoundViewHolder<T> holder, int position) {
        Object item = mItems.get(position);
        holder.binding.setVariable(BR.data, mItems.get(position));
        // this will work even if the layout does not have a callback parameter
        holder.binding.setVariable(BR.callback, mActionCallback);
        if (item instanceof DynamicBinding) {
            ((DynamicBinding) item).bind(holder);
        }
    }

    public @LayoutRes
    int getItemLayoutId(int position) {
        // use layout ids as types
        Object item = getItem(position);

        if (item instanceof LayoutBinding) {
            return ((LayoutBinding) item).getLayoutId();
        }
        throw new IllegalArgumentException("unknown item type " + item);

    }

    public final Object getItem(int position) {
        return mItems.get(position);
    }

    public final void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public final void addAll(Object... items) {
        Collections.addAll(mItems, items);
        notifyDataSetChanged();
    }

    public final void add(Object item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    /**
     * Class that all action callbacks must extend for the adapter callback.
     */
    public interface ActionCallback {}

}