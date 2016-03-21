package com.khangvu.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.khangvu.nytimessearch.R;

import java.util.ArrayList;
import java.util.List;

public class FilterSettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSortSpinner;
    String sortString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_setting);

        getSupportActionBar().setTitle("Articles Filtering");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSortSpinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        assert mSortSpinner != null;
        mSortSpinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Oldest");
        categories.add("Newest");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mSortSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        sortString = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sortString = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.done_menu_item:
                Intent data = new Intent();
                // Date Begin


                // Desk Values


                // Sort String
                if (sortString.equals("oldest")) data.putExtra("sortString", "Oldest");
                else data.putExtra("sortString", "Newest");

                setResult(RESULT_OK, data);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
