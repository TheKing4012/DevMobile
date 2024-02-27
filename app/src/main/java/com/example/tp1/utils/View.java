package com.example.tp1.utils;

import android.app.Activity;
import android.widget.Spinner;

import com.example.tp1.R;

public class View {
    public static boolean checkNotEmptyText(Activity activity, String text) {
        if (text.isEmpty()) {
            activity.showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }

    public static boolean checkNotEmptySpinner(Activity activity, Spinner spinner, String text) {
        if (text.equals(spinner.getAdapter().getItem(0).toString())) {
            activity.showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }
}
