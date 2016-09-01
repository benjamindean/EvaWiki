package com.benjaminabel.evawiki.adapter;

import android.content.Context;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Article article = getItem(i);
        ArticleHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ArticleHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_article, viewGroup, false);
            viewHolder.title = (TextView) view.findViewById(R.id.article_title);
            viewHolder.url = (TextView) view.findViewById(R.id.article_url);
            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ArticleHolder) view.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.url.setText(article.getTitle());
        viewHolder.title.setText(article.getUrl());
        // Return the completed view to render on screen
        return view;
    }

    public static class ArticleHolder {
        TextView title;
        TextView url;
    }

    public ArticlesAdapter(ArrayList<Article> articles, Context context) {
        super(context, R.layout.list_item_article, articles);
    }


}
