package com.soundsofpolaris.timeline.event;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.tools.Utils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by hscissors on 11/2/14.
 */
public class EventEditView extends RelativeLayout{

    private Spinner mMonth;
    private EditText mDay;
    private EditText mYear;
    private Spinner mEra;

    public EventEditView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.event_edit_view, this);

        mMonth = (Spinner) findViewById(R.id.event_edit_month);
        mDay = (EditText) findViewById(R.id.event_edit_day);
        mYear = (EditText) findViewById(R.id.event_edit_year);
        mEra = (Spinner) findViewById(R.id.event_edit_era);

        mDay.setEnabled(false);

        Locale currentLocale = getResources().getConfiguration().locale;
        DateFormatSymbols monthSymbols = new DateFormatSymbols(currentLocale);
        ArrayList<String> monthArray = new ArrayList();
        monthArray.add("Month");
        monthArray.addAll(Arrays.asList(monthSymbols.getMonths()));

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item, monthArray);
        monthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mMonth.setAdapter(monthAdapter);
        mMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDaysInput(position);
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    mMonth.setDropDownVerticalOffset(-1 * (view.getMeasuredHeight() + 16));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        ArrayAdapter<CharSequence> eraAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.era_array, R.layout.spinner_item);
        eraAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mEra.setAdapter(eraAdapter);
        mEra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    mEra.setDropDownVerticalOffset(-1 * (position + 1) * (view.getMeasuredHeight() + 16));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    private void updateDaysInput(int monthPosition){
        if(monthPosition == 0) {
            mDay.setEnabled(false);
            return;
        }

        mDay.setEnabled(true);
        int maxDaysInMonth = -1;
        switch(monthPosition){
            case 2:
                String currentSelectedYearString = mYear.getText().toString();
                if(!Utils.isEmpty(currentSelectedYearString) && isLeapYear(Integer.parseInt(currentSelectedYearString))){
                    maxDaysInMonth = 29;
                } else {
                    maxDaysInMonth = 28;
                }

                break;
            case 4:case 6:case 9:case 11:
                maxDaysInMonth = 30;
                break;
            case 1:case 3:case 5: case 7:case 8:case 10: case 12:
                maxDaysInMonth = 31;
                break;
        }
        mDay.setFilters(new InputFilter[]{ new InputFilterMinMax(1, maxDaysInMonth)});
    }

    private boolean isLeapYear(int year){
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
