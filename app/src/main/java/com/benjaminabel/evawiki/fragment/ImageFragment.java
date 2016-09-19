package com.benjaminabel.evawiki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.benjaminabel.evawiki.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageFragment extends Fragment {

    private static final String IMAGE_URL = "";
    private String imageURL;

    public ImageFragment() {
    }

    public static ImageFragment newInstance(String value) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            imageURL = getArguments().getString(IMAGE_URL);
        }

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.full_size_image);

        Picasso.with(getContext())
                .load(imageURL)
                .into(imageView);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);

        return view;
    }
}
