package com.khangvu.nytimessearch.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.khangvu.nytimessearch.R;

/**
 * Created by duyvu on 3/24/16.
 */
public class TextArticleViewHolder extends RecyclerView.ViewHolder {

//    @Bind(R.id.snippet_text_view) TextView snippet;
//    @Bind(R.id.headline_text_view) TextView headline;
    TextView snippet;
    TextView headline;
    public TextView getHeadline() {
        return headline;
    }

    public TextView getSnippet() {
        return snippet;
    }

    public void setSnippet(TextView snippet) {
        this.snippet = snippet;
    }

    public void setHeadline(TextView headline) {
        this.headline = headline;
    }

    public TextArticleViewHolder(View v) {
        super(v);
        headline = (TextView) v.findViewById(R.id.headline_text_view);
        snippet = (TextView) v.findViewById(R.id.snippet_text_view);
    }
}
