package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.LatLng;
import com.example.magicplacefinder.models.PlaceDetailRequest;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.SearchState;
import com.example.magicplacefinder.models.responses.PlaceIdentifier;
import com.example.magicplacefinder.models.responses.PlaceResponse;

import com.example.magicplacefinder.network.RESTClient;
import com.example.magicplacefinder.network.RESTService;
import com.example.magicplacefinder.repository.PlacesRepository;
import com.example.magicplacefinder.utils.Constants;
import com.example.magicplacefinder.utils.RequestListener;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
            LatLng latLng = new LatLng(50.412524, -5.1119065);
            String latLangString = latLng.toString();
            Log.i(TAG, "Latitude and longitude: " + latLng);
            SearchRequest searchRequest = new SearchRequest(latLng, "Shop", "surf", "50");
            //mViewModel.search(searchRequest);
            fetchPlaceDetailsFollowingGeneralPlaceInfo(searchRequest, new RequestListener() {
                @Override
                public void onResultsFound(List<PlaceResponse> results) {
                    Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo RESULTS FOUND");

                }

                @Override
                public void onApiCallFailure(Throwable e) {
                    Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo API CALL FAILURE");

                }

                @Override
                public void noResults() {
                    Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo NO RESULTS");

                }
            });
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
        //TODO: cancel search
    }

    private static Observable<List<PlaceIdentifier>> fetchGeneralInfoOfPlaces(SearchRequest searchRequest){
        RESTService restService = RESTClient.getClient().create(RESTService.class);
        return restService.getPlaces(searchRequest.getLatlng().toString(), searchRequest.getKeyword(),
                searchRequest.getType(), searchRequest.getRadius(), searchRequest.getApiKey())
                .flatMap(x -> Flowable.just(x.getResults()))
                .subscribeOn(Schedulers.io())
                .toObservable()
                .timeout(15, TimeUnit.SECONDS);
    }

    private static Observable<PlaceResponse> fetchPlaceDetailData(String placeID){
        PlaceDetailRequest detailRequest = new PlaceDetailRequest(placeID);
        RESTService restService = RESTClient.getClient().create(RESTService.class);
        return restService.getPlaceDetails(detailRequest.getPlaceID(),detailRequest.getFields(), detailRequest.getApiKey())
                .subscribeOn(Schedulers.io())
                .toObservable()
                .timeout(10, TimeUnit.SECONDS);
    }

    public void fetchPlaceDetailsFollowingGeneralPlaceInfo(SearchRequest searchRequest, final RequestListener callback) {
        fetchGeneralInfoOfPlaces(searchRequest)
                .flatMapIterable(new Function<List<PlaceIdentifier>, Iterable<PlaceIdentifier>>() {
                    @Override
                    public Iterable<PlaceIdentifier> apply(List<PlaceIdentifier> results) throws Exception {
                        return results;
                    }
                })
                .flatMap(x -> fetchPlaceDetailData(x.getPlaceId()))
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
                .subscribe(new io.reactivex.Observer<List<PlaceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo onSubscribe");
                    }

                    @Override
                    public void onNext(List<PlaceResponse> place) {
                        if(!place.toString().equals("[]")) {
                            callback.onResultsFound(place);
                        } else {
                            callback.noResults();
                        }
                        Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo onNext" + place.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "fetchPlaceDetailsFollowingGeneralPlaceInfo onError" + e.getMessage());
                        callback.onApiCallFailure(e);

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "ViewModel onComplete");

                    }
                });
    }
}
