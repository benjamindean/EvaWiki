package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ArticleContent {

    @SerializedName("title")
    private String title;
    @SerializedName("level")
    private int level;
    @SerializedName("content")
    private List<ArticleTextContent> content;
    @SerializedName("images")
    private ArrayList images;

    public ArticleContent(String title, int level, List<ArticleTextContent> content, ArrayList images) {
        this.title = title;
        this.level = level;
        this.content = content;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList getImages() {
        return images;
    }

    public List<ArticleTextContent> getContent() {
        return content;
    }

    public int getLevel() {
        return level;
    }
}