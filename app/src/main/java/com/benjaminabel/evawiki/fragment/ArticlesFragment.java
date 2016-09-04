package com.benjaminabel.evawiki.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.activity.MainActivity;
import com.benjaminabel.evawiki.adapter.ArticlesAdapter;
import com.benjaminabel.evawiki.model.Article;
import com.benjaminabel.evawiki.model.ArticleDetailsResponse;
import com.benjaminabel.evawiki.model.ArticleResponse;
import com.benjaminabel.evawiki.rest.ApiClient;
import com.benjaminabel.evawiki.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {

    public ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    public View view;

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

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_articles, container, false);

            final getItemIds callback = new getItemIds() {
                @Override
                public void getIds(String ids) {
                    performDetailsRequest(ids, view);
                }
            };

            performRequest(args, callback);
        }
        return view;
    }


    public interface getItemIds {
        void getIds(String ids);
    }

    public void performRequest(final Bundle args, final getItemIds callback) {

        Call<ArticleResponse> call;
        call = apiService.getTopArticles(args.getInt("limit"), args.getString("category"));

        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if(response.body() == null) return;

                Log.d("URL", String.valueOf(call.request().url()));

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

    public void performDetailsRequest(String ids, final View view) {

        Call<ArticleDetailsResponse> call;
        call = apiService.getTopArticlesDetails(ids);

        call.enqueue(new Callback<ArticleDetailsResponse>() {
            @Override
            public void onResponse(Call<ArticleDetailsResponse> call, Response<ArticleDetailsResponse> response) {

                Map<String, Article> map = response.body().getArticles();
                ArrayList<Article> articleList = new ArrayList<>();

                String regex = "/wiki/[0-9a-zA-z]+$";
                Pattern patt = Pattern.compile(regex);

                for (Map.Entry<String, Article> entry : map.entrySet()) {
                    Article item = entry.getValue();

                    if(patt.matcher(item.getUrl()).matches()) {
                        articleList.add(entry.getValue());
                    }
                }

                ListView lv = (ListView) view.findViewById(R.id.section_label);
                ArticlesAdapter adapter = new ArticlesAdapter(articleList, getContext());
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArticleDetailsResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
