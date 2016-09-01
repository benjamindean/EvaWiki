package com.benjaminabel.evawiki.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.adapter.ArticlesAdapter;
import com.benjaminabel.evawiki.model.Article;
import com.benjaminabel.evawiki.model.ArticleResponse;
import com.benjaminabel.evawiki.rest.ApiClient;
import com.benjaminabel.evawiki.rest.ApiInterface;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesFragment extends Fragment {

    private String title = "Articles";

    public String getTitle() {
        return title;
    }

    public ArticlesFragment() {
    }

    public static ArticlesFragment newInstance() {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_articles, container, false);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArticleResponse> call;
        call = apiService.getAllArticles(25);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArrayList<Article> articleList = new ArrayList<>(response.body().getArticles());
                ListView lv = (ListView) view.findViewById(R.id.section_label);
                lv.setAdapter(new ArticlesAdapter(articleList, getContext()));
            }
            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });

        return view;
    }
}
