package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;


public class ArticleImagesContent {

    @SerializedName("src")
    private final String src;
    @SerializedName("text")
    private final String caption;

    public ArticleImagesContent(String src, String caption) {
        this.src = src;
        this.caption = caption;

    }

    public String getSrc() {
        return src.replaceAll("scale-to-width-down/([0-9]+)", "scale-to-width-down/500");
    }

    public String getCaption() {
        return caption;
    }
}