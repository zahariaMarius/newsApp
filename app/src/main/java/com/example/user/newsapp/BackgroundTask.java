package com.example.user.newsapp;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 22/11/2017.
 */

public class BackgroundTask extends AsyncTask<URL, Void, String> {

    StoriesTabActivity.PlaceholderFragment fragment;

    public BackgroundTask(StoriesTabActivity.PlaceholderFragment placeholderFragment) {
        fragment = placeholderFragment;
    }

    public BackgroundTask() {

    }

    protected String doInBackground(URL... urls) {
        String response = null;
        GetAPIStories getAPIStories = new GetAPIStories();
        try {
            response = getAPIStories.run(urls[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String response) {
        if (response != null) {
            fragment.newsArticles = jsonParse(response);
            fragment.mAdapter.notifyDataSetChanged();
        }
    }

    protected ArrayList<JSONObject> jsonParse(String jsonResponse) {

        ArrayList<JSONObject> newsArticles = new ArrayList<JSONObject>();

        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray articles = jsonObj.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsArticles.add(article);
                Log.i("bbbou", "jsonParse: " + newsArticles);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArticles;
    }
}
