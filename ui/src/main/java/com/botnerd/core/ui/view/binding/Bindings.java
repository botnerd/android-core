package com.botnerd.core.ui.view.binding;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.botnerd.core.ui.view.font.FontManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import timber.log.Timber;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Custom bindings for XML attributes using data binding.
 * (http://developer.android.com/tools/data-binding/guide.html)
 */
@SuppressWarnings("unused,WeakerAccess")
public class Bindings {

    @BindingAdapter({"font"})
    public static void setFont(TextView textView, @StringRes int resId) {
        String fontName = textView.getResources().getString(resId);
        if (!TextUtils.isEmpty(fontName)) {
            setFont(textView, fontName);
        }
    }

    @BindingAdapter({"font"})
    public static void setFont(RadioButton radioButton, @StringRes int resId) {
        String fontName = radioButton.getResources().getString(resId);
        if (!TextUtils.isEmpty(fontName)) {
            setFont(radioButton, fontName);
        }
    }

    @BindingAdapter({"font"})
    public static void setFont(TextView textView, String fontName) {
        FontManager.getInstance().setFont(textView, fontName);
    }

    @BindingAdapter({"font"})
    public static void setFont(TabLayout tabLayout, @StringRes int resId) {
        String fontName = tabLayout.getResources().getString(resId);
        if (!TextUtils.isEmpty(fontName)) {
            setFont(tabLayout, fontName);
        }
    }

    @BindingAdapter({"font", "selectedFont"})
    public static void setFont(TabLayout tabLayout, @StringRes int defaultFontResId, @StringRes int selectedFontResId) {
        String fontName = tabLayout.getResources().getString(defaultFontResId);
        String selectedFontName = tabLayout.getResources().getString(selectedFontResId);
        if (!TextUtils.isEmpty(fontName)) {
            if (!TextUtils.isEmpty(selectedFontName)) {
                setFont(tabLayout, fontName, selectedFontName);
            } else {
                setFont(tabLayout, fontName);
            }
        }
    }

    @BindingAdapter({"font"})
    public static void setFont(TabLayout tabLayout, String fontName) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    FontManager.getInstance().setFont((TextView) tabViewChild, fontName);
                }
            }
        }
    }

    @BindingAdapter({"font", "selectedFont"})
    public static void setFont(TabLayout tabLayout, final String fontName, final String selectedFontName) {
        setFont(tabLayout, fontName);
        final ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabsCount = vg.getChildCount();
                for (int j = 0; j < tabsCount; j++) {
                    ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
                    int tabChildsCount = vgTab.getChildCount();
                    for (int i = 0; i < tabChildsCount; i++) {
                        View tabViewChild = vgTab.getChildAt(i);
                        if (tabViewChild instanceof TextView) {
                            ((TextView) tabViewChild).setAllCaps(false);
                            if (j == tab.getPosition()) {
                                FontManager.getInstance().setFont((TextView) tabViewChild, selectedFontName);
                            } else {
                                FontManager.getInstance().setFont((TextView) tabViewChild, fontName);
                            }
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @BindingAdapter({"font"})
    public static void setFont(CollapsingToolbarLayout toolbar, @StringRes int resId) {
        String fontName = toolbar.getResources().getString(resId);
        if (!TextUtils.isEmpty(fontName)) {
            setFont(toolbar, fontName);
        }
    }

    @BindingAdapter({"font"})
    public static void setFont(CollapsingToolbarLayout toolbar, String fontName) {
        toolbar.setExpandedTitleTypeface(FontManager.getInstance().getTypeface(toolbar.getContext(), fontName));
        toolbar.setCollapsedTitleTypeface(FontManager.getInstance().getTypeface(toolbar.getContext(), fontName));
        int childCount = toolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View toolbarChild = toolbar.getChildAt(i);
            if (toolbarChild instanceof TextView) {
                FontManager.getInstance().setFont((TextView) toolbarChild, fontName);
            }
        }
    }

    @BindingAdapter("htmlText")
    public static void setText(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(Html.fromHtml(text));
        } else {
            textView.setText(text);
        }
    }

    @BindingAdapter("error")
    public static void setError(TextInputLayout textInputLayout, String error) {
        // This fixes a defect in TextInputLayout
        if (TextUtils.isEmpty(error)) {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setError(error);
            textInputLayout.setErrorEnabled(true);
        }
    }

    @BindingAdapter("error")
    public static void setError(Spinner spinner, String error) {
        TextView errorText = (TextView) spinner.getSelectedView();
        if (null == errorText) {
            errorText = spinner.findViewById(android.R.id.text1);
        }
        if (null != errorText) {
            if (!TextUtils.isEmpty(error)) {
                errorText.setTextColor(Color.RED);
                errorText.setText(error);
            }
        }
    }

    @BindingAdapter("passwordToggleDrawable")
    public static void setPasswordToggleDrawable(TextInputLayout textInputLayout, Drawable drawable) {
        textInputLayout.setPasswordVisibilityToggleDrawable(drawable);
    }

    @BindingAdapter({"progress","max"})
    public static void setProgress(ProgressBar progressBar, BigDecimal progress, BigDecimal max) {
        int progressInt = null != progress ? progress.intValue() : 0;
        int maxInt = null != max ? max.intValue() : 1;
        if (maxInt <= 0) maxInt = 1;
        progressBar.setMax(maxInt);
        progressBar.setProgress(progressInt);
    }

    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, @DrawableRes int resId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setImageDrawable(view.getContext().getDrawable(resId));
            } else {
                view.setImageDrawable(view.getContext().getResources().getDrawable(resId));
            }
        } catch (Resources.NotFoundException e) {
            Timber.e(e);
        }
    }

    @BindingAdapter({"android:layout_gravity"})
    public static void setLayoutGravity(View view, int gravity) {
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.gravity = gravity;
            view.setLayoutParams(params);
        } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = gravity;
            view.setLayoutParams(params);
        }
    }

    @BindingAdapter({"number"})
    public static void setTextAsNumber(TextView textView, int value) {
        textView.setText(String.format(Locale.US, "%d", value));
    }

    @BindingAdapter({"dividerItemDecoration"})
    public static void setDividerItemDecoration(RecyclerView recyclerView, Drawable dividerDrawable) {
        if (null != dividerDrawable) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
                itemDecoration.setDrawable(dividerDrawable);
                recyclerView.addItemDecoration(itemDecoration);
            }
        }
    }

    @BindingConversion
    public static int convertToViewVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

}