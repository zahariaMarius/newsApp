package com.example.user.newsapp;

import java.io.Serializable;
import java.net.URL;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 25/11/2017.
 */

public class NewsArticle extends RealmObject implements Serializable{

    @PrimaryKey
    private String uuid;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private static final long serialVersionUID = 1;

    public NewsArticle(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.uuid = UUID.randomUUID().toString();
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public NewsArticle() {
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
