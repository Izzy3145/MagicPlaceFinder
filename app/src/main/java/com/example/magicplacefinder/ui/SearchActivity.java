package com.example.magicplacefinder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openResultsActivity = new Intent(SearchActivity.this, ResultsActivity.class);
                openResultsActivity.putExtra(Constants.BEGIN_SEARCH, false);
                //TODO: add bundle with search parameters
                startActivity(openResultsActivity);
            }
        });
    }
}
