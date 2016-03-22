package com.khangvu.nytimessearch.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khangvu.nytimessearch.Models.Article;
import com.khangvu.nytimessearch.R;

import java.util.ArrayList;

/**
 * Created by duyvu on 3/19/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView headlineTextView;
        public ImageView thumbnailImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            headlineTextView = (TextView) itemView.findViewById(R.id.headline_text_view);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
        }
    }

    // Store a member variable for the contacts
    private ArrayList<Article> mArticles;
    private Context mContext;

    // Pass in the contact array into the constructor
    public SearchAdapter(ArrayList<Article> articles) {
        mArticles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.article_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on the data model
        TextView headlineTextView = holder.headlineTextView;
        headlineTextView.setText(article.getmHeadline());

        ImageView thumbnailImageView = holder.thumbnailImageView;
        Glide.with(mContext)
                .load(article.getmThumbNail())
                .placeholder(R.drawable.default_placeholder)
                .fitCenter()
                .into(thumbnailImageView);


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

}
