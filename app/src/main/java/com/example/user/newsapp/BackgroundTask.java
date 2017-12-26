package com.example.user.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by user on 22/11/2017.
 */

public class BackgroundTask extends AsyncTask<URL, Void, String> {

    ArticlesTabActivity.PlaceholderFragment fragment;

    public BackgroundTask(ArticlesTabActivity.PlaceholderFragment placeholderFragment) {
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
            //call function that parse all response and insert it into arraylist
            JsonNewsParse(response);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("responseException", "doInBackground: " + e);
        }
        return response;
    }

    protected void onPostExecute(String response) {
        if (response != null) {
            fragment.mAdapter.notifyDataSetChanged();
        }
    }


    public void JsonNewsParse(String response) {
        try {
            //create json object and array to parse it
            JSONObject jsonObj = new JSONObject(response);
            JSONArray articles = jsonObj.getJSONArray("articles");
            for (int i = 0; i < articles.length(); i++) {
                //get json article object
                JSONObject article = articles.getJSONObject(i);
                JSONObject source = article.getJSONObject("source");
                //parse json obj and get property
                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");
                String publishedAt = article.getString("publishedAt");
                String idNews = source.getString("id");
                //create news articled instance and add on arraylist
                NewsArticle newsArticle = new NewsArticle(author, title, description, url, urlToImage, publishedAt, idNews);
                //insert news into arrayList
                switch (idNews) {
                    case "bbc-news":
                        NewsArticleSingleton.getInstance().addTopNewsArticle(newsArticle);
                        break;
                    case "ansa":
                        NewsArticleSingleton.getInstance().addWorldNewsArticle(newsArticle);
                        break;
                    case "the-sport-bible":
                        NewsArticleSingleton.getInstance().addSportNewsArticle(newsArticle);
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
