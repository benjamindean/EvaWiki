package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;


public class ArticleTextContent {

    @SerializedName("type")
    private final String type;
    @SerializedName("text")
    private final String text;

    public ArticleTextContent(String type, String text) {
        this.type = type;
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}