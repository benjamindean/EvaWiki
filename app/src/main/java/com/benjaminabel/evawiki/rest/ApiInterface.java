package com.benjaminabel.evawiki.rest;

import com.benjaminabel.evawiki.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by khazizovroman on 9/1/16.
 */

public interface ApiInterface {

    @GET("Articles/List")
    Call<ArticleResponse> getAllArticles(@Query("limit") int limit);

}
