 package com.khangvu.nytimessearch.activities;

 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.webkit.WebView;
 import android.webkit.WebViewClient;

 import com.khangvu.nytimessearch.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String url = getIntent().getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.web_view);
        assert webView != null;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
