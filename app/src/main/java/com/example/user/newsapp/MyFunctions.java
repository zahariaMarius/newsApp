package com.example.user.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.realm.Realm;

/**
 * Created by zahrsino on 19/12/17.
 */

class MyFunctions {

    public String parseArticleDate(String date) {
        String[] dateSplited = date.split("T");
        String dateDay = dateSplited[0];
        String dateTime = dateSplited[1].substring(0, (dateSplited[1].length() - 1));
        return "Publised " + dateDay + " at " + dateTime;
    }

    public String getComparedDate(String articleDate) {
        String dateStart = articleDate;
        Date dateStop = new Date();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date d1 = null;
        Date d2 = null;
        String returnTime = "";

        try {
            Map<String, Long> map = new HashMap<String, Long>();
            d1 = format.parse(dateStart);
            d2 = dateStop;

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            map.put("Day", diff / (24 * 60 * 60 * 1000));
            map.put("Hours", diff / (60 * 60 * 1000) % 24);
            map.put("Minutes", diff / (60 * 1000) % 60);
            map.put("Seconds", diff / 1000 % 60);

            if (!checkIfDateIsZero(map.get("Day"))) {
                returnTime = map.get("Day") + " Day ago";
            }else {
                if (!checkIfDateIsZero(map.get("Hours"))) {
                    returnTime = map.get("Hours") + " Hours ago";
                }else {
                    if (!checkIfDateIsZero(map.get("Minutes"))) {
                        returnTime = map.get("Minutes")  + " Minutes ago";
                    }else {
                        if (!checkIfDateIsZero(map.get("Seconds"))) {
                            returnTime = map.get("Seconds") + " Seconds ago";
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnTime;
    }

    public Boolean checkIfDateIsZero(Long val) {
        boolean flag = false;
        if (val == 0) {flag = true;}
        return flag;
    }

}
