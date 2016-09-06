package com.benjaminabel.evawiki.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.model.ArticleContent;
import com.benjaminabel.evawiki.model.ArticleContentResponse;
import com.benjaminabel.evawiki.rest.ApiClient;
import com.benjaminabel.evawiki.rest.ApiInterface;
import com.benjaminabel.evawiki.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.article_details_title);
        ImageView imageView = (ImageView) findViewById(R.id.article_details_thumbnail);

        String title = intent.getStringExtra(getString(R.string.intent_article_title));
        String thumbUrl = intent.getStringExtra(getString(R.string.intent_article_thumbnail));

        if (thumbUrl != null) {
            Picasso.with(getApplicationContext())
                    .load(thumbUrl)
                    .transform(new CircleTransform())
                    .into(imageView);
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(title.substring(0, 1), Color.DKGRAY);
            imageView.setImageDrawable(drawable);
        }

        textView.setText(title);

        setArticleContent(intent.getStringExtra("article_id"));

    }

    public void setArticleContent(String id) {

        Call<ArticleContentResponse> call;
        call = apiService.getArticleContent(id);

        call.enqueue(new Callback<ArticleContentResponse>() {
            @Override
            public void onResponse(Call<ArticleContentResponse> call, Response<ArticleContentResponse> response) {
                Log.d("ARTICLE", String.valueOf(call.request().url()));
                List<ArticleContent> map = response.body().getArticleContent();

                for (ArticleContent element : map) {
                    Log.d("ARTICLE", String.valueOf(element.getTitle()));
                }

            }

            @Override
            public void onFailure(Call<ArticleContentResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }
}


