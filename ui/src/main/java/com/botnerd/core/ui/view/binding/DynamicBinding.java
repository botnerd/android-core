package com.botnerd.core.ui.view.binding;

/**
 * @author tjudkins
 * @since 2/24/17
 */

public interface DynamicBinding {

    /**
     * Gives the item an opportunity to do work during binding.
     */
    void bind(DataBoundViewHolder holder);

}
