package com.example.budgetapp.ui.common;

import android.view.View;
import android.widget.EditText;

import com.example.budgetapp.utils.Constants;

import java.util.Locale;

public class AmountInputFocusListener implements View.OnFocusChangeListener {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText)v;
        if (hasFocus){
            String value = editText.getText().toString();
            String[] values = value.split(" ");
            editText.setText(values[0]);
        } else {
            float amount = Float.parseFloat((editText.getText().toString().trim()).replace(",", "."));
            String value = String.format(Locale.getDefault(), "%.2f %s",
                    Math.abs(amount), Constants.CURRENCY);
            editText.setText(value);
        }
    }
}
