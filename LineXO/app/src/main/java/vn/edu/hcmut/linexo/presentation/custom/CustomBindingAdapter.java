package vn.edu.hcmut.linexo.presentation.custom;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Class included methods that used in xml file to bind attribute or
 * something else of view.
 */

public class CustomBindingAdapter {

    /**
     * Add setter to bind {@code View.OnTouchListener} to a {@code View}.
     * @param view view need to set listener.
     * @param listener used to set into view.
     */
    @BindingAdapter("onTouch")
    public static void setOnTouchListener(View view, View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }
}