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

    protected String doInBackground(URL... urls) {
        //initializate response to null;
        String response = null;
        //create instance of API class
        GetAPIStories getAPIStories = new GetAPIStories();
        try {
            //get the API response from passed url
            response = getAPIStories.run(urls[0].toString());
            try {
                //create json object and array to parse it
                JSONObject jsonObj = new JSONObject(response);
                JSONArray articles = jsonObj.getJSONArray("articles");
                for (int i = 0; i < articles.length(); i++) {
                    //get json article object
                    JSONObject article = articles.getJSONObject(i);
                    JSONObject source = articles.getJSONObject(i);
                    //parse json obj and get property
                    String author = article.getString("author");
                    String title = article.getString("title");
                    String description = article.getString("description");
                    String url = article.getString("url");
                    String urlToImage = article.getString("urlToImage");
                    String publishedAt = article.getString("publishedAt");
                    //create news articled instance and add on arraylist
                    NewsArticleData newsArticleData = new NewsArticleData(author, title, description, url, urlToImage, publishedAt);
                    //insert news into arrayList
                    fragment.newsArticleDataArrayList.add(newsArticleData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String response) {
        if (response != null) {
            fragment.mAdapter.notifyDataSetChanged();
        }
    }


}
