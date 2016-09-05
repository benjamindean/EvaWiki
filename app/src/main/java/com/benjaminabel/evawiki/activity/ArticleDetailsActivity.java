package com.benjaminabel.evawiki.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.benjaminabel.evawiki.R;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ArticleID");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(id);
    }
}
