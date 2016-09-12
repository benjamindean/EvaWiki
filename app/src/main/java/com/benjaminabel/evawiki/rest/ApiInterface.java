package com.benjaminabel.evawiki.rest;

import com.benjaminabel.evawiki.model.ArticleContentResponse;
import com.benjaminabel.evawiki.model.ArticleDetailsResponse;
import com.benjaminabel.evawiki.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("Articles/Top")
    Call<ArticleResponse> getTopArticles(
            @Query("limit") int limit,
            @Query("category") String category
    );

    @GET("Articles/Details")
    Call<ArticleDetailsResponse> getTopArticlesDetails(
            @Query("ids") String ids
    );

    @GET("Articles/AsSimpleJson")
    Call<ArticleContentResponse> getArticleContent(
            @Query("id") String id
    );

}
