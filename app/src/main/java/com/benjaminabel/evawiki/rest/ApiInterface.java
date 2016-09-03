package com.benjaminabel.evawiki.rest;

import com.benjaminabel.evawiki.model.ArticleDetailsResponse;
import com.benjaminabel.evawiki.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("Articles/List")
    Call<ArticleResponse> getAllArticles(@Query("limit") int limit, @Query("category") String category);

    @GET("Articles/Details")
    Call<ArticleDetailsResponse> getAllArticlesDetails(@Query("ids") String ids, @Query("limit") int limit, @Query("category") String category);

}
