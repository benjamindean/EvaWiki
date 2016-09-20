package com.benjaminabel.evawiki.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benjaminabel.evawiki.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends AppCompatActivity {

    private DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        final String imageURL = intent.getStringExtra("image_url");
        String imageCaption = intent.getStringExtra("image_caption");

        ImageView imageView = (ImageView) findViewById(R.id.full_size_image);
        TextView imageCaptionView = (TextView) findViewById(R.id.full_size_image_caption);

        if(!imageCaption.isEmpty()) {
            imageCaptionView.setText(imageCaption);
        }

        if(!imageURL.isEmpty()) {
            Picasso.with(getApplicationContext())
                    .load(imageURL)
                    .into(imageView);
            new PhotoViewAttacher(imageView);
        }

        final ImageButton download = (ImageButton) findViewById(R.id.download_image);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(imageURL);
            }
        });
    }

    private void downloadImage(String src) {
        String pattern = ("/revision/(.*)");
        String fullURL = src.replaceAll(pattern, "");

        Uri imageURL = Uri.parse(fullURL);
        String imageName = imageURL.getLastPathSegment();

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        dm.enqueue(new DownloadManager.Request(imageURL)
                .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE
                )
                .setAllowedOverRoaming(false)
                .setTitle(imageName)
                .setDescription(String.valueOf(imageURL))
                .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        imageName
                ));

        Toast.makeText(
                getApplicationContext (),
                "Image saved to " + Environment.DIRECTORY_DOWNLOADS,
                Toast.LENGTH_SHORT
        ).show();
    }
}
