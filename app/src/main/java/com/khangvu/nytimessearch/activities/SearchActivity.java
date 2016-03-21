package com.khangvu.nytimessearch.activities;

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
import android.widget.Button;
import android.widget.Toast;

import com.khangvu.nytimessearch.Article;
import com.khangvu.nytimessearch.EndlessRecyclerViewScrollListener;
import com.khangvu.nytimessearch.ItemClickSupport;
import com.khangvu.nytimessearch.R;
import com.khangvu.nytimessearch.SearchAdapter;
import com.khangvu.nytimessearch.SpaceItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    Button mSearchButton;
    SearchAdapter mAdapter;
    ArrayList<Article> mArticles;
    RecyclerView articlesRecyclerView;
    String mCurrentQuery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpView();
        RequestParams params = new RequestParams();
        getInitialDataWithParams(params);
    }

    public void setUpView() {
        mSearchButton = (Button) findViewById(R.id.search_button);
        articlesRecyclerView = (RecyclerView) findViewById(R.id.article_recycler_view);
        mArticles = new ArrayList<>();
        mAdapter = new SearchAdapter(mArticles);
        assert articlesRecyclerView != null;
        articlesRecyclerView.setAdapter(mAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        RecyclerView.ItemDecoration decoration = new SpaceItemDecoration(12);
        articlesRecyclerView.addItemDecoration(decoration);
        ItemClickSupport.addTo(articlesRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = mArticles.get(position);
                i.putExtra("url", article.getmWebUrl());
                startActivity(i);
            }
        });
        articlesRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestParams params = new RequestParams();
                mCurrentQuery = query;
                params.put("q", query);
                getInitialDataWithParams(params);
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
        Intent i = new Intent(getApplicationContext(), FilterSettingActivity.class);
        startActivityForResult(i, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 200) {
            // Extract name value from result extras
            RequestParams params = new RequestParams();
            params.put("sort", data.getExtras().getString("sortString"));
            params.put("sort", data.getExtras().getString("sortString"));
            int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    public void getInitialDataWithParams(RequestParams params) {
        params.put("api-key", "892b87ac6fd48069993780e61a589da6:6:70163452");
        params.put("page", 0);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG-SUCCEEDED", response.toString());
                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    mArticles.clear();
                    mAdapter.notifyDataSetChanged();
                    mArticles.addAll(Article.fromJSONArray(articleJsonResults));
                    mAdapter.notifyDataSetChanged();
                    articlesRecyclerView.scrollToPosition(0);
                    Log.d("DEBUG-SUCCEEDED", mArticles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG-FAILed", responseString);
            }
        });
    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int offset) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the data source for the adapter
        final ArrayList<Article> moreItems = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("api-key", "892b87ac6fd48069993780e61a589da6:6:70163452");
        params.put("page", offset);
        if (mCurrentQuery != null) params.put("q", mCurrentQuery);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    mArticles.addAll(Article.fromJSONArray(articleJsonResults));
                    mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mArticles.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG-FAILed", responseString);
            }
        });
    }
}
