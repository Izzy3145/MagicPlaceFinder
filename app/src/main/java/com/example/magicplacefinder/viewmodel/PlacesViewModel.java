package com.example.magicplacefinder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.SearchState;
import com.example.magicplacefinder.models.responses.PlaceResponse;
import com.example.magicplacefinder.repository.PlacesRepository;
import com.example.magicplacefinder.utils.RequestListener;

import java.util.List;


public class PlacesViewModel extends ViewModel implements RequestListener {

    private static final String TAG = PlacesViewModel.class.getSimpleName();
    private MutableLiveData<List<PlaceResponse>> placesList;
    private PlacesRepository placesRepository;
    private MutableLiveData<SearchState> searchState;

    public PlacesViewModel(){
        init();
    }

    void init(){
        placesRepository = placesRepository.getInstance();

        if(placesList == null){
            placesList = new MutableLiveData<List<PlaceResponse>>();
        }
        if(searchState == null){
            searchState = new MutableLiveData<SearchState>();
        } else {
            searchState.postValue(SearchState.IDLE);
        }
    }

    public void search(SearchRequest searchRequest){
        searchState.postValue(SearchState.SEARCHING);
        placesRepository.fetchPlaceDetailsFollowingGeneralPlaceInfo(searchRequest, this);
    }

    public MutableLiveData<List<PlaceResponse>> getResults(){
        return placesList;
    }

    public MutableLiveData<SearchState> getSearchState(){
        return searchState;
    }

    @Override
    public void onResultsFound(List<PlaceResponse> results) {
        searchState.postValue(SearchState.RESULTS_FOUND);
        placesList.postValue(results);
    }

    @Override
    public void onApiCallFailure(Throwable e) {
        searchState.postValue(SearchState.FAILED_API_CALL);
    }

    @Override
    public void noResults() {
        searchState.postValue(SearchState.NO_RESULTS);
    }


}
