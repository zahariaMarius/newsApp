package com.example.user.newsapp;

import android.util.Log;

/**
 * Created by zahrsino on 19/12/17.
 */

class MyFunctions {

    public String parseArticleDate(String date) {
        String[] dateSplited = date.split("T");
        String dateDay = dateSplited[0];
        String dateTime = dateSplited[1].substring(0, (dateSplited[1].length() - 1));
        return dateDay + " AT " + dateTime;
    }
}
