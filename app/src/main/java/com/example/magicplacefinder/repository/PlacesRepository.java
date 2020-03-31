package com.example.magicplacefinder.repository;

import android.content.Context;
import android.util.Log;

import com.example.magicplacefinder.models.PlaceDetailRequest;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.models.responses.NearbyResponse;
import com.example.magicplacefinder.models.responses.PlaceIdentifier;
import com.example.magicplacefinder.models.responses.PlaceResponse;
import com.example.magicplacefinder.network.RESTClient;
import com.example.magicplacefinder.network.RESTService;
import com.example.magicplacefinder.ui.ResultsActivity;
import com.example.magicplacefinder.utils.RequestListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PlacesRepository {

    private static final String TAG = PlacesRepository.class.getSimpleName();
    private static PlacesRepository instance;

    private PlacesRepository() {
    }

    public synchronized static PlacesRepository getInstance(){
        if(instance == null){
            instance = new PlacesRepository();
        }
        return instance;
    }


    //TODO: if i were to do this again, i would extract the "status" attribute part way through this method, if poss
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
                .subscribe(new Observer<List<PlaceResponse>>() {
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
