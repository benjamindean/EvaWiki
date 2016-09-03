package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;


public class ArticleDetailsResponse {

    @SerializedName("items")
    private Map<String, Article> result;

    public Map<String, Article> getArticles() {
        return result;
    }

}
