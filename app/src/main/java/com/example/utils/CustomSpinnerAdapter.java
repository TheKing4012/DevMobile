package com.example.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {

    private boolean isFirstSelection = true;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull CharSequence[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view;

        if (isFirstSelection) {
            textView.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        isFirstSelection = false;
    }
}
