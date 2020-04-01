package com.example.magicplacefinder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.magicplacefinder.R;
import com.example.magicplacefinder.models.GPSCoords;
import com.example.magicplacefinder.models.SearchRequest;
import com.example.magicplacefinder.utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLocation;
    FloatingActionButton searchButton;
    EditText latEntry;
    EditText lngEntry;
    EditText radiusEntry;
    EditText typeEntry;
    EditText keywordEntry;
    CoordinatorLayout rootLayout;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    Button findLocation;
    Circle mapCircle;
    boolean waitingLocationPermission = false;
    boolean currentLocationFound = false;


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
        findLocation = findViewById(R.id.find_location_btn);

        searchButton.setOnClickListener((View v) -> {
            launchResultsIfNoEmptyFields();});


        Toolbar toolbar = (Toolbar) findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.app_name);
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkLocationPermissions();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        findLocation.setOnClickListener((View v) -> findCurrentLocation());
        radiusEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString()) && !editable.toString().equals("")) {
                    if (currentLocationFound) {
                        if (currentLocation != null) {
                            setCircleAroundCurrentPositon(currentLocation, Double.valueOf(radiusEntry.getText().toString()));
                        }
                    }
                }
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
            GPSCoords newGPSCoords = new GPSCoords(Double.valueOf(latEntry.getText().toString()), Double.valueOf(lngEntry.getText().toString()));
            Intent openResultsActivity = new Intent(SearchActivity.this, ResultsActivity.class);
            openResultsActivity.putExtra(Constants.BEGIN_SEARCH, true);
            openResultsActivity.putExtra(Constants.SEARCH_CRITERIA, newSearch);
            openResultsActivity.putExtra(Constants.SEARCH_LATLNG, newGPSCoords);
            startActivity(openResultsActivity);
        }
    }

    private boolean isNoEntry(EditText entryBox){
        return entryBox.getText().toString().equals("") || TextUtils.isEmpty(entryBox.getText().toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!waitingLocationPermission) {
                requestLocationPermission();
            }
            return;
        }

        //findCurrentLatLng();
    }

    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        waitingLocationPermission = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            waitingLocationPermission = false;
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(mapFragment.getView() != null) {
                    mapFragment.getView().setVisibility(View.VISIBLE);
                }
                findLocation.setEnabled(true);
            } else {
                if(mapFragment.getView() != null) {
                    mapFragment.getView().setVisibility(View.GONE);
                }
                findLocation.setEnabled(false);
            }
        }
    }

    private void findCurrentLocation() {
        if (!mMap.isMyLocationEnabled()) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setIndoorEnabled(false);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, (Location location)->{
                    if(location != null){
                        currentLocationFound = true;
                        latEntry.setText(String.valueOf(location.getLatitude()));
                        lngEntry.setText(String.valueOf(location.getLongitude()));
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
                        setCircleAroundCurrentPositon(currentLocation, Double.valueOf(radiusEntry.getText().toString()));
                    }
                });
    }

    private void setCircleAroundCurrentPositon(LatLng latLng, Double radius){
        if(mapCircle != null){
            mapCircle.remove();
        }
        mapCircle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latLng.latitude, latLng.longitude))
                .radius(radius * 1000)
                .strokeColor(Color.WHITE)
                .fillColor(getResources().getColor(R.color.trans_grey)));
        mapCircle.setCenter(latLng);
    }

}
