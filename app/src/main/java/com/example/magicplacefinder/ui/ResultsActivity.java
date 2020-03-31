package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.magicplacefinder.R;
import com.example.magicplacefinder.adapter.PlacesAdapter;
import com.example.magicplacefinder.models.LatLng;
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
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PlacesAdapter adapter;
    PlacesViewModel mViewModel;
    TextView resultsTv;
    TextView resultsSubtitle;
    SearchRequest currentSearchCritera;
    LatLng currentLatLng;
    boolean shouldSearch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resultsTv = findViewById(R.id.results_tv);
        recyclerView = findViewById(R.id.places_rv);
        resultsSubtitle = findViewById(R.id.results_subtitle);
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
            currentLatLng = getIntent().getParcelableExtra(Constants.SEARCH_LATLNG);
        } else if(savedInstanceState !=null){
            currentSearchCritera = savedInstanceState.getParcelable(CURRENT_SEARCH);
            currentLatLng = savedInstanceState.getParcelable(CURRENT_LATLNG);
        }

        resultsSubtitle.setText(getResources().getString(R.string.results_subtitle, currentSearchCritera.getType(),
                currentSearchCritera.getKeyword(), currentSearchCritera.getRadius(), String.valueOf(currentLatLng.getLat()),
                String.valueOf(currentLatLng.getLng()), DateUtils.dateToString(new Date())));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.results_title);
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new PlacesAdapter(this, new ArrayList<PlaceResponse>(0));
        recyclerView.setAdapter(adapter);

}

    @Override
    protected void onStart() {
        super.onStart();
        //view model observe results of event, populate view with data
        observeStateChange(mViewModel);
        observeAPICallResults(mViewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldSearch && currentLatLng != null && currentSearchCritera != null){
            currentSearchCritera.setLatlng(currentLatLng);
            mViewModel.search(currentSearchCritera);
            shouldSearch = false;
        }
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
        outState.putParcelable(CURRENT_SEARCH, currentSearchCritera);
        outState.putParcelable(CURRENT_LATLNG, currentLatLng);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        currentSearchCritera = savedInstanceState.getParcelable(CURRENT_SEARCH);
        currentLatLng = savedInstanceState.getParcelable(CURRENT_LATLNG);

        super.onRestoreInstanceState(savedInstanceState);
    }
}
