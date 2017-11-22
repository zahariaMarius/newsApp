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

    protected void onProgressUpdate() {
    }

    protected void onPostExecute(String response) {
        if (response != null) {
            jsonParse(response);
        }
    }

    protected void jsonParse(String jsonResponse) {
        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray articles = jsonObj.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                Log.i("bbbou", "jsonParse: " + article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
