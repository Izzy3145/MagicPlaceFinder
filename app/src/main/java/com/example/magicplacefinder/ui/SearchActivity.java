package com.example.magicplacefinder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.LatLng;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    Button searchButton;
    EditText latEntry;
    EditText lngEntry;
    EditText radiusEntry;
    EditText typeEntry;
    EditText keywordEntry;
    ConstraintLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.search_button);
        latEntry = findViewById(R.id.lat_et);
        lngEntry = findViewById(R.id.lon_et);
        radiusEntry = findViewById(R.id.radius_et);
        typeEntry = findViewById(R.id.type_et);
        keywordEntry = findViewById(R.id.keyword_et);
        rootLayout = findViewById(R.id.search_root);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchResultsIfNoEmptyFields();

            }
        });
    }

    private void launchResultsIfNoEmptyFields(){
        if(isNoEntry(latEntry)){
            Snackbar snackbar = Snackbar
                    .make(rootLayout, getResources().getString(R.string.enter_lat_value), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (isNoEntry(lngEntry)){
            Snackbar snackbar = Snackbar
                    .make(rootLayout, getResources().getString(R.string.enter_lng_value), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (isNoEntry(radiusEntry)){
            Snackbar snackbar = Snackbar
                    .make(rootLayout, getResources().getString(R.string.enter_radius_value), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (isNoEntry(typeEntry)){
            Snackbar snackbar = Snackbar
                    .make(rootLayout, getResources().getString(R.string.enter_type_value), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (isNoEntry(keywordEntry)){
            Snackbar snackbar = Snackbar
                    .make(rootLayout, getResources().getString(R.string.enter_keyword_value), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            SearchRequest newSearch = new SearchRequest(radiusEntry.getText().toString(),
                    typeEntry.getText().toString(), keywordEntry.getText().toString());
            LatLng newLatLng = new LatLng(Double.valueOf(latEntry.getText().toString()), Double.valueOf(lngEntry.getText().toString()));
            Intent openResultsActivity = new Intent(SearchActivity.this, ResultsActivity.class);
            openResultsActivity.putExtra(Constants.BEGIN_SEARCH, true);
            openResultsActivity.putExtra(Constants.SEARCH_CRITERIA, newSearch);
            openResultsActivity.putExtra(Constants.SEARCH_LATLNG, newLatLng);
            startActivity(openResultsActivity);
        }
    }

    private boolean isNoEntry(EditText entryBox){
        return entryBox.getText().toString().equals("") || TextUtils.isEmpty(entryBox.getText().toString());
    }
}
