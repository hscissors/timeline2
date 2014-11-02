package com.soundsofpolaris.timeline.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.debug.Logger;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SelectImageSourceDialog extends DialogFragment {

    private static final String TAG = SelectImageSourceDialog.class.toString();
    private static final int SELECT_PICTURE = 1;
    private static final String TIMELINE_TITLE = "timeline_title";

    private String mTimelineTitle;

    private Button mSelectFromPhone;
    private Button mSelectFromWeb;

    private Listener mListener;

    public static SelectImageSourceDialog newInstance(String timelineTitle) {
        SelectImageSourceDialog fragment = new SelectImageSourceDialog();

        Bundle args = new Bundle();
        args.putString(TIMELINE_TITLE, timelineTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            mTimelineTitle = getArguments().getString(TIMELINE_TITLE);
        }

        View v = inflater.inflate(R.layout.dialog_select_image_source, container, false);

        mSelectFromPhone = (Button) v.findViewById(R.id.dialog_select_image_source_phone_button);
        mSelectFromWeb = (Button) v.findViewById(R.id.dialog_select_image_source_web_button);

        mSelectFromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromPhone();
            }
        });

        mSelectFromWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromWeb();
            }
        });
        return v;
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturned) {
        super.onActivityResult(requestCode, resultCode, imageReturned);

        switch(requestCode) {
            case SELECT_PICTURE:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Uri uri = imageReturned.getData();
                        Bitmap image = FileHelper.resampleBitmap(uri, getActivity());
                        if(mListener != null){
                            mListener.onSelectedFromPhone(image);
                        }
                        dismiss();

                    } catch (Exception e) {
                        MessageDialog messageDialog = MessageDialog.newInstance("Error", getResources().getString(R.string.error_select_new_image), true);
                        messageDialog.show(getActivity().getSupportFragmentManager(), TAG);
                    }
                }
        }
    }

    private void selectFromPhone(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    private void selectFromWeb(){
        if(Utils.isEmpty(mTimelineTitle)){
            mListener.onSelectedFromWeb(null);
            dismiss();
            return;
        } else {
            //#rg_s .rg_i
            ArrayList<String> imageUrls = new ArrayList();
            FetchImageUrlsTask task = new FetchImageUrlsTask(this.getActivity());
            task.execute(mTimelineTitle);

            try {
               //TODO Replace with call back
                imageUrls = task.get();
                ((BaseActivity) getActivity()).hideLoader();

                if(mListener != null){
                    mListener.onSelectedFromWeb(imageUrls);
                }
                dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static interface Listener{
        public void onSelectedFromPhone(Bitmap image);
        public void onSelectedFromWeb(ArrayList<String> imageUrls);
    }


    private static class FetchImageUrlsTask extends AsyncTask<String, Void, ArrayList<String>> {
        private static final String SIZE = "m"; // M or L
        private static final String IMAGE_A_SELECTOR = "a.rg_l";
        private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36";

        private final Context mContext;

        public FetchImageUrlsTask(Context context){
            mContext = context;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> imageUrls = new ArrayList();
            if(params.length == 0) return imageUrls;
            try {
                String requestUrl = constructUrl(params[0]);
                Document doc = Jsoup.connect(requestUrl)
                        .userAgent(USER_AGENT)
                        .get();
                Elements imageLinks = doc.select(IMAGE_A_SELECTOR);
                for (Element link : imageLinks){
                    Uri externalLink = Uri.parse(link.attr("href"));
                    imageUrls.add(externalLink.getQueryParameter("imgurl"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageUrls;
        }

        @Override
        protected void onPreExecute() {
            ((BaseActivity) mContext).showLoader();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> imageUrls) {
            ((BaseActivity) mContext).hideLoader();
            super.onPostExecute(imageUrls);
        }

        private String constructUrl(String term){
            StringBuilder url = new StringBuilder();
            url.append("https://www.google.com/search?tbm=isch&q=");
            url.append(Uri.encode(term));
            url.append("#q=");
            url.append(Uri.encode(term));
            url.append("&tbm=isch&tbs=isz:");
            url.append(SIZE);
            return url.toString();
        }
    }
}

