package com.benjaminabel.evawiki.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.activity.ArticleDetailsActivity;
import com.benjaminabel.evawiki.activity.MainActivity;
import com.benjaminabel.evawiki.adapter.ArticlesAdapter;
import com.benjaminabel.evawiki.model.Article;
import com.benjaminabel.evawiki.model.ArticleDetailsResponse;
import com.benjaminabel.evawiki.model.ArticleResponse;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {

    private View view;
    private ProgressBar progressBar;

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
            view = inflater.inflate(R.layout.fragment_articles_list, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            performRequest(args);
        }
        return view;
    }

    private void performRequest(final Bundle args) {

        if (MainActivity.apiService == null) return;
        Call<ArticleResponse> call;
        call = MainActivity.apiService.getTopArticles(args.getInt("limit"), args.getString("category"));

        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() == null) return;

                Log.d("URL", String.valueOf(call.request().url()));

                ArrayList<Article> articleList = new ArrayList<>(response.body().getArticles());
                ArrayList<String> articleIds = new ArrayList<>();

                for (Article article : articleList) {
                    articleIds.add(String.valueOf(article.getId()));
                }

                performDetailsRequest(TextUtils.join(",", articleIds), view);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void onArticleClick(AdapterView<?> adapterView, int i) {
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        Article article = (Article) adapterView.getItemAtPosition(i);

        intent.putExtra(getString(R.string.intent_article_title), article.getTitle());
        intent.putExtra(getString(R.string.intent_article_thumbnail), article.getThumbnail());
        intent.putExtra("article_id", String.valueOf(article.getId()));

        startActivity(intent);

    }

    private void performDetailsRequest(String ids, final View view) {

        Call<ArticleDetailsResponse> call;
        call = MainActivity.apiService.getTopArticlesDetails(ids);

        call.enqueue(new Callback<ArticleDetailsResponse>() {
            @Override
            public void onResponse(Call<ArticleDetailsResponse> call, Response<ArticleDetailsResponse> response) {

                Map<String, Article> map = response.body().getArticles();
                ArrayList<Article> articleList = new ArrayList<>();

                for (Map.Entry<String, Article> entry : map.entrySet()) {
                    articleList.add(entry.getValue());
                }

                ListView listView = (ListView) view.findViewById(R.id.section_label);
                ArticlesAdapter adapter = new ArticlesAdapter(articleList, getContext());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        onArticleClick(adapterView, i);
                    }
                });

                listView.setAdapter(adapter);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArticleDetailsResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}
