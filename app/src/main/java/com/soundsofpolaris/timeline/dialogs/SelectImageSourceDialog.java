package com.soundsofpolaris.timeline.dialogs;

import android.app.Activity;
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
import com.soundsofpolaris.timeline.tools.Utils;

import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
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

    public static SelectImageSourceDialog newInstance(String title) {
        SelectImageSourceDialog fragment = new SelectImageSourceDialog();

        Bundle args = new Bundle();
        args.putString(TIMELINE_TITLE, title);
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

        Button selectFromPhone = (Button) v.findViewById(R.id.dialog_select_image_source_phone_button);
        Button selectFromWeb = (Button) v.findViewById(R.id.dialog_select_image_source_web_button);

        selectFromPhone.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

        selectFromWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isEmpty(mTimelineTitle)){
                   //TODO add warning to set title first
                    return;
                } else {
                    ((BaseActivity) getActivity()).showLoader();
                    //#rg_s .rg_i
                    List<String> imageUrls = new ArrayList();
                    FetchImageUrlsTask task = new FetchImageUrlsTask();
                    task.execute(mTimelineTitle);

                    try {
                        imageUrls = task.get();
                        ((BaseActivity) getActivity()).hideLoader();

                        for (String url : imageUrls){
                            Logger.v(TAG, "found url! " + url);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }

    private static class FetchImageUrlsTask extends AsyncTask<String, Void, List<String>>{
        private static final String SIZE = "m"; // M or L
        private static final String IMAGE_A_SELECTOR = "a.rg_l";

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> imageUrls = new ArrayList();
            if(params.length == 0) return imageUrls;
            try {
                String requestUrl = constructUrl(params[0]);
                Document doc = Jsoup.connect(requestUrl)
                        .userAgent("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36")
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

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PICTURE:
                if(resultCode == Activity.RESULT_OK){
//                    try {
//                        Uri selectedImage = imageReturnedIntent.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                        Cursor cursor = getActivity().getContentResolver().query(
//                                selectedImage, filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String filePath = cursor.getString(columnIndex);
//                        cursor.close();
//
//                        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//                    } catch (Exception e) {
                        MessageDialog messageDialog = MessageDialog.newInstance("Error", getResources().getString(R.string.error_select_new_image), true);

                        messageDialog.show(getActivity().getSupportFragmentManager(), TAG);
 //                   }
                }
        }
    }
}

