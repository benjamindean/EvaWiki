package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleContentResponse {

    @SerializedName("sections")
    public List<Article> content;

    public List<Article> getArticleContent() {
        return content;
    }

}
