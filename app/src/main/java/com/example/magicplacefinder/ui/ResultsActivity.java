package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.LatLng;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.SearchState;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import com.example.magicplacefinder.utils.Constants;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

import java.util.List;



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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Log.i(TAG, "mViewModel = " + mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //view model observe results of event, populate view with data
        observeStateChange(mViewModel);
        observeAPICallResults(mViewModel);
    }

    private void observeStateChange(ViewModel viewModel){
        mViewModel.getSearchState().observe(this, new Observer<SearchState>() {
            @Override
            public void onChanged(SearchState searchState) {
                if(searchState != null){
                    switch(searchState) {
                        case IDLE:
                            Log.i(TAG, "observeStateChange = IDLE");
                            //TODO
                            break;
                        case SEARCHING:
                            Log.i(TAG, "observeStateChange = SEARCHING");
                            //TODO
                            break;
                        case NO_RESULTS:
                            Log.i(TAG, "observeStateChange = NO_RESULTS");
                            //TODO
                            break;
                        case FAILED_API_CALL:
                            Log.i(TAG, "observeStateChange = FAILED_API_CALL");
                            //TODO
                            break;
                        case RESULTS_FOUND:
                            Log.i(TAG, "observeStateChange = RESULTS_FOUND");
                            //TODO
                            break;
                        default:
                            Log.i(TAG, "observeStateChange = DEFAULT");
                            //TODO same as idle
                            break;
                    }
                }
            }
        });
    }

    private void observeAPICallResults(ViewModel viewModel){
        mViewModel.getResults().observe(this, new Observer<List<PlaceResponse>>() {
            @Override
            public void onChanged(List<PlaceResponse> placeResponses) {
                if(placeResponses != null){
                    Log.i(TAG, "observeAPICallResults changed()");
                    updateAdapter(placeResponses);
                }
            }
        });
    }

    private void updateAdapter(List<PlaceResponse> placeResponses){

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldSearch){
            LatLng latLng = new LatLng(51.7073855, -0.6104911);
            String latLangString = latLng.toString();
            Log.i(TAG, "Latitude and longitude: " + latLng);
            SearchRequest searchRequest = new SearchRequest(latLng, "restaurants", "thai", "40");
            mViewModel.search(searchRequest);
            //pass event to view model
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stop observing viewmodel
        mViewModel.getResults().removeObservers(this);
        mViewModel.getSearchState().removeObservers(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO: cancel search by using Disposable subscriber in Repository
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //TODO: cancel search by using Disposable subscriber in Repository
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
