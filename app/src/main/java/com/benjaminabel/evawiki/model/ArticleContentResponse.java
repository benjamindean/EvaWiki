package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleContentResponse {

    @SerializedName("sections")
    private List<ArticleContent> content;

    public List<ArticleContent> getArticleContent() {
        return content;
    }

}
