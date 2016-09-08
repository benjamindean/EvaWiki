package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;


public class ArticleImagesContent {

    @SerializedName("src")
    private String src;
    @SerializedName("text")
    private String caption;

    public ArticleImagesContent(String src, String caption) {
        this.src = src;
        this.caption = caption;

    }

    public String getSrc() {
        return src;
    }

    public String getCaption() {
        return caption;
    }
}