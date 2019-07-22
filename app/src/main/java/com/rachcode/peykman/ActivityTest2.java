package com.rachcode.peykman;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.rachcode.peykman.mSend.SendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rachcode.peykman.config.General.BOUNDS;

public class ActivityTest2 extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private PlaceAutocompleteAdapterAddress mAdapter;
    private GoogleApiClient googleApiClient;


    @BindView(R.id.searchText)
    AutoCompleteTextView searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);



        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();

        setupAutocompleteTextView();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    private void setupAutocompleteTextView() {
        mAdapter = new PlaceAutocompleteAdapterAddress(this, googleApiClient, BOUNDS, null);
        searchText.setAdapter(mAdapter);
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputManager =
                        (InputMethodManager) ActivityTest2.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(searchText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                AutocompletePrediction item = mAdapter.getItem(position);
                getLocationFromPlaceId(item.getPlaceId(), new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        final Place place = places.get(0);
                        LatLng latLng = place.getLatLng();
                        Intent itentData = getIntent();
                        Intent intent;
                        if (itentData.hasExtra("favorite")){
                              intent = new Intent(ActivityTest2.this, FavoriteMap.class);

                        }else{
                              intent = new Intent(ActivityTest2.this, SendActivity.class);
                        }
                        intent.putExtra("search_location",latLng);
                        startActivityForResult(intent,404);
                        finish();
                    }
                });

            }
        });
    }

    private void getLocationFromPlaceId(String placeId, ResultCallback<PlaceBuffer> callback) {
        Places.GeoDataApi.getPlaceById(googleApiClient, placeId).setResultCallback(callback);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
