package com.benjaminabel.evawiki.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.utils.CircleTransform;
import com.squareup.picasso.Picasso;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.article_details_title);
        ImageView imageView = (ImageView) findViewById(R.id.article_details_thumbnail);

        String title = intent.getStringExtra(getString(R.string.intent_article_title));
        String thumbUrl = intent.getStringExtra(getString(R.string.intent_article_thumbnail));

        if(thumbUrl != null) {
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

    }
}
