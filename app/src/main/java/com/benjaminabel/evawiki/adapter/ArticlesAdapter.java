package com.benjaminabel.evawiki.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.benjaminabel.evawiki.R;
import com.benjaminabel.evawiki.model.Article;
import com.benjaminabel.evawiki.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(ArrayList<Article> articles, Context context) {
        super(context, R.layout.list_item_article, articles);
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        final Article article = getItem(i);
        ArticleHolder viewHolder;
        if (view == null) {
            viewHolder = new ArticleHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_article, viewGroup, false);

            viewHolder.title = (TextView) view.findViewById(R.id.article_title);
            //viewHolder.url = (TextView) view.findViewById(R.id.article_url);
            //viewHolder.article_id = (TextView) view.findViewById(R.id.article_id);
            viewHolder.abs = (TextView) view.findViewById(R.id.article_abstract);
            viewHolder.thumb = (ImageView) view.findViewById(R.id.article_thumbnail);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ArticleHolder) view.getTag();
        }

        assert article != null;
        viewHolder.title.setText(article.getTitle());
        //viewHolder.url.setText(article.getUrl());
        //viewHolder.article_id.setText(String.valueOf(article.getId()));
        viewHolder.abs.setText(article.getAbs());
        if (article.getThumbnail() != null) {
            Picasso.with(getContext())
                    .load(article.getThumbnail())
                    .transform(new CircleTransform())
                    .into(viewHolder.thumb);
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(article.getTitle().substring(0, 1), Color.DKGRAY);
            viewHolder.thumb.setImageDrawable(drawable);
        }

        return view;
    }

    private static class ArticleHolder {
        TextView title;
        TextView url;
        TextView article_id;
        TextView abs;
        ImageView thumb;
    }

}
