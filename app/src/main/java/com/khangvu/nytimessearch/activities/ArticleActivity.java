package com.khangvu.nytimessearch.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.khangvu.nytimessearch.R;

public class ArticleActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        WebView webView = (WebView) findViewById(R.id.web_view);
        url = getIntent().getStringExtra("url");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_article, menu);
//
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//
//        // pass in the URL currently being used by the WebView
//        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
//
//        miShare.setShareIntent(shareIntent);
//        return super.onCreateOptionsMenu(menu);
//    }
}
