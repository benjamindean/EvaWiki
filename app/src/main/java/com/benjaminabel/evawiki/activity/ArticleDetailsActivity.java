package com.benjaminabel.evawiki.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.model.ArticleContent;
import com.benjaminabel.evawiki.model.ArticleContentResponse;
import com.benjaminabel.evawiki.model.ArticleImagesContent;
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

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private LinearLayout layout;
    private LayoutInflater inflater;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        layout = (LinearLayout) findViewById(R.id.layout_details);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        inflater = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.article_details_title);
        ImageView imageView = (ImageView) findViewById(R.id.article_details_thumbnail);

        String title = intent.getStringExtra(getString(R.string.intent_article_title));
        String thumbUrl = intent.getStringExtra(getString(R.string.intent_article_thumbnail));

        if (thumbUrl != null) {
            String pattern = ("/revision/(.*)");
            String fullImageURL = thumbUrl.replaceAll(pattern, "");
            Picasso.with(getApplicationContext())
                    .load(fullImageURL)
                    .into(imageView);
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(title.substring(0, 1), Color.DKGRAY);
            imageView.setImageDrawable(drawable);
        }

        textView.setText(title);

        setArticleContent(intent.getStringExtra("article_id"));
    }

    private void setArticleContent(String id) {
        Call<ArticleContentResponse> call;
        call = apiService.getArticleContent(id);

        call.enqueue(new Callback<ArticleContentResponse>() {
            @Override
            public void onResponse(Call<ArticleContentResponse> call, Response<ArticleContentResponse> response) {
                Log.d("URL", String.valueOf(call.request().url()));
                List<ArticleContent> map = response.body().getArticleContent();
                int index = 0;
                TextView lastHeading = null;
                for (ArticleContent element : map) {
                    if (index != 0) {
                        String articleTitle = element.getTitle();
                        if (!Objects.equals(articleTitle, "")) {
                            lastHeading = createTextView(articleTitle, R.layout.partial_article_heading);
                            layout.addView(lastHeading);
                        }
                    }
                    for (ArticleTextContent content : element.getContent()) {
                        String articleParagraph = content.getText();
                        if (!Objects.equals(articleParagraph, null) && (!Objects.equals(content.getType(), "list"))) {
                            layout.addView(createTextView(articleParagraph, R.layout.partial_article_paragraph));
                        } else {
                            layout.removeView(lastHeading);
                        }
                    }
                    for (ArticleImagesContent images : element.getImages()) {
                        String imageURL = images.getSrc();
                        if (!Objects.equals(imageURL, "") && !(imageURL.contains("_Icon.png"))) {
                            layout.addView(createImageView(imageURL, images.getCaption()));
                        }
                    }
                    index++;
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
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

    private LinearLayout createImageView(final String imageURL, final String caption) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.partial_article_image, null);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.article_image);
        TextView textView = (TextView) linearLayout.findViewById(R.id.article_image_caption);
        Picasso.with(getApplicationContext())
                .load(imageURL)
                .into(imageView);
        if (caption != null && !caption.isEmpty()) {
            textView.setText(caption);
        } else {
            textView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(imageURL, caption);
            }
        });
        return linearLayout;
    }

    private void onImageClick(String imageURL, String caption) {
        Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
        intent.putExtra("image_url", imageURL);
        intent.putExtra("image_caption", caption);
        startActivity(intent);
    }
}


