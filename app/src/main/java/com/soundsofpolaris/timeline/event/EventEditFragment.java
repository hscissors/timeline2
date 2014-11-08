package com.soundsofpolaris.timeline.event;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.tasks.SaveEventTask;
import com.soundsofpolaris.timeline.timeline.Timeline;
import com.soundsofpolaris.timeline.tools.Utils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EventEditFragment extends Fragment {

    private static final String PARENT_TIMELINE = "parentTimeline";

    private Timeline mParentTimeline;

    private Spinner mMonth;
    private EditText mDay;
    private EditText mYear;
    private Spinner mEra;

    private EditText mTitle;
    private EditText mDesc;

    private Button mNegativeButton;
    private Button mPositiveButton;

    public static EventEditFragment newInstance(Timeline parentTimeline) {
        EventEditFragment fragment = new EventEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARENT_TIMELINE, parentTimeline);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParentTimeline = (Timeline) getArguments().getParcelable(PARENT_TIMELINE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.event_edit_fragment, container, false);

        mMonth = (Spinner) rootView.findViewById(R.id.event_edit_month);
        mDay = (EditText) rootView.findViewById(R.id.event_edit_day);
        mYear = (EditText) rootView.findViewById(R.id.event_edit_year);
        mEra = (Spinner) rootView.findViewById(R.id.event_edit_era);

        mTitle = (EditText) rootView.findViewById(R.id.event_edit_title);
        mDesc = (EditText) rootView.findViewById(R.id.event_edit_description);

        mPositiveButton = (Button) rootView.findViewById(R.id.event_edit_positive_button);
        mNegativeButton = (Button) rootView.findViewById(R.id.event_edit_negative_button);

        mDay.setEnabled(false);

        Locale currentLocale = getResources().getConfiguration().locale;
        DateFormatSymbols monthSymbols = new DateFormatSymbols(currentLocale);
        ArrayList<String> monthArray = new ArrayList();
        monthArray.add("Month");
        monthArray.addAll(Arrays.asList(monthSymbols.getMonths()));

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, monthArray);
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

        ArrayAdapter<CharSequence> eraAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.era_array, R.layout.spinner_item);
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

        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllMonth = false;
                int day = 1;
                if(!Utils.isEmpty(mDay.getText().toString())){
                   day = Integer.parseInt(mDay.getText().toString());
                } else {
                   isAllMonth = true;
                }

                int month = mMonth.getSelectedItemPosition();
                boolean isAllYear = false;
                if(isAllMonth && month == 0){
                    isAllYear = true;
                } else {
                    month--; //0-based month
                }

                int year;
                if(!Utils.isEmpty(mYear.getText().toString())){
                    year = Integer.parseInt(mYear.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Please select a year", Toast.LENGTH_SHORT).show();
                    return;
                }

                int era;
                if(mEra.getSelectedItemPosition() == 0){ //CE
                    era = 1;
                } else { //BCE
                    era = 0;
                    year = year * -1; //for sorting purposes
                }

                GregorianCalendar date = new GregorianCalendar(year, month, day);
                date.set(Calendar.ERA, era);

                String title = mTitle.getText().toString();
                if(Utils.isEmpty(title)){
                    Toast.makeText(getActivity(), "Please select a title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String desc = mDesc.getText().toString();

                SaveEventTask task = new SaveEventTask(year, month, date.getTimeInMillis(), title, desc, isAllYear, isAllMonth, mParentTimeline);
                task.setListener(new SaveEventTask.Listener(){
                    @Override
                    public void onTaskComplete(Event event) {
                        //return event
                    }
                });

                task.execute();
            }
        });

        return rootView;
    }

    private void updateDaysInput(int monthPosition){
        if(monthPosition == 0) {
            mDay.setText("");
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
