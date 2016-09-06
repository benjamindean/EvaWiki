package com.benjaminabel.evawiki.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benjaminabel.evawiki.R;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        TextView textView = (TextView) findViewById(R.id.article_details_title);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setTransitionName(getString(R.string.transition_article_details));
        }

        Intent intent = getIntent();
        String id = intent.getStringExtra(getString(R.string.intent_article_id));
        textView.setText(id);
    }
}
