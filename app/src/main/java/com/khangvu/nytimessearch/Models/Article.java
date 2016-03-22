package com.khangvu.nytimessearch.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by duyvu on 3/19/16.
 */
public class Article {

    private String mWebUrl;
    private String mHeadline;
    private String mThumbNail;

    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmHeadline() {
        return mHeadline;
    }

    public String getmThumbNail() {
        return mThumbNail;
    }

    public void setmThumbNail(String mThumbNail) {
        this.mThumbNail = mThumbNail;
    }

    public void setmHeadline(String mHeadline) {
        this.mHeadline = mHeadline;
    }

    public void setmWebUrl(String mWebUrl) {
        this.mWebUrl = mWebUrl;
    }

    public Article(JSONObject jsonObject) {
        try {
            this.mWebUrl = jsonObject.getString("web_url");
            this.mHeadline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.mThumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.mThumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
