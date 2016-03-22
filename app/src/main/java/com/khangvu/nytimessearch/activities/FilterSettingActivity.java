package com.khangvu.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.khangvu.nytimessearch.Models.Query;
import com.khangvu.nytimessearch.R;

import java.util.ArrayList;
import java.util.List;

public class FilterSettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Query mCurrentQuery;
    Spinner mSortSpinner;
    CheckBox mCheckBox1, mCheckBox2, mCheckBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_setting);
        Bundle bundle = this.getIntent().getExtras();
        mCurrentQuery = bundle.getParcelable("query");

        getSupportActionBar().setTitle("Article Filtering");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpDatePicker();
        setUpCheckBox();
        setUpSortSpinner();
    }

    public void setUpDatePicker() {

    }

    public void setUpCheckBox() {
        mCheckBox1 = (CheckBox) findViewById(R.id.check_box_1);
        mCheckBox2 = (CheckBox) findViewById(R.id.check_box_2);
        mCheckBox3 = (CheckBox) findViewById(R.id.check_box_3);

        if (mCurrentQuery.desks.contains("Arts")) mCheckBox1.setChecked(true);
        if (mCurrentQuery.desks.contains("Fashion & Style")) mCheckBox2.setChecked(true);
        if (mCurrentQuery.desks.contains("Sports")) mCheckBox3.setChecked(true);
    }

    public void setUpSortSpinner() {
        mSortSpinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        assert mSortSpinner != null;
        mSortSpinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("None");
        categories.add("Oldest");
        categories.add("Newest");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mSortSpinner.setAdapter(dataAdapter);
        switch (mCurrentQuery.sort) {
            case "":
                mSortSpinner.setSelection(0);
                break;
            case "oldest":
                mSortSpinner.setSelection(1);
                break;
            case "newest":
                mSortSpinner.setSelection(2);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (position) {
            case 0:
                mCurrentQuery.sort = "";
                break;
            case 1:
                mCurrentQuery.sort = "oldest";
                break;
            case 2:
                mCurrentQuery.sort = "newest";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                if (mCheckBox1.isChecked())
                    mCurrentQuery.desks.add("Arts");
                else mCurrentQuery.desks.remove("Arts");
                if (mCheckBox2.isChecked())
                    mCurrentQuery.desks.add("Fashion & Style");
                else mCurrentQuery.desks.remove("Fashion & Style");
                if (mCheckBox3.isChecked())
                    mCurrentQuery.desks.add("Sports");
                else mCurrentQuery.desks.remove("Sports");

                // Sort String
                Bundle b = new Bundle();
                b.putParcelable("query_back", mCurrentQuery);
                data.putExtras(b);

                setResult(RESULT_OK, data);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
