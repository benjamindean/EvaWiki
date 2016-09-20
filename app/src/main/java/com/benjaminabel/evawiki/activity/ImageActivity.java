package com.benjaminabel.evawiki.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.benjaminabel.evawiki.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        final String imageURL = intent.getStringExtra("image_url");
        String imageCaption = intent.getStringExtra("image_caption");

        ImageView imageView = (ImageView) findViewById(R.id.full_size_image);
        final ImageButton download = (ImageButton) findViewById(R.id.download_image);

        Picasso.with(getApplicationContext())
                .load(imageURL)
                .into(imageView);
        new PhotoViewAttacher(imageView);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(imageURL);
            }
        });
    }

    private void downloadImage(String src) {
    }
}
