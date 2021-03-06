package com.benjaminabel.evawiki.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleContent {

    @SerializedName("title")
    private final String title;
    @SerializedName("type")
    private final String type;
    @SerializedName("level")
    private final int level;
    @SerializedName("content")
    private final List<ArticleTextContent> content;
    @SerializedName("images")
    private final List<ArticleImagesContent> images;

    public ArticleContent(String title, String type, int level, List<ArticleTextContent> content, List<ArticleImagesContent> images) {
        this.title = title;
        this.type = type;
        this.level = level;
        this.content = content;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public List<ArticleImagesContent> getImages() {
        return images;
    }

    public List<ArticleTextContent> getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}