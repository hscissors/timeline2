package com.soundsofpolaris.timeline.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;

public class MessageDialog extends DialogFragment {

    private static final String TAG = MessageDialog.class.toString();

    private static final String TITLE = "dialog_message_title";
    private static final String MESSAGE = "dialog_message_body";

    private Button mPositiveButton;

    public static MessageDialog newInstance(String title, String message, boolean cancelable) {
        MessageDialog fragment = new MessageDialog();

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        fragment.setCancelable(cancelable);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_message, container, false);

        if(getArguments() != null){
            getDialog().setTitle(getArguments().getString(TITLE));
            TextView message = (TextView) v.findViewById(R.id.dialog_message_body);
            message.setText(getArguments().getString(MESSAGE));
        }
        Button mPositiveButton = (Button) v.findViewById(R.id.dialog_message_positive_button);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    public void setListener(View.OnClickListener listener){
        mPositiveButton.setOnClickListener(listener);
    }
}

