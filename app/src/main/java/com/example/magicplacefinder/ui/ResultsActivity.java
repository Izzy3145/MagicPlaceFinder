package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.utils.Constants;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = ResultsActivity.class.getSimpleName();
    PlacesViewModel mViewModel;
    TextView resultsTv;
    boolean shouldSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resultsTv = findViewById(R.id.results_tv);

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PlacesViewModel();
            }
        }).get(PlacesViewModel.class);


        if(getIntent() != null){
            shouldSearch = getIntent().getBooleanExtra(Constants.BEGIN_SEARCH, false);
        }

        Log.i(TAG, "mViewModel = " + mViewModel);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //view model observe results of event, populate view with data
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldSearch){
            //pass event to view model
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stop observing viewmodel
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO: cancel search
    }
}
