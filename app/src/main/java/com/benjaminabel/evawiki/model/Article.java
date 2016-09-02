package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by khazizovroman on 9/1/16.
 */

public class Article {

    @SerializedName("id")
    private int article_id;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("abstract")
    private String abs;
    @SerializedName("thumbnail")
    private String thumbnail;

    public Article(int article_id, String title, String url, String abs, String thumbnail) {
        this.article_id = article_id;
        this.title = title;
        this.url = url;
        this.abs = abs;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return article_id;
    }

    public String getAbs() {
        return abs;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}