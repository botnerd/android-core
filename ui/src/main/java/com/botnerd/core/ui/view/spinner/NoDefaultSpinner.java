package com.botnerd.core.ui.view.spinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSpinner;
import com.botnerd.core.ui.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A modified Spinner that doesn't automatically select the first entry in the list.
 *
 * Shows the prompt if nothing is selected.
 *
 * Limitations: does not display prompt if the entry list is empty.
 */
@SuppressWarnings("unused")
public class NoDefaultSpinner extends AppCompatSpinner {

    public NoDefaultSpinner(Context context) {
        this(context, null);
    }

    public NoDefaultSpinner(Context context, AttributeSet attrs) {
        // This ensures that it inherits the styles of a Spinner
        this(context, attrs, R.attr.spinnerStyle);
    }

    public NoDefaultSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public NoDefaultSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    @SuppressWarnings("unused")
    public NoDefaultSpinner(Context context, int mode) {
        this(context, null, 0, mode);
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    @SuppressLint("PrivateApi")
    @Override
    public void setAdapter(SpinnerAdapter orig ) {
        final SpinnerAdapter adapter = newProxy(orig);

        super.setAdapter(adapter);

        try {
            final Method m = AdapterView.class.getDeclaredMethod(
                               "setNextSelectedPositionInt",int.class);
            m.setAccessible(true);
            m.invoke(this,-1);

            final Method n = AdapterView.class.getDeclaredMethod(
                               "setSelectedPositionInt",int.class);
            n.setAccessible(true);
            n.invoke(this,-1);
        } 
        catch( Exception ignored) {
        }
    }

    protected SpinnerAdapter newProxy(SpinnerAdapter obj) {
        if (null == obj) return null;
        return (SpinnerAdapter) java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                new Class[]{SpinnerAdapter.class},
                new SpinnerAdapterProxy(obj));
    }



    /**
     * Intercepts getView() to display the prompt if position < 0
     */
    protected class SpinnerAdapterProxy implements InvocationHandler {

        SpinnerAdapter obj;
        Method getView;


        SpinnerAdapterProxy(SpinnerAdapter obj) {
            this.obj = obj;
            try {
                this.getView = SpinnerAdapter.class.getMethod(
                                 "getView",int.class,View.class,ViewGroup.class);
            }
            catch( Exception e ) {
                throw new RuntimeException(e);
            }
        }

        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            try {
                return m.equals(getView) &&
                       (Integer)(args[0])<0 ?
                         getView((Integer)args[0],(View)args[1],(ViewGroup)args[2]) :
                         m.invoke(obj, args);
            }
            catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        View getView(int position, View convertView, ViewGroup parent) {

            if( position<0 ) {
                final TextView v =
                  (TextView) ((LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(
                      android.R.layout.simple_spinner_item,parent,false);
                v.setText(getPrompt());
                return v;
            }
            return obj.getView(position,convertView,parent);
        }
    }
}