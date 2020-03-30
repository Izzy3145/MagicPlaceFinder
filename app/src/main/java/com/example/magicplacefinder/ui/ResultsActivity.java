package com.example.magicplacefinder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.magicplacefinder.BuildConfig;
import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.LatLng;
import com.example.magicplacefinder.models.NearbyResponse;
import com.example.magicplacefinder.network.RESTClient;
import com.example.magicplacefinder.network.RESTService;
import com.example.magicplacefinder.utils.Constants;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            LatLng latLng = new LatLng(50.3755, 4.1427);
            RESTService restService = RESTClient.getClient().create(RESTService.class);
            restService.getPlaces(latLng.toString(), "shop", "surf", "distance", BuildConfig.PLACES_API_KEY)
                    .enqueue(new Callback<NearbyResponse>() {
                        @Override
                        public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {
                            if(response.body() != null) {
                                resultsTv.setText(response.body().toString());
                            }
                            Log.i(TAG, "onResponse successful");
                        }

                        @Override
                        public void onFailure(Call<NearbyResponse> call, Throwable t) {
                            Log.i(TAG, "onFailure successful");
                        }
                    });
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
