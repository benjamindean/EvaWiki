package com.benjaminabel.evawiki.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.model.ArticleContent;
import com.benjaminabel.evawiki.model.ArticleContentResponse;
import com.benjaminabel.evawiki.model.ArticleTextContent;
import com.benjaminabel.evawiki.rest.ApiClient;
import com.benjaminabel.evawiki.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ApiInterface apiService;
    private LinearLayout layout;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        layout = (LinearLayout) findViewById(R.id.layout_details);
        inflater = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.article_details_title);
        ImageView imageView = (ImageView) findViewById(R.id.article_details_thumbnail);

        String title = intent.getStringExtra(getString(R.string.intent_article_title));
        String thumbUrl = intent.getStringExtra(getString(R.string.intent_article_thumbnail));

        if (thumbUrl != null) {
            Picasso.with(getApplicationContext())
                    .load(thumbUrl)
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
                List<ArticleContent> map = response.body().getArticleContent();
                for (ArticleContent element : map) {
                    String articleTitle = element.getTitle();
                    if(!Objects.equals(articleTitle, "")) {
                        layout.addView(createTextView(articleTitle, R.layout.partial_article_heading));
                    }
                    for(ArticleTextContent content : element.getContent()) {
                        String articleParagraph = content.getText();
                        if(!Objects.equals(articleParagraph, "")) {
                            layout.addView(createTextView(content.getText(), R.layout.partial_article_paragraph));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleContentResponse> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private TextView createTextView(String text, int template) {
        TextView textView = (TextView) inflater.inflate(template, null);
        textView.setText(text);
        return textView;
    }
}


