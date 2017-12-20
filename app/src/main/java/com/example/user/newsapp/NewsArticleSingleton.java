package com.example.user.newsapp;

import java.util.ArrayList;

/**
 * Created by user on 26/11/2017.
 */

public class NewsArticleSingleton {

    private static NewsArticleSingleton newsArticleSingleton = null;
    private ArrayList<NewsArticle> topNewsArrayList = new ArrayList<NewsArticle>();
    private ArrayList<NewsArticle> worldNewsArrayList = new ArrayList<NewsArticle>();
    private ArrayList<NewsArticle> sportNewsArrayList = new ArrayList<NewsArticle>();

    private NewsArticleSingleton() {}

    public static NewsArticleSingleton getInstance() {
        if (newsArticleSingleton == null) {
            newsArticleSingleton = new NewsArticleSingleton();
        }
        return newsArticleSingleton;
    }

    public void addTopNewsArticle(NewsArticle newsArticle) {
        this.topNewsArrayList.add(newsArticle);
    }

    public void addWorldNewsArticle(NewsArticle newsArticle) {
        this.worldNewsArrayList.add(newsArticle);
    }

    public void addSportNewsArticle(NewsArticle newsArticle) {
        this.sportNewsArrayList.add(newsArticle);
    }

    public ArrayList<NewsArticle> getTopNewsArrayList() {
        return topNewsArrayList;
    }

    public ArrayList<NewsArticle> getWorldNewsArrayList() {
        return worldNewsArrayList;
    }

    public ArrayList<NewsArticle> getSportNewsArrayList() {
        return sportNewsArrayList;
    }

    public void clearTopNewsArticles() {
        this.topNewsArrayList.clear();
    }

    public void clearWorldNewsArticles() {
        this.worldNewsArrayList.clear();
    }

    public void clearSportNewsArticles() {
        this.sportNewsArrayList.clear();
    }

    public void clearAllNewsArticles() {
        this.topNewsArrayList.clear();
        this.worldNewsArrayList.clear();
        this.sportNewsArrayList.clear();
    }
}
