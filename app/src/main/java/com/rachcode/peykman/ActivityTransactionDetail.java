package com.rachcode.peykman;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.rachcode.peykman.model.ItemHistory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTransactionDetail extends AppCompatActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.driver_name)
    TextView Driver_Name;
    @BindView(R.id.driver_image)
    de.hdodenhof.circleimageview.CircleImageView driverImage;
    @BindView(R.id.ratingBar)
    com.github.ornolfr.ratingview.RatingView ratingBar;
    @BindView(R.id.driver_police_number1)
    TextView driverPoliceNumber1;
    @BindView(R.id.driver_car)
    TextView driver_car;
    @BindView(R.id.driver_police_number2)
    TextView driverPoliceNumber2;
    @BindView(R.id.textView58)
    TextView originAddres;
    @BindView(R.id.textView57)
    TextView destriginAddres;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textView65)
    TextView request_time;
    @BindView(R.id.textView404)
    TextView price;
    @BindView(R.id.textView505)
    TextView discount;
    @BindView(R.id.textView606)
    TextView finprice;
    private boolean isMapReady = false;

    @BindView(R.id.driver_police_numberA)
    TextView driver_police_numberA;
    @BindView(R.id.driver_police_numberB)
    TextView driver_police_numberB;
    @BindView(R.id.driver_police_numberC)
    TextView driver_police_numberC;
    @BindView(R.id.driver_police_numberr)
    TextView driverPoliceNumberr;
    @BindView(R.id.plauqeMashin)
    ConstraintLayout plauqeMashin;
    @BindView(R.id.plauqeMotor)
    ConstraintLayout plauqeMotor;
    private GoogleMap gMap;
    ItemHistory itemHistory;
    private Location lastKnownLocation;
    private static final int REQUEST_PERMISSION_LOCATION = 99;
    private LatLng pickUpLatLng;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        ButterKnife.bind(this);
        itemHistory = (ItemHistory) getIntent().getSerializableExtra("itemHistory");
        Driver_Name.setText(itemHistory.driver_first_name+" "+itemHistory.driver_last_name);
        Log.i("itemHistory", "onCreate: driver_first_name "+itemHistory.driver_first_name+" driver_last_name"+itemHistory.driver_last_name);
        Glide.with(getApplicationContext()).load(itemHistory.photo).into(driverImage);
        ratingBar.setRating(Float.parseFloat(itemHistory.rating));
        driver_car.setText("نوع پیک: " + itemHistory.brand + " " + itemHistory.type + " (" + itemHistory.color + ")");
        originAddres.setText(itemHistory.origin_address);
        originAddres.setText(itemHistory.origin_address);
        destriginAddres.setText(itemHistory.destination_address);
        String[] date = itemHistory.order_start_time.split("-");
        title.setText(date[0]+" "+date[1]);
        request_time.setText(date[0]+" "+date[1]);
        price.setText(String.valueOf(itemHistory.price));
        finprice.setText(String.valueOf(itemHistory.final_price));
        discount.setText(String.valueOf(itemHistory.discount_amount));



         switch (itemHistory.order_feature) {
            case "Send-Motor":
                // set peyk motori
                plauqeMotor.setVisibility(View.VISIBLE);

                 String[] p = itemHistory.number_of_vehicle.split("-");
                driverPoliceNumber1.setText(p[0].replace("", " "));
                driverPoliceNumber2.setText(p[1].replace("", " "));



                break;

             case "Send-Car":
                 // set peyk mashini
                 plauqeMashin.setVisibility(View.VISIBLE);
                 android.util.Log.i("www", "NumberOfVehicle: "+itemHistory.number_of_vehicle);
                  String[] numberOfVehicle = itemHistory.number_of_vehicle.replace(""," ").split("-");
                 android.util.Log.i("www", "NumberOfVehicle array: 0:"+numberOfVehicle[0]+"1:"+numberOfVehicle[1]+"2:"+numberOfVehicle[2]+"3:"+numberOfVehicle[3]);


                 String a0 = numberOfVehicle[0];
                 String a1 = numberOfVehicle[1];
                 String a2 = numberOfVehicle[2];
                 String a3 = numberOfVehicle[3];
                 driver_police_numberA.setText(a0);
                 driver_police_numberB.setText(a1);
                 driver_police_numberC.setText(a2);
                 driverPoliceNumberr.setText(a3);



                /*plauqeMashin.setVisibility(View.VISIBLE);
                String[] numberOfVehicle = driver.getNumberOfVehicle().split("-");
                driverPoliceNumber.setText(numberOfVehicle[0].replace(""," "));
                driverPoliceNumberr.setText(numberOfVehicle[1].replace(""," "));
*/
                 //plauqeMashin.setVisibility(View.VISIBLE);
                 //driverPoliceNumber.setText(driver.getNumberOfVehicle());
                 break;


             case "Send-Vanet":
                 // set peyk mashini
                 android.util.Log.i("www", "NumberOfVehicle: "+itemHistory.number_of_vehicle);
                 plauqeMashin.setVisibility(View.VISIBLE);
                 String[] numberOfVehicleV = itemHistory.number_of_vehicle.replace(""," ").split("-");
                 android.util.Log.i("www", "NumberOfVehicle array: 0:"+numberOfVehicleV[0]+"1:"+numberOfVehicleV[1]+"2:"+numberOfVehicleV[2]+"3:"+numberOfVehicleV[3]);


                 String b0 = numberOfVehicleV[0];
                 String b1 = numberOfVehicleV[1];
                 String b2 = numberOfVehicleV[2];
                 String b3 = numberOfVehicleV[3];
                 driver_police_numberA.setText(b0);
                 driver_police_numberB.setText(b1);
                 driver_police_numberC.setText(b2);
                 driverPoliceNumberr.setText(b3);
                 break;
        }


        pickUpLatLng = new LatLng(itemHistory.getStartLatitude(), itemHistory.getStartLongitude());
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        isMapReady = true;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_map));
        updateLastLocation(true);


        setMarker();
    }

    private void setMarker() {
        LatLng latLngPikup = null;
        LatLng dis1 = null;
        LatLng dis2 = null;
        LatLng dis3 = null;
        LatLng dis4 = null;

        Log.i("www", "Count Marker: "+itemHistory.getdestination_count());
        switch (itemHistory.getdestination_count()) {
            case 1:
                latLngPikup = new LatLng(itemHistory.getStartLatitude(),itemHistory.getStartLongitude());
                dis1 = new LatLng(itemHistory.getEndLatitude(), itemHistory.getEndLongitude());
                break;


            case 2:
                latLngPikup = new LatLng(itemHistory.getStartLatitude(),itemHistory.getStartLongitude());
                dis1 = new LatLng(itemHistory.getEndLatitude(), itemHistory.getEndLongitude());
                dis2 = new LatLng(itemHistory.getEnd_latitude_second(), itemHistory.getEnd_longitude_second());
                break;


            case 3:
                latLngPikup = new LatLng(itemHistory.getStartLatitude(),itemHistory.getStartLongitude());
                dis1 = new LatLng(itemHistory.getEndLatitude(), itemHistory.getEndLongitude());
                dis2 = new LatLng(itemHistory.getEnd_latitude_second(), itemHistory.getEnd_longitude_second());
                dis3 = new LatLng(itemHistory.getEnd_latitude_third(), itemHistory.getEnd_longitude_third());
                break;


            case 4:
                latLngPikup = new LatLng(itemHistory.getStartLatitude(),itemHistory.getStartLongitude());
                dis1 = new LatLng(itemHistory.getEndLatitude(), itemHistory.getEndLongitude());
                dis2 = new LatLng(itemHistory.getEnd_latitude_second(), itemHistory.getEnd_longitude_second());
                dis3 = new LatLng(itemHistory.getEnd_latitude_third(), itemHistory.getEnd_longitude_third());
                dis4 = new LatLng(itemHistory.getEnd_latitude_fourth(), itemHistory.getEnd_longitude_fourth());

                break;
        }

        switch (itemHistory.getdestination_count()) {
            case 0:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 16f));
                break;
            case 1:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 16f));



                break;
            case 2:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.addMarker(createMark(2, dis2));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 16f));


                break;
            case 3:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(2, dis1));
                gMap.addMarker(createMark(2, dis2));
                gMap.addMarker(createMark(3, dis3));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 16f));


                break;
            case 4:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(3, dis1));
                gMap.addMarker(createMark(3, dis2));
                gMap.addMarker(createMark(3, dis3));
                gMap.addMarker(createMark(4, dis4));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 16f));

                break;
        }
    }
    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (pickUpLatLng != null) {
            if (move) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        pickUpLatLng, 16f)
                );

                gMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
            }
//            fetchNearDriver();
        }
    }
    private MarkerOptions createMark(int positionMarker, final LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        switch (positionMarker) {
            case 0:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_pik));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                break;

            case 1:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_dis));
                break;

            case 2:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_dis2));
                break;

            case 3:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_dis3));
                break;

            case 4:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_dis4));
                break;
        }
        markerOptions.position(latLng);


        //         Bitmap bm = ln.getDrawingCache();
        return markerOptions;
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
