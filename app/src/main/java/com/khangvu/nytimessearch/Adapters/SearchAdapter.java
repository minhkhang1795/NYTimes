package com.khangvu.nytimessearch.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.khangvu.nytimessearch.Models.Article;
import com.khangvu.nytimessearch.R;
import com.khangvu.nytimessearch.Views.ImageArticleViewHolder;
import com.khangvu.nytimessearch.Views.TextArticleViewHolder;

import java.util.ArrayList;

/**
 * Created by duyvu on 3/19/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Article> mArticles;
    private final int TEXT = 0, IMAGE = 1;
    private Context mContext;

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // Your holder should contain a member variable
//        // for any view that will be set as you render a row
//        public TextView headlineTextView;
//        public ImageView thumbnailImageView;
//
//        // We also create a constructor that accepts the entire item row
//        // and does the view lookups to find each subview
//        public ViewHolder(View itemView) {
//            // Stores the itemView in a public final member variable that can be used
//            // to access the context from any ViewHolder instance.
//            super(itemView);
//            headlineTextView = (TextView) itemView.findViewById(R.id.headline_text_view);
//            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
//        }
//    }

    // Pass in the contact array into the constructor
    public SearchAdapter(ArrayList<Article> articles) {
        mArticles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        if (mArticles.get(position).getmThumbNail() == "") {
            return TEXT;
        } else {
            return IMAGE;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case TEXT:
                View textArticleView = inflater.inflate(R.layout.text_article_view_holder, parent, false);
                viewHolder = new TextArticleViewHolder(textArticleView);
                break;
            default:
                View imageArticleView = inflater.inflate(R.layout.image_article_view_holder, parent, false);
                viewHolder = new ImageArticleViewHolder(imageArticleView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);
        switch (viewHolder.getItemViewType()) {
            case TEXT:
                TextArticleViewHolder vh1 = (TextArticleViewHolder) viewHolder;
                configureTextViewHolder(vh1, article);
                break;
            default:
                ImageArticleViewHolder vh2 = (ImageArticleViewHolder) viewHolder;
                configureImageViewHolder(vh2, article);
                break;
        }
    }

    private void configureTextViewHolder(TextArticleViewHolder vh1, Article article) {
        vh1.getHeadline().setText(article.getmHeadline());
        vh1.getSnippet().setText(article.getmSnippet());
    }

    private void configureImageViewHolder(ImageArticleViewHolder vh2, Article article) {
        vh2.getHeadline().setText(article.getmHeadline());
        Glide.with(mContext)
                .load(article.getmThumbNail())
                .placeholder(R.drawable.default_placeholder)
                .fitCenter()
                .into(vh2.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

}
