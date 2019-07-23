package com.rachcode.peykman.home.submenu.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rachcode.peykman.ActivityPosti;
import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.adapter.MainAdapterHome;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.submenu.TopUpActivity;
import com.rachcode.peykman.mBox.BoxActivity;
import com.rachcode.peykman.mFood.FoodActivity;
import com.rachcode.peykman.mFood.FoodMenuActivity;
import com.rachcode.peykman.mFood.FoodItemHome;
import com.rachcode.peykman.mMart.MartActivity;
 import com.rachcode.peykman.mRideCar.RideCarActivity;
import com.rachcode.peykman.mSend.SendActivity;
import com.rachcode.peykman.mService.mServiceActivity;
import com.rachcode.peykman.model.Banner;
import com.rachcode.peykman.model.Fitur;
import com.rachcode.peykman.model.PesananFood;
import com.rachcode.peykman.model.Restoran;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.user.GetBannerResponseJson;
import com.rachcode.peykman.model.json.user.GetSaldoRequestJson;
import com.rachcode.peykman.model.json.user.GetSaldoResponseJson;
import com.rachcode.peykman.model.json.user.UserDataResponseJson;
import com.rachcode.peykman.splash.SplashActivity;
import com.rachcode.peykman.utils.ConnectivityUtils;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.RecyclerTouchListener;
import com.rachcode.peykman.utils.SnackbarController;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.rachcode.peykman.utils.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.mSend.SendActivity.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by Androgo on 10/10/2018.
 */

public class HomeFragment extends Fragment  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    public ArrayList<Banner> banners = new ArrayList<>();

    @BindView(R.id.home_topUpButton)
    Button balance;
    @BindView(R.id.home_mPayBalance)
    TextView mPayBalance;
    @BindView(R.id.loading)
    ProgressBar loading_pay;

    @BindView(R.id.promo_taxi)
    LinearLayout Promo_Taxi;
    @BindView(R.id.promo_gofood)
    LinearLayout Promo_Food;
    @BindView(R.id.text_nearme)
    TextView text_nearme;
    private GoogleMap mMap;
    private boolean isMapReady = false;
    private FusedLocationProviderClient fusedLocationClient;
    private static Location lastKnowLocation;

    private GoogleApiClient googleApiClient;
    @BindView(R.id.slide_viewPager)
    AutoScrollViewPager slideViewPager;
    @BindView(R.id.slide_viewPager_indicator)
    CircleIndicator slideIndicator;
    private SnackbarController snackbarController;
    private boolean connectionAvailable;
    private boolean isDataLoaded = false;
    private Realm realm;
    private int successfulCall;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.recyclerView_main_home)
    RecyclerView recyclerView_explore;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    SupportMapFragment mapFragment;

    @BindView(R.id.nearme_recycler)
    RecyclerView nearmeRecycler;
    private RealmResults<Restoran> restoranRealmResults;
    private FastItemAdapter<FoodItemHome> restoranAdapter;

    @BindView(R.id.slider)
    Slider slider;

    /********** find view **********/
    private String[] name = {

            "غذا",
            "پست",
            "پیک",
            "شارژ",
            "بلیت",
            "فروشگاه"/*,
            General.Name_GOBOX,
            General.Name_GOSERVICE*/
    };

    private Integer[] image = {
            R.drawable.ic_food,
            R.drawable.ic_post,
            R.drawable.ic_peyk,
            R.drawable.ic_beli_bezoodi,
            R.drawable.icon_sharj_bezoodi,
            R.drawable.ic_shop
    };


    public MainAdapterHome mainAdapterHome;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SnackbarController) {
            snackbarController = (SnackbarController) context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_home, container, false);
       FragmentManager fragmentManager = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mSend_mapView);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        connectionAvailable = false;
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTopUpClick();
            }
        });
        Promo_Taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoCarClick();
            }
        });
        Promo_Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoFoodClick();
            }
        });

        realm = GoTaxiApplication.getInstance(getActivity()).getRealmInstance();
        getImageBanner();

        recyclerView_explore.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView_explore.setLayoutManager(layoutManager);
        recyclerView_explore.setNestedScrollingEnabled(false);
        recyclerView_explore.setFocusable(false);
        mainAdapterHome = new MainAdapterHome(getActivity(), name, image);
        recyclerView_explore.setAdapter(mainAdapterHome);
        recyclerView_explore.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView_explore, new MainAdapterHome.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                navigation(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        restoranAdapter = new FastItemAdapter<>();
        nearmeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        nearmeRecycler.setNestedScrollingEnabled(false);
        nearmeRecycler.setAdapter(restoranAdapter);
        restoranAdapter.withOnClickListener(new FastAdapter.OnClickListener<FoodItemHome>() {
            @Override
            public boolean onClick(View v, IAdapter<FoodItemHome> adapter, FoodItemHome item, int position) {
                Log.e("BUTTON", "CLICKED");
                Restoran selectedResto = realm.where(Restoran.class).
                        equalTo("id", restoranAdapter.getAdapterItem(position).id).findFirst();
                Intent intent = new Intent(getActivity(), FoodMenuActivity.class);
                intent.putExtra(FoodMenuActivity.ID_RESTO, selectedResto.getId());
                intent.putExtra(FoodMenuActivity.NAMA_RESTO, selectedResto.getNamaResto());
                intent.putExtra(FoodMenuActivity.ALAMAT_RESTO, selectedResto.getAlamat());
                intent.putExtra(FoodMenuActivity.DISTANCE_RESTO, selectedResto.getDistance());
                intent.putExtra(FoodMenuActivity.JAM_BUKA, selectedResto.getJamBuka());
                intent.putExtra(FoodMenuActivity.JAM_TUTUP, selectedResto.getJamTutup());
                intent.putExtra(FoodMenuActivity.IS_OPEN, selectedResto.isOpen());
                intent.putExtra(FoodMenuActivity.PICTURE_URL, selectedResto.getFotoResto());
                intent.putExtra(FoodMenuActivity.IS_MITRA, selectedResto.isPartner());
                startActivity(intent);
                return true;
            }
        });

        restoranRealmResults = realm.where(Restoran.class).findAll();
        FoodItemHome restoranItem;
        for (int i = 0; i < restoranRealmResults.size(); i++) {
            restoranItem = new FoodItemHome(getActivity());
            restoranItem.id = restoranRealmResults.get(i).getId();
            restoranItem.namaResto = restoranRealmResults.get(i).getNamaResto();
            restoranItem.alamat = restoranRealmResults.get(i).getAlamat();
            restoranItem.distance = restoranRealmResults.get(i).getDistance();
            restoranItem.jamBuka = restoranRealmResults.get(i).getJamBuka();
            restoranItem.jamTutup = restoranRealmResults.get(i).getJamTutup();
            restoranItem.fotoResto = restoranRealmResults.get(i).getFotoResto();
            restoranItem.isOpen = restoranRealmResults.get(i).isOpen();
            restoranItem.pictureUrl = restoranRealmResults.get(i).getFotoResto();
            restoranItem.isMitra = restoranRealmResults.get(i).isPartner();
            restoranAdapter.add(restoranItem);
            Log.e("RESTO", restoranItem.namaResto + "");
            Log.e("RESTO", restoranItem.alamat + "");
            Log.e("RESTO", restoranItem.jamBuka + "");
            Log.e("RESTO", restoranItem.jamTutup + "");
            Log.e("RESTO", restoranItem.fotoResto + "");
        }
        updateLastLocation(true);

    }

    private void getImageBanner() {
        UserService userService = ServiceGenerator.createService(UserService.class);
        userService.getBanner().enqueue(new Callback<GetBannerResponseJson>() {
            @Override
            public void onResponse(Call<GetBannerResponseJson> call, Response<GetBannerResponseJson> response) {
                if (response.isSuccessful()) {
                    banners = response.body().getData();
                    setBanners(banners);
                }
            }

            @Override
            public void onFailure(Call<GetBannerResponseJson> call, Throwable t) {

            }
        });

    }
    public void setBanners(ArrayList<Banner> banners){
        /** slider ***/
        List<Slide> slideList = new ArrayList<>();
        for(Banner b: banners){
            slideList.add(new Slide(b.getId(), b.getPhoto(), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        }

        //create list of slides

        /*slideList.add(new Slide(0, "http://s9.picofile.com/file/8366488434/img_banner.png", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1, "http://s9.picofile.com/file/8366488434/img_banner.png", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2, "http://s9.picofile.com/file/8366488434/img_banner.png", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3, "http://s9.picofile.com/file/8366488434/img_banner.png", getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
*/
        //handle slider click listener
        slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
            }
        });



        //add slides to slider
        slider.addSlides(slideList);


        nearmeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        nearmeRecycler.setNestedScrollingEnabled(false);
    }


    private void navigation(int position) {

        switch (position) {

            case 0:
                //Fitur selectedFitur = realm.where(Fitur.class).equalTo("idFitur", 2).findFirst();
                /*Intent intent = new Intent(getActivity(), RideCarActivity.class);
                intent.putExtra(RideCarActivity.FITUR_KEY, "2");
                getActivity().startActivity(intent);*/
                break;

            case 1:
                /*Fitur selectedFitur1 = realm.where(Fitur.class).equalTo("idFitur", 1).findFirst();
                Intent intent1 = new Intent(getActivity(), RideCarActivity.class);
                intent1.putExtra(RideCarActivity.FITUR_KEY, selectedFitur1.getIdFeature());
                getActivity().startActivity(intent1);*/

                Intent intent1 = new Intent(getActivity(), ActivityPosti.class);
                getActivity().startActivity(intent1);
                break;

            case 2:

                Fitur selectedFitur2 = realm.where(Fitur.class).equalTo("id_feature", 9).findFirst();
                Intent intent2 = new Intent(getActivity(), SendActivity.class);
                intent2.putExtra(SendActivity.FITUR_KEY, selectedFitur2.getIdFeature());
                getActivity().startActivity(intent2);
                break;

            case 3:
                /*Fitur selectedFitur3 = realm.where(Fitur.class).equalTo("idFitur", 3).findFirst();
                Intent intent3 = new Intent(getActivity(), FoodActivity.class);
                intent3.putExtra(FoodActivity.FITUR_KEY, selectedFitur3.getIdFeature());
                getActivity().startActivity(intent3);*/
                break;

            case 4:
                /*Fitur selectedFitur4 = realm.where(Fitur.class).equalTo("idFitur", 4).findFirst();
                Intent intent4 = new Intent(getActivity(), MartActivity.class);
                intent4.putExtra(MartActivity.FITUR_KEY, selectedFitur4.getIdFeature());
                getActivity().startActivity(intent4);*/

                break;

            case 5:

                break;

            case 6:
                /*Fitur selectedFiturbox = realm.where(Fitur.class).equalTo("idFitur", 7).findFirst();
                Intent intentbox = new Intent(getActivity(), BoxActivity.class);
                intentbox.putExtra(BoxActivity.FITUR_KEY, selectedFiturbox.getIdFeature());
                getActivity().startActivity(intentbox);*/
                break;

            case 7:
                /*Fitur selectedFitur8 = realm.where(Fitur.class).equalTo("idFitur", 8).findFirst();
                Intent intent8 = new Intent(getActivity(), mServiceActivity.class);
                intent8.putExtra(mServiceActivity.FITUR_KEY, selectedFitur8.getIdFeature());
                getActivity().startActivity(intent8);*/
                break;

            case 8:
                featurePro();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(PesananFood.class);
        realm.commitTransaction();
        successfulCall = 0;
        connectionAvailable = ConnectivityUtils.isConnected(getActivity());
            if (!connectionAvailable) {
                if (snackbarController != null) snackbarController.showSnackbar(
                        R.string.text_noInternet, Snackbar.LENGTH_INDEFINITE, R.string.text_close,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                return;
                            }
                        });
        } else {
            loading_pay.setVisibility(View.VISIBLE);
            mPayBalance.setVisibility(View.GONE);
        }
    }

    private void onTopUpClick() {
        Intent intent = new Intent(getActivity(), TopUpActivity.class);
        startActivity(intent);
    }

    private void onGoCarClick() {
        Fitur selectedFitur = realm.where(Fitur.class).equalTo("idFitur", 2).findFirst();
        Intent intent = new Intent(getActivity(), RideCarActivity.class);
        intent.putExtra(RideCarActivity.FITUR_KEY, selectedFitur.getIdFeature());
        getActivity().startActivity(intent);
    }


    private void onGoFoodClick() {
        Fitur selectedFitur = realm.where(Fitur.class).equalTo("idFitur", 3).findFirst();
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        intent.putExtra(FoodActivity.FITUR_KEY, selectedFitur.getIdFeature());
        getActivity().startActivity(intent);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getActivity(), R.raw.style_map));
        isMapReady = true;


        updateLastLocation(true);

    }
    private void updateLastLocation(boolean mange) {
        if (mange) {
            try {
                if(checkLocationPermission()){
                    onGetCurrentLocation(true);
                }
            } catch (Exception e) {
                android.util.Log.e("www", "error: " + e.getMessage());
            }

        } else {

        }
    }

    private void onGetCurrentLocation(final boolean move) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastKnowLocation = location;
                     if (lastKnowLocation != null) {
                        if (move) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude()), 15f));
                        }
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);

                     }
                }
            }
        });


    }

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("title_location_permission")
                        .setMessage("text_location_permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 5;
        public ArrayList<Banner> banners = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Banner> banners) {
            super(fragmentManager);
            this.banners = banners;
        }

        @Override
        public int getCount() {
            return banners.size();
        }

        @Override
        public Fragment getItem(int position) {
            return SlideFragment.newInstance(banners.get(position).getId(), banners.get(position).getPhoto());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }


    }


    private void featurePro() {
        String appPackageName = "com.gotaxi.peykman";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    }


}
