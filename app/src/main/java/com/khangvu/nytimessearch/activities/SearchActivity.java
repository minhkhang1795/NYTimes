package com.khangvu.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.khangvu.nytimessearch.Adapters.SearchAdapter;
import com.khangvu.nytimessearch.EndlessRecyclerViewScrollListener;
import com.khangvu.nytimessearch.ItemClickSupport;
import com.khangvu.nytimessearch.Models.Article;
import com.khangvu.nytimessearch.Models.Query;
import com.khangvu.nytimessearch.R;
import com.khangvu.nytimessearch.SpaceItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Article> mArticles = new ArrayList<>();
    ;
    private SearchAdapter mAdapter = new SearchAdapter(mArticles);
    @Bind(R.id.article_recycler_view)
    RecyclerView mArticlesRecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager =
            new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    Query mCurrentQuery = new Query();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setUpView();
        mArticles.clear();
        mAdapter.notifyDataSetChanged();
        getDataWithParams(mCurrentQuery.getParams());
    }

    public void setUpView() {
        mArticlesRecyclerView.setAdapter(mAdapter);
        mArticlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        RecyclerView.ItemDecoration decoration = new SpaceItemDecoration(12);
        mArticlesRecyclerView.addItemDecoration(decoration);
        ItemClickSupport.addTo(mArticlesRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = mArticles.get(position);
                i.putExtra("url", article.getmWebUrl());
                startActivity(i);
            }
        });
        mArticlesRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mCurrentQuery.page = page;
                getDataWithParams(mCurrentQuery.getParams());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestParams params = new RequestParams();
                mCurrentQuery.query = query;
                mCurrentQuery.page = 0;
                mArticles.clear();
                mAdapter.notifyDataSetChanged();
                getDataWithParams(mCurrentQuery.getParams());
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void showFilterSetting(MenuItem item) {
        Intent i = new Intent(this, FilterSettingActivity.class);
        i.putExtra("query", mCurrentQuery);
        startActivityForResult(i, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 200) {
            // Extract name value from result extras
            Bundle bundle = data.getExtras();
            mCurrentQuery = bundle.getParcelable("query_back");
            if (mCurrentQuery != null) mCurrentQuery.page = 0;
            mArticles.clear();
            mAdapter.notifyDataSetChanged();
            getDataWithParams(mCurrentQuery.getParams());
        }
    }

    public void getDataWithParams(RequestParams params) {
        if (isNetworkAvailable()) {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray articleJsonResults = null;
                    try {
                        articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                        mArticles.addAll(Article.fromJSONArray(articleJsonResults));
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG-FAILed", responseString);
                }
            });
        } else {
            Toast.makeText(this, "Network unavailable. Please try again later!", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean isNetworkAvailable() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    //    public void onArticleSearch(View view) {
//        String query = mSearchFieldEditText.getText().toString();
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
//        RequestParams params = new RequestParams();
//
//        params.put("api-key", "892b87ac6fd48069993780e61a589da6:6:70163452");
//        params.put("page", 0);
//        params.put("q", query);
//
//        client.get(url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("DEBUG-SUCCEEDED", response.toString());
//                JSONArray articleJsonResults = null;
//                try {
//                    mAdapter.clear();
//                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
//                    mArticles.addAll(Article.fromJSONArray(articleJsonResults));
//                    mAdapter.addAll(mArticles);
//                    Log.d("DEBUG-SUCCEEDED", mArticles.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("DEBUG-FAILed", responseString);
//            }
//        });
//    }
}