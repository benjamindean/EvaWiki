package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by benjamin on 9/3/16.
 */
public class ArticleDetailsResponse {

    @SerializedName("items")
    private Map<String, Article> result;

    public Map<String, Article> getArticles() {
        return result;
    }

}
