package com.botnerd.core.view.binding;

/**
 * @author tjudkins
 * @since 2/9/17
 */

public interface GridSpannable {
    /**
     * Get the number of columns the item layout should span in a {@link androidx.recyclerview.widget.GridLayoutManager}
     * @return the number of columns to span
     */
    int getSpanSize();
}
