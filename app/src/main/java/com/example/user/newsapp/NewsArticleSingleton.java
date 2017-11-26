package com.example.user.newsapp;

import java.util.ArrayList;

/**
 * Created by user on 26/11/2017.
 */

public class NewsArticleSingleton {

    private static NewsArticleSingleton newsArticleSingleton = null;
    private ArrayList<NewsArticleData> topNewsArrayList = new ArrayList<NewsArticleData>();
    private ArrayList<NewsArticleData> worldNewsArrayList = new ArrayList<NewsArticleData>();
    private ArrayList<NewsArticleData> sportNewsArrayList = new ArrayList<NewsArticleData>();

    private NewsArticleSingleton() {}

    public static NewsArticleSingleton getInstance() {
        if (newsArticleSingleton == null) {
            newsArticleSingleton = new NewsArticleSingleton();
        }
        return newsArticleSingleton;
    }

    public void addTopNewsArticle(NewsArticleData newsArticleData) {
        this.topNewsArrayList.add(newsArticleData);
    }

    public void addWorldNewsArticle(NewsArticleData newsArticleData) {
        this.worldNewsArrayList.add(newsArticleData);
    }

    public void addSportNewsArticle(NewsArticleData newsArticleData) {
        this.sportNewsArrayList.add(newsArticleData);
    }

    public ArrayList<NewsArticleData> getTopNewsArrayList() {
        return topNewsArrayList;
    }

    public ArrayList<NewsArticleData> getWorldNewsArrayList() {
        return worldNewsArrayList;
    }

    public ArrayList<NewsArticleData> getSportNewsArrayList() {
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
