package com.rachcode.peykman;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.BookService;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.model.FavoriteAddress;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.book.AddFavoriteAddressResponseJson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.mSend.SendActivity.search_location;

public class FavoriteMap extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener{
    @BindView(R.id.favorite_marker)
    ImageView favorite_marker;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private String favorite_title;
    private boolean isMapReady = false;
    private static Location lastKnowLocation;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_map);
        ButterKnife.bind(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mSend_mapView);
        mapFragment.getMapAsync(FavoriteMap.this);
        ImageView btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isfavorite = new Intent( FavoriteMap.this, ActivityTest2.class);
                isfavorite.putExtra("favorite",1);
                startActivity(isfavorite);
            }
        });

        favorite_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LatLng centerPos = mMap.getCameraPosition().target;
                Intent data = getIntent();
                UserData userData = GoTaxiApplication.getInstance(FavoriteMap.this).getLoginUserD();
                if (data.hasExtra("favorite_title")){
                    favorite_title=data.getStringExtra("favorite_title");
                    data.removeExtra("favorite_title");
                    UserService bookService = ServiceGenerator.createService(UserService.class);
                    bookService.insert_favorite(favorite_title,userData.getId(),centerPos.latitude,centerPos.longitude).enqueue(new Callback<AddFavoriteAddressResponseJson>() {
                        @Override
                        public void onResponse(Call<AddFavoriteAddressResponseJson> call, Response<AddFavoriteAddressResponseJson> response) {
                            if (response.isSuccessful()){
                                if (response.body().getMessage().equals("success")) {
                                    search_location = new LatLng(centerPos.latitude, centerPos.longitude);

                                    finish();
                                } else {
                                    Toast.makeText(FavoriteMap.this, "متاسفانه مشکلی رخ داده است !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddFavoriteAddressResponseJson> call, Throwable t) {

                        }
                    });
                }

             }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        isMapReady=true;
        Intent intent = getIntent();
        if (intent.hasExtra("search_location")) {
            LatLng latLng = intent.getParcelableExtra("search_location");
            zoomTo(latLng, true);
        } else {
            updateLastLocation(true);
        }


    }
    private void zoomTo(LatLng latLng, boolean animate) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        if (animate) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("title_location_permission")
                        .setMessage("text_location_permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FavoriteMap.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(FavoriteMap.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void onGetCurrentLocation(final boolean move) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = getIntent();
        if (intent.hasExtra("search_location")) {
            intent.removeExtra("search_location");
            LatLng latLng = intent.getParcelableExtra("search_location");
            zoomTo(latLng, true);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lastKnowLocation = location;
                        mMap.setTrafficEnabled(true);
                        if (lastKnowLocation != null) {
                            if (move) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude()), 15f));
                            }
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
                            if (ActivityCompat.checkSelfPermission(FavoriteMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FavoriteMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);

                        }
                    }
                }
            });
        }

    }
    private void updateLastLocation(boolean mange) {
        if (mange) {
            try {
                checkLocationPermission();
                onGetCurrentLocation(true);
            } catch (Exception e) {
                android.util.Log.e("www", "error: " + e.getMessage());
            }

        } else {

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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
