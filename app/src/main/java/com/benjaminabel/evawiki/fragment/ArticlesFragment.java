package com.benjaminabel.evawiki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.adapter.ArticlesAdapter;
import com.benjaminabel.evawiki.model.Article;
import com.benjaminabel.evawiki.model.ArticleDetailsResponse;
import com.benjaminabel.evawiki.model.ArticleResponse;
import com.benjaminabel.evawiki.rest.ApiClient;
import com.benjaminabel.evawiki.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {

    public ArticlesFragment() {
    }

    public static ArticlesFragment newInstance(String category, int limit) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putInt("limit", limit);
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle args = getArguments();
        final View view = inflater.inflate(R.layout.fragment_articles, container, false);
        idsCallback callback = new idsCallback() {
            @Override
            public void getIds(String ids) {
                performFullRequest(ids, view, args);
            }
        };
        performRequest(args, callback);
        return view;
    }


    public interface idsCallback {
        void getIds(String ids);
    }

    public void performRequest(Bundle args, final idsCallback callback) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArticleResponse> call;
        call = apiService.getAllArticles(args.getInt("limit"), args.getString("category"));
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArrayList<Article> articleList = new ArrayList<>(response.body().getArticles());
                ArrayList<String> articleIds = new ArrayList<>();

                for (Article article : articleList) {
                    articleIds.add(String.valueOf(article.getId()));
                }
                callback.getIds(TextUtils.join(",", articleIds));
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    public void performFullRequest(String ids, final View view, Bundle args) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArticleDetailsResponse> call;
        call = apiService.getAllArticlesDetails(ids, args.getInt("limit"), args.getString("category"));
        call.enqueue(new Callback<ArticleDetailsResponse>() {
            @Override
            public void onResponse(Call<ArticleDetailsResponse> call, Response<ArticleDetailsResponse> response) {
                Map<String, Article> map = response.body().getArticles();
                ArrayList<Article> articleList = new ArrayList<>();
                for (Map.Entry<String, Article> entry : map.entrySet()) {
                    articleList.add(entry.getValue());
                }
                ListView lv = (ListView) view.findViewById(R.id.section_label);
                lv.setAdapter(new ArticlesAdapter(articleList, getContext()));
            }

            @Override
            public void onFailure(Call<ArticleDetailsResponse> call, Throwable t) {
                Log.d("TAG", String.valueOf(call.request().url()));
                Log.d("Error", t.toString());
            }
        });
    }
}
