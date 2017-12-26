package com.example.user.newsapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zahrsino on 25/12/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("realmFavoriteArticles.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
