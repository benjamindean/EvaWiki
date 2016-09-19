package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;


public class Article {

    @SerializedName("id")
    private final int article_id;
    @SerializedName("title")
    private final String title;
    @SerializedName("url")
    private final String url;
    @SerializedName("abstract")
    private final String abs;
    @SerializedName("thumbnail")
    private final String thumbnail;

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