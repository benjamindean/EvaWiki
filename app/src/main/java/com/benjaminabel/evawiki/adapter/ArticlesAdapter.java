package com.benjaminabel.evawiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.model.Article;

import java.util.ArrayList;

/**
 * Created by khazizovroman on 9/1/16.
 */

public class ArticlesAdapter extends ArrayAdapter<Article> {

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        Article article = getItem(i);
        ArticleHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ArticleHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_article, viewGroup, false);

            viewHolder.title = (TextView) view.findViewById(R.id.article_title);
            viewHolder.url = (TextView) view.findViewById(R.id.article_url);
            viewHolder.article_id = (TextView) view.findViewById(R.id.article_id);
            viewHolder.abs = (TextView) view.findViewById(R.id.article_abstract);
            viewHolder.thumb = (TextView) view.findViewById(R.id.article_thumbnail);

            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ArticleHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        assert article != null;
        viewHolder.url.setText(article.getTitle());
        viewHolder.title.setText(article.getUrl());
        viewHolder.article_id.setText(String.valueOf(article.getId()));
        viewHolder.abs.setText(article.getAbs());
        viewHolder.thumb.setText(article.getThumbnail());
        // Return the completed view to render on screen
        return view;
    }

    private static class ArticleHolder {
        TextView title;
        TextView url;
        TextView article_id;
        TextView abs;
        TextView thumb;
    }

    public ArticlesAdapter(ArrayList<Article> articles, Context context) {
        super(context, R.layout.list_item_article, articles);
    }


}
