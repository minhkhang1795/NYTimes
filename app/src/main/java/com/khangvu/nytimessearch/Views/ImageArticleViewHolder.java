package com.khangvu.nytimessearch.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khangvu.nytimessearch.R;

/**
 * Created by duyvu on 3/24/16.
 */
public class ImageArticleViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnail;
    TextView headline;

    public TextView getHeadline() {
        return headline;
    }

    public void setHeadline(TextView headline) {
        this.headline = headline;
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageView thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageArticleViewHolder(View v) {
        super(v);
        thumbnail = (ImageView) v.findViewById(R.id.thumbnail_image_view);
        headline = (TextView) v.findViewById(R.id.headline_text_view_2);
    }
}
