package com.botnerd.core.ui.common.error;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.botnerd.core.ui.BR;

/**
 * @author tjudkins
 * @since 3/8/17
 */

public class LayoutLoadingWithErrorViewModel extends BaseObservable {

    @Bindable
    private boolean loading;
    @Bindable
    private boolean showError;
    @Bindable
    private String error;

    public LayoutLoadingWithErrorViewModel() {}

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public boolean isShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError = showError;
        notifyPropertyChanged(BR.showError);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
    }

}
