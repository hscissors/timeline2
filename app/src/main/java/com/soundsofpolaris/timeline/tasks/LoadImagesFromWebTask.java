package com.soundsofpolaris.timeline.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.soundsofpolaris.timeline.base.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hscissors on 11/4/14.
 */
public class LoadImagesFromWebTask extends AsyncTask<String, Void, ArrayList<String>> {
        private static final String SIZE = "m"; // M or L
        private static final String IMAGE_A_SELECTOR = "a.rg_l";
        private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36";

        private Listener mListener;

        public static interface Listener{
            public void onTaskComplete(ArrayList<String> imageUrls);
        }

        public LoadImagesFromWebTask(Listener listener){
            mListener = listener;
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> imageUrls) {
            if(mListener != null){
                mListener.onTaskComplete(imageUrls);
            }
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
