package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleResponse {

    @SerializedName("items")
    public List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

}
