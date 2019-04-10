package com.botnerd.core.userinterface.common;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.botnerd.core.R;

/**
 * @author tjudkins
 * @since 2/27/17
 */

public abstract class BaseParentFragment extends Fragment {

    public static boolean handleBackPressed(FragmentManager fm)
    {
        if(fm.getFragments() != null){
            for(Fragment frag : fm.getFragments()){
                if(frag instanceof BaseParentFragment && frag.isVisible()){
                    if(((BaseParentFragment)frag).onBackPressed()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean onBackPressed()
    {
        FragmentManager fm = getChildFragmentManager();
        if(handleBackPressed(fm)){
            return true;
        }
        else if(getUserVisibleHint() && fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            return true;
        }
        return false;
    }

    protected void showFragment(@IdRes int containerViewId, Fragment fragment, boolean addToBackStack, boolean animate) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                    R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ft.replace(containerViewId, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    protected void clearStack() {
        FragmentManager fm = getChildFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.executePendingTransactions();
    }

}
