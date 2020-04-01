package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.magicplacefinder.R;
import com.example.magicplacefinder.adapters.PlacesAdapter;
import com.example.magicplacefinder.models.GPSCoords;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.SearchState;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import com.example.magicplacefinder.utils.Constants;
import com.example.magicplacefinder.utils.DateUtils;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = ResultsActivity.class.getSimpleName();
    private static final String CURRENT_SEARCH = "Current search";
    private static final String CURRENT_LATLNG = "Current latlng";
    private static final String SHOULD_SEARCH = "Should search";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Group resultsFoundViews;
    private ProgressBar progressBar;
    private PlacesAdapter adapter;
    PlacesViewModel mViewModel;
    TextView resultsTv;
    TextView resultsSubtitle;
    TextView noResultsTv;
    SearchRequest currentSearchCritera;
    GPSCoords currentGPSCoords;
    boolean shouldSearch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        findViews();
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PlacesViewModel();
            }
        }).get(PlacesViewModel.class);

        if(getIntent() != null){
            shouldSearch = getIntent().getBooleanExtra(Constants.BEGIN_SEARCH, false);
            currentSearchCritera = getIntent().getParcelableExtra(Constants.SEARCH_CRITERIA);
            currentGPSCoords = getIntent().getParcelableExtra(Constants.SEARCH_LATLNG);
        } else if(savedInstanceState !=null){
            currentSearchCritera = savedInstanceState.getParcelable(CURRENT_SEARCH);
            currentGPSCoords = savedInstanceState.getParcelable(CURRENT_LATLNG);
            shouldSearch = savedInstanceState.getBoolean(SHOULD_SEARCH);
        }

        resultsSubtitle.setText(getResources().getString(R.string.results_subtitle, currentSearchCritera.getType(),
                currentSearchCritera.getKeyword(), currentSearchCritera.getRadius(), String.valueOf(currentGPSCoords.getLat()),
                String.valueOf(currentGPSCoords.getLng()), DateUtils.dateToString(new Date())));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.results_title);
        }

        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration   mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        adapter = new PlacesAdapter(this, new ArrayList<PlaceResponse>(0));
        recyclerView.setAdapter(adapter);

}

    @Override
    protected void onStart() {
        super.onStart();
        //view model observe results of event, populate view with data
        observeStateChange();
        observeAPICallResults();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldSearch && currentGPSCoords != null && currentSearchCritera != null){
            currentSearchCritera.setLatlng(currentGPSCoords);
            mViewModel.search(currentSearchCritera);
            shouldSearch = false;
        }
    }

    private void observeStateChange(){
        mViewModel.getSearchState().observe(this, new Observer<SearchState>() {
            @Override
            public void onChanged(SearchState searchState) {
                if(searchState != null){
                    switch(searchState) {
                        case SEARCHING:
                            progressBar.setVisibility(View.VISIBLE);
                            noResultsTv.setVisibility(View.GONE);
                            resultsFoundViews.setVisibility(View.GONE);
                            Log.i(TAG, "Current state: SEARCHING");
                            break;
                        case NO_RESULTS:
                            progressBar.setVisibility(View.GONE);
                            noResultsTv.setVisibility(View.VISIBLE);
                            noResultsTv.setText(getResources().getString(R.string.no_results_found_dialog));
                            //resultsTv.setText(getResources().getString(R.string.no_results_found_dialog));
                            resultsFoundViews.setVisibility(View.GONE);
                            Log.i(TAG, "Current state: NO_RESULTS");
                            break;
                        case FAILED_API_CALL:
                            progressBar.setVisibility(View.GONE);
                            noResultsTv.setVisibility(View.VISIBLE);
                            noResultsTv.setText(getResources().getString(R.string.failed_api_call_dialog));
                            resultsFoundViews.setVisibility(View.GONE);
                            Log.i(TAG, "Current state: FAILED_API_CALL");
                            break;
                        case RESULTS_FOUND:
                            progressBar.setVisibility(View.GONE);
                            noResultsTv.setVisibility(View.GONE);
                            resultsFoundViews.setVisibility(View.VISIBLE);
                            Log.i(TAG, "Current state: RESULTS_FOUND");

                            break;
                        default:
                            progressBar.setVisibility(View.GONE);
                            noResultsTv.setVisibility(View.VISIBLE);
                            resultsFoundViews.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    private void observeAPICallResults(){
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
        ArrayList<PlaceResponse> placesArray = new ArrayList<>(placeResponses);
        adapter.updateAdapter(placesArray);
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


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_SEARCH, currentSearchCritera);
        outState.putParcelable(CURRENT_LATLNG, currentGPSCoords);
        outState.putBoolean(SHOULD_SEARCH, shouldSearch);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSearchCritera = savedInstanceState.getParcelable(CURRENT_SEARCH);
        currentGPSCoords = savedInstanceState.getParcelable(CURRENT_LATLNG);
        shouldSearch = savedInstanceState.getBoolean(SHOULD_SEARCH);
    }

    private void findViews(){
        resultsTv = findViewById(R.id.results_tv);
        recyclerView = findViewById(R.id.places_rv);
        resultsSubtitle = findViewById(R.id.results_subtitle);
        resultsFoundViews = findViewById(R.id.results_group);
        progressBar = findViewById(R.id.progressBar);
        noResultsTv = findViewById(R.id.no_results_tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noResultsTv.setText("");
    }
}
