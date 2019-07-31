package com.rachcode.peykman.mSend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.tasks.OnSuccessListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.rachcode.peykman.ActivityAbout;
import com.rachcode.peykman.ActivityGardeshHesab;
import com.rachcode.peykman.ActivityProfile;
import com.rachcode.peykman.Favorites;
import com.rachcode.peykman.ActivityTest2;
import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.GozinehaActivity;
import com.rachcode.peykman.R;
import com.rachcode.peykman.ServicesPerformedActivity;
import com.rachcode.peykman.api.MapDirectionAPI;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.BookService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.gmap.directions.Directions;
import com.rachcode.peykman.gmap.directions.Route;
import com.rachcode.peykman.mMart.PlaceAutocompleteAdapter;
import com.rachcode.peykman.model.AdditionalMbox;
import com.rachcode.peykman.model.Driver;
import com.rachcode.peykman.model.Fitur;
import com.rachcode.peykman.model.GetStopTime;
import com.rachcode.peykman.model.MboxInsurance;
import com.rachcode.peykman.model.Product;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.GetNearRideDriverResponseJson;
import com.rachcode.peykman.model.json.book.GetAdditionalMboxResponseJson;
import com.rachcode.peykman.model.json.book.GetNearRideCarRequestJson;
import com.rachcode.peykman.model.json.book.GetNearRideCarResponseJson;
import com.rachcode.peykman.model.json.book.GetProductResponseJson;
import com.rachcode.peykman.model.json.book.OffecrcodeequestJson;
import com.rachcode.peykman.model.json.book.RequestSendRequestJson;
import com.rachcode.peykman.model.json.book.offerCodeResponseJson;
import com.rachcode.peykman.splash.SplashActivity;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.MenuSelector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.R.id.mSend_detail;
import static com.rachcode.peykman.utils.Utility.round;

public class SendActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    public static final String FITUR_KEY = "mSendFiturKey";
    private static final String TAG = "mSendActivity";
    private FusedLocationProviderClient fusedLocationClient;

    private static final int REQUEST_PERMISSION_LOCATION = 991;
    @BindView(R.id.mSend_title)
    TextView title;
    @BindView(R.id.mSend_pickUpContainer)
    LinearLayout setPickUpContainer;
    @BindView(R.id.mSend_destinationContainer)
    LinearLayout setDestinationContainer;
    @BindView(R.id.mSend_pickUpButton)
    ImageView setPickUpButton;
    int mablaghTakhfifSabet;
    @BindView(R.id.btn_step_next)
    ConstraintLayout btn_step_next;
    String pickUpText;
    String destinationText;
    String destinationText2;
    String destinationText3;
    String destinationText4;
    @BindView(R.id.btn_step_next_des)
    ConstraintLayout btn_step_next_des;
    @BindView(R.id.design_pickup)
    ConstraintLayout pickup_datile;
    @BindView(R.id.design_request)
    ConstraintLayout request_datile;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    int byme_price;
    @BindView(R.id.top_address)
    CardView top_address;
    @BindView(R.id.mSend_destinationButton)
    ImageView setDestinationButton;
    LinearLayout btnTopAddress;
    @BindView(R.id.buttont)
    LinearLayout buttont;
    @BindView(mSend_detail)
    ConstraintLayout detail;
    @BindView(R.id.pay_detail)
    ConstraintLayout pay_detail;
    @BindView(R.id.design_des)
    ConstraintLayout des_detail;
    Boolean isValidDistance = false;
    /*   @BindView(R.id.mSend_distance)
       TextView distanceText;*/
    @BindView(R.id.mSend_price)
    TextView priceText;

    @BindView(R.id.price)
    TextView totalprice;
    @BindView(R.id.code_takhfif)
    TextView codee_takhfif;
    @BindView(R.id.price_pardakht)
    TextView price_pardakht;
    @BindView(R.id.price_wallet)
    TextView my_price;
    @BindView(R.id.rideCar_select_kamion_container)
    RelativeLayout kamionSelectContainer;
    @BindView(R.id.rideCar_select_kamion)
    ImageView kamionSelect;

    @BindView(R.id.rideCar_select_vanet_container)
    RelativeLayout vanetSelectContainer;
    @BindView(R.id.rideCar_select_vanet)
    ImageView vanetSelect;

    @BindView(R.id.rideCar_select_savari_container)
    RelativeLayout savariSelectContainer;
    @BindView(R.id.rideCar_select_savari)
    ImageView savariSelect;

    @BindView(R.id.rideCar_select_motor_container)
    RelativeLayout motorSelectContainer;
    @BindView(R.id.rideCar_select_motor)
    ImageView motorSelect;

    @BindView(R.id.select_box_vije)
    LinearLayout select_box_vije;
    @BindView(R.id.icon_box_vije)
    ImageView icon_box_vije;
    @BindView(R.id.btn_logo)
    ImageView btn_logo;
    @BindView(R.id.txt_box_vije)
    TextView txt_box_vije;

    boolean isService = false;

    @BindView(R.id.select_box_normal)
    LinearLayout select_box_normal;
    @BindView(R.id.icon_box_normal)
    ImageView icon_box_normal;
    @BindView(R.id.txt_box_normal)
    TextView txt_box_normal;

    @BindView(R.id.insurance_spinner)
    Spinner spinner;

    @BindView(R.id.main_tabLayout)
    SmartTabLayout mainTabLayout;
    public static GetStopTime getStopTime=null;
    private int StopTimeId= 0;
    public static int go_back=0;

    /**
     * pickup detile
     **/
    @BindView(R.id.Edescription)
    EditText Edescription_pik;
    @BindView(R.id.Epelak)
    EditText Epelak_pik;
    @BindView(R.id.textView18)
    TextView offerBtn;
    @BindView(R.id.Etabaghe_pik)
    EditText Etabaghe_pik;
    @BindView(R.id.Evahed)
    EditText Evahed_pik;
    @BindView(R.id.Efirst_name_pek)
    EditText Efirstname_pik;
    @BindView(R.id.Ephone)
    EditText Ephone_pik;
    HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    int ads_code = 0;
    /**
     * dis detile
     **/
    @BindView(R.id.Epelak_des)
    EditText Epelak_des;
    @BindView(R.id.Evahed_des)
    EditText Evahed_des;
    @BindView(R.id.Ename_des)
    EditText Ename_des;
    @BindView(R.id.Ephone_des)
    EditText Ephone_des;
    @BindView(R.id.Etabaghe_des)
    EditText Etabaghe_des;
    @BindView(R.id.editText)
    EditText offerText;
    int lastprice;
    /**
     * request taxi
     **/
    @BindView(R.id.constraintLayout5)
    LinearLayout btn_request;
    @BindView(R.id.btn_request)
    ConstraintLayout btn_pay_request;

    private int lastDestancePrice = 0;
    //
    @BindView(R.id.mSend_next)
    TextView mSend_next;
    private boolean nearServiceCamerachenge = false;

    private void onSelectButtonRequest() {
        btn_request.setSelected(true);
        mSend_next.setSelected(true);
    }

    private void offSelectButtonRequest() {
        btn_request.setSelected(false);
        mSend_next.setSelected(false);
    }

    private Boolean isDriverAvailable = false;
    @BindView(R.id.scroll_view_design)
    ScrollView scroll_view_design;


    @BindView(R.id.btnCarentlocation)
    ImageView btnCarentlocation;

    @BindView(R.id.btnTopAddressGozineha)
    LinearLayout btnTopAddressGozineha;

    String receiver_plaque_first = null;
    String receiver_plaque_second = null;
    String receiver_plaque_third = null;
    String receiver_plaque_fourth = null;
    String receiver_floor_first = null;
    String receiver_floor_second = null;
    String receiver_floor_third = null;
    String receiver_floor_fourth = null;
    String receiver_unit_first = null;
    String receiver_unit_second = null;
    String receiver_unit_third = null;
    String receiver_unit_fourth = null;
    String receiver_name_first = null;
    String receiver_name_second = null;
    String receiver_name_third = null;
    String receiver_name_fourth = null;
    String receiver_phone_first = null;
    String receiver_phone_second = null;
    String receiver_phone_third = null;
    String receiver_phone_fourth = null;


    int finall_price;
    long finalll_price;

    public void selectKamion() {
/*        kamionSelect.setSelected(true);
        vanetSelect.setSelected(false);
        savariSelect.setSelected(false);
        motorSelect.setSelected(false);

        updateFitur();*/
        Toast.makeText(this, "بزودی ...", Toast.LENGTH_SHORT).show();
    }

    private void setupTabLayoutViewPager() {
        GoTaxiTabProvider tabProvider = new GoTaxiTabProvider(this);
        selector = (MenuSelector) tabProvider;
        mainTabLayout.setCustomTabView(tabProvider);

        /*adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.main_menuHome, HomeFragment.class)
                .add(R.string.main_menuHistory, HistoryFragment.class)
                .add(R.string.main_menuHelp, HelpFragment.class)
                .add(R.string.main_menuSetting, SettingFragment.class)
                .create());*/


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("", Fragment.class)
                .add("", Fragment.class)
                .add("", Fragment.class)
                .create());


        viewPager = new ViewPager(getApplicationContext());
        viewPager.setAdapter(adapter);
        mainTabLayout.setViewPager(viewPager);
        //mainViewPager.setPagingEnabled(false);


        mainTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                selector.selectMenu(position);

                // remove shodow
                drawer.setScrimColor(Color.TRANSPARENT);
                drawer.setDrawerElevation(0);

                // bottom nav item click
                if (position == 0) {
                    design_wallet.setVisibility(View.VISIBLE);
                    drawer.closeDrawers();
                } else if (position == 1) {
                    design_wallet.setVisibility(View.GONE);
                    drawer.closeDrawers();
                } else if (position == 2) {
                    drawer.openDrawer(Gravity.RIGHT);
                    //design_wallet.setVisibility(View.GONE);
                }
            }
        });

    }

    public void selectVanet() {
        kamionSelect.setSelected(false);
        vanetSelect.setSelected(true);
        savariSelect.setSelected(false);
        motorSelect.setSelected(false);
        if (designedFitur.getIdFeature() == 11)
            return;
        designedFitur = realm.where(Fitur.class).equalTo("id_feature", 11).findFirst();

        updateFitur();
    }

    public void selectSavari() {
        kamionSelect.setSelected(false);
        vanetSelect.setSelected(false);
        savariSelect.setSelected(true);
        motorSelect.setSelected(false);
        if (designedFitur.getIdFeature() == 10)
            return;
        designedFitur = realm.where(Fitur.class).equalTo("id_feature", 10).findFirst();

        updateFitur();

    }

    public void selectMotor() {
        kamionSelect.setSelected(false);
        vanetSelect.setSelected(false);
        savariSelect.setSelected(false);
        motorSelect.setSelected(true);
        if (designedFitur.getIdFeature() == 9)
            return;
        designedFitur = realm.where(Fitur.class).equalTo("id_feature", 9).findFirst();
        updateFitur();
    }


//    @BindView(R.id.mSend_paymentGroup)
//    RadioGroup paymentGroup;
//    @BindView(R.id.mSend_mPayPayment)
//    RadioButton mPayButton;
//    @BindView(R.id.mSend_cashPayment)
//    RadioButton cashButton;
//    @BindView(R.id.mSend_topUp)
//    Button topUpButton;


    SupportMapFragment mapFragment;
    int fiturId;
    //    @BindView(R.id.mSend_mPayBalance)
//    TextView mPayBalanceText;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private int ride_price = 0;
    private LatLng defult = new LatLng(0.0, 0.0);

    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private LatLng destinationLatLang2 = defult;
    private LatLng destinationLatLang3 = defult;
    private LatLng destinationLatLang4 = defult;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private int DestinationNumber = 0;
    private Marker destinationMarker2;
    private Marker destinationMarker3;
    private LatLng centerPos;
    private Marker destinationMarker4;
    private ArrayList<Driver> driverAvailable;
    private List<Marker> driverMarkers;
    private Realm realm;
    private Fitur designedFitur;
    private static Location lastKnowLocation;
    private double Unit_distance;
    private double timeDistance = 0;
    private int price;
    private String Send_type = "t";
    List<MboxInsurance> mboxInsurances_clicked = new ArrayList<>();
    private long UserInventory;
    private int ProductTypeItemSelected = 0;
    private int Insurances_id_clicked = 0;
    UserData userLogin;
    LatLng CurentMarkerPostion;
    private boolean isMapReady = false;
    private PlaceAutocompleteAdapter mAdapter;

    DrawerLayout drawer;
    ConstraintLayout design_wallet;

    //    private static final LatLngBounds BOUNDS = new LatLngBounds(
//            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(okhttp3.Call call, IOException e) {

        }

        @Override
        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = response.body().string();
                final long distance = MapDirectionAPI.getDistance(SendActivity.this, json);
                final long time = MapDirectionAPI.getTimeDistance(SendActivity.this, json);
                if (distance >= 0) {
                    SendActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
/*
                            updateLineDestination(json);
*/

                            updateDistance(distance);
                            timeDistance = time / 60;
                            if (isValidDistance) {
                                pay_detail.setVisibility(View.VISIBLE);
                            } else {
                                pay_detail.setVisibility(View.GONE);

                            }

                        }
                    });
                } else {
                    pay_detail.setVisibility(View.GONE);
                }
            }
        }
    };

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        checkLocationPermission();


        // view wallet
        updatetext();

        selectorBtnWallet10Toman();


        btnWallet10Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet10Toman();
            }
        });

        btnWallet20Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet20Toman();
            }
        });

        btnWallet50Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet50Toman();
            }
        });
        button_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirst=true;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://peykman.com/utaxi/api.php/UserInventory/request?price="+edtTextPrice.getText().toString()+"&phone="+userLogin.getPhone()));
                startActivity(browserIntent);
            }
        });
        // price ++
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int price = 5000 + Integer.parseInt(edtTextPrice.getText().toString());
                edtTextPrice.setText(String.valueOf(price));
            }
        });



        // price --
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PriceX = Integer.parseInt(edtTextPrice.getText().toString());
                if (PriceX != 0 && PriceX > 5000) {
                    int price = Integer.parseInt(edtTextPrice.getText().toString()) - 5000;

                    edtTextPrice.getText().clear();
                    edtTextPrice.setText(String.valueOf(price));
                }
            }
        });

        btnTopAddressGozineha.setVisibility(View.GONE);
        btnTopAddressGozineha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SendActivity.this, GozinehaActivity.class);
                i.putExtra("DestinationNumber", DestinationNumber);
                startActivity(i);
            }
        });


        /*if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }*/
        offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookService service = ServiceGenerator.createService(BookService.class);
                OffecrcodeequestJson param = new OffecrcodeequestJson();
                param.setOrder_feature(designedFitur.getIdFeature());
                finall_price -= byme_price ;
                param.setPrice(finall_price);
                param.setCoupon_serial(offerText.getText().toString());
                service.getOfferCode(param).enqueue(new Callback<offerCodeResponseJson>() {
                    @Override
                    public void onResponse(Call<offerCodeResponseJson> call, Response<offerCodeResponseJson> response) {
                        offerCodeResponseJson responseJson = response.body();

                        if (response.isSuccessful()) {

                            if (responseJson.getMessage().equals("success")) {
                                Toast.makeText(SendActivity.this, "sdsdsdsdssddssdds", Toast.LENGTH_SHORT).show();

                                ads_code = Integer.parseInt(responseJson.getData().getAds_credit());
                                finall_price -= Integer.parseInt(responseJson.getData().getfinal_price());
                                finall_price += byme_price;

                                price_pardakht.setText(formatMony(finall_price));
                                mablaghTakhfifSabet += Integer.parseInt(responseJson.getData().getfinal_price());
                                codee_takhfif.setText(formatMony(mablaghTakhfifSabet));

                                Toast.makeText(SendActivity.this, "تخفیف با موفقیت اعمال شد", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SendActivity.this, "no", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<offerCodeResponseJson> call, Throwable t) {
                        android.util.Log.i("offerBtn", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
        title.setText("کجاهستید؟");

        pay_detail.setVisibility(View.GONE);

        setDestinationContainer.setVisibility(View.GONE);
        kamionSelectContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectKamion();
            }
        });

        savariSelectContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSavari();
            }
        });

        vanetSelectContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVanet();
            }
        });

        motorSelectContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMotor();
            }
        });

        userLogin = new UserData();
        if (GoTaxiApplication.getInstance(this).getLoginUserD() != null) {
            userLogin = GoTaxiApplication.getInstance(this).getLoginUserD();
        } else {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
        UserInventory = Long.parseLong(userLogin.getBalance());


        select_box_vije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxVije();
            }
        });

        select_box_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxNormal();
            }
        });


        //mmd
        /** corentlocation **/
        btnCarentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetCurrentLocation(true);
            }
        });


        /** set on btn request (background and text) **/
        onSelectButtonRequest();

        /** Address Montakhab **/
        btnTopAddress = findViewById(R.id.btnTopAddress);
        btnTopAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendActivity.this, Favorites.class));
            }
        });

        /** search **/
        ImageView btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendActivity.this, ActivityTest2.class));
            }
        });


        /** Set UserInformation **/
        Ephone_pik.setText(userLogin.getPhone());
        Efirstname_pik.setText(userLogin.getFirstName());


        btn_step_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide keybord
                dismissKeyboard();
                detail.setVisibility(View.VISIBLE);


                if (select_box_vije.isSelected()) {
                    Send_type = "vip";
                }
                if (select_box_normal.isSelected()) {
                    Send_type = "normal";
                }


                if (select_box_vije.isSelected()) {
                    if (Efirstname_pik.getText().toString().equals("")) {
                        Toast.makeText(SendActivity.this, "فیلد نام و نام خانوادگی خالی است.", Toast.LENGTH_SHORT).show();
                    } else if (Ephone_pik.getText().toString().equals("")) {
                        Toast.makeText(SendActivity.this, "فیلد شماره خالی است.", Toast.LENGTH_SHORT).show();
                    } else {

                        btnTopAddress.setVisibility(View.VISIBLE);
                        pickup_datile.setVisibility(View.GONE);
                        scroll_view_design.setVisibility(View.GONE);
                        top_address.setVisibility(View.VISIBLE);
                        title.setText("برای کجا سفارش میبرید؟");
                        String PhonePic = Ephone_pik.getText().toString();
                        String NamePic = Efirstname_pik.getText().toString();
                        if (NamePic.isEmpty() || NamePic.length() < 2) {
                            Toast.makeText(SendActivity.this, "نام خود را صحیح وارد کنید!", Toast.LENGTH_SHORT).show();
                        }
                        if (PhonePic.isEmpty() || PhonePic.length() != 11) {
                            Toast.makeText(SendActivity.this, "شماره خود را صحیح وارد کنید!", Toast.LENGTH_SHORT).show();
                        }

                        setDestinationContainer.setVisibility(View.VISIBLE);
                    }
                }


                if (select_box_normal.isSelected()) {
                    if (Efirstname_pik.getText().toString().equals("")) {
                        showPopupHold("فیلد نام و نام خانوادگی خالی است.");
                    } else if (Ephone_pik.getText().toString().equals("")) {
                        showPopupHold("فیلد شماره خالی است.");
                    } else {
                        pickup_datile.setVisibility(View.GONE);
                        scroll_view_design.setVisibility(View.GONE);
                        btnTopAddress.setVisibility(View.VISIBLE);
                        title.setText("برای کجا سفارش میبرید؟");
                        String PhonePic = Ephone_pik.getText().toString();
                        String NamePic = Efirstname_pik.getText().toString();
                        if (NamePic.isEmpty() || NamePic.length() < 2) {
                            showPopupHold("نام خود را صحیح وارد کنید!");
                        }
                        if (PhonePic.isEmpty() || PhonePic.length() != 11) {
                            showPopupHold("شماره خود را صحیح وارد کنید!");
                        }

                        setDestinationContainer.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btn_step_next_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide keybord
                dismissKeyboard();
                detail.setVisibility(View.VISIBLE);

                String Phonedes = Ephone_des.getText().toString();
                String Namedec = Ename_des.getText().toString();
                if (Namedec.isEmpty() || Namedec.length() < 2) {
                    showPopupHold("نام خود را صحیح وارد کنید!");
                } else if (Phonedes.isEmpty() || Phonedes.length() != 11) {
                    showPopupHold("شماره خود را صحیح وارد کنید!");
                } else {
                    btnTopAddress.setVisibility(View.VISIBLE);

                    des_detail.setVisibility(View.GONE);
                    scroll_view_design.setVisibility(View.GONE);
                    top_address.setVisibility(View.VISIBLE);
                    txt_byme.setVisibility(View.VISIBLE);
                    byme.setVisibility(View.VISIBLE);
                    title.setText("پیک من شما تقریبا آماده است");

                    switch (DestinationNumber) {
                        case 1:
                            setPickUpContainer.setVisibility(View.GONE);
                            receiver_name_first = Ename_des.getText().toString();
                            receiver_phone_first = Ephone_des.getText().toString();
                            receiver_plaque_first = Epelak_des.getText().toString();
                            receiver_unit_first = Evahed_des.getText().toString();
                            receiver_floor_first = Etabaghe_des.getText().toString();
                            clearTextViewsDes();
                            break;
                        case 2:
                            setDestinationContainer.setVisibility(View.GONE);
                            setPickUpContainer.setVisibility(View.GONE);
                            receiver_name_second = Ename_des.getText().toString();
                            receiver_phone_second = Ephone_des.getText().toString();
                            receiver_plaque_second = Epelak_des.getText().toString();
                            receiver_unit_second = Evahed_des.getText().toString();
                            receiver_floor_second = Etabaghe_des.getText().toString();
                            clearTextViewsDes();
                            break;
                        case 3:
                            setDestinationContainer.setVisibility(View.GONE);

                            receiver_name_third = Ename_des.getText().toString();
                            receiver_phone_third = Ephone_des.getText().toString();
                            receiver_plaque_third = Epelak_des.getText().toString();
                            receiver_unit_third = Evahed_des.getText().toString();
                            receiver_floor_third = Etabaghe_des.getText().toString();
                            clearTextViewsDes();
                            break;
                        case 4:
                            receiver_name_fourth = Ename_des.getText().toString();
                            receiver_phone_fourth = Ephone_des.getText().toString();
                            receiver_plaque_fourth = Epelak_des.getText().toString();
                            receiver_unit_fourth = Evahed_des.getText().toString();
                            receiver_floor_fourth = Etabaghe_des.getText().toString();
                            clearTextViewsDes();
                            break;
                    }
                }

            }
        });


        btn_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_pay_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int paymant_type = 0;
                int is_pay = 0;
                if (select_box_pish_keraye.isSelected()) {
                    paymant_type = 0;
                } else if (select_box_pas_keraye.isSelected()) {
                    paymant_type = 1;
                }


                if (select_box_online.isSelected()) {
                    is_pay = 1;

                } else if (select_box_naghdi.isSelected()) {
                    is_pay = 2;

                } else if (select_box_kartkhon.isSelected()) {
                    is_pay = 3;
                }
                int box_type = 0;
                if (select_box_vije.isSelected()) {
                    box_type = 1;
                } else if (select_box_normal.isSelected()) {
                    box_type = 0;
                }

                payRequest(paymant_type, is_pay, box_type);


            }
        });

        setPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUpClick();
            }
        });

        setDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestinationClick();
            }
        });
        buttont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DestinationNumberc(DestinationNumber);
                setDestinationContainer.setVisibility(View.VISIBLE);


                buttont.setSelected(true);
            }
        });
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mSend_mapView);
        mapFragment.getMapAsync(this);

        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }

        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        if (intent.hasExtra(FITUR_KEY)) {
            fiturId = intent.getIntExtra(FITUR_KEY, -1);

            if (fiturId != -1)
                designedFitur = realm.where(Fitur.class).equalTo("id_feature", fiturId).findFirst();
        } else {
            designedFitur = realm.where(Fitur.class).equalTo("id_feature", 9).findFirst();
        }


        setupFitur();
        updateFitur();
/*
        setupAutocompleteTextView();
*/
        setSpinner();


        /** box selector keraye **/
        select_box_pish_keraye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxPishKeraye();
                selectBoxNaghdi();
            }
        });

        select_box_pas_keraye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxNaghdi();
                selectBoxPasKeraye();
            }
        });


        /** box selector type peyment **/
        select_box_naghdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxNaghdi();
            }
        });

        select_box_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserInventory < finall_price) {
                    Toast.makeText(SendActivity.this, "موجودی شما برای پرداخت آنلاین کافی نیست !", Toast.LENGTH_SHORT).show();
                } else {
                    selectBoxOnline();

                }
            }
        });

        select_box_kartkhon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxKartkhon();
            }
        });


        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if (go_back == 1){
              price *=2;
            Toast.makeText(SendActivity.this, "دوبرابر شد", Toast.LENGTH_SHORT).show();
        }
                if (isDriverAvailable) {
                    selectBoxPishKeraye();
                    top_address.setVisibility(View.GONE);


                    design_wallet.setVisibility(View.GONE);
                    request_datile.setVisibility(View.VISIBLE);
                    scroll_view_design.setVisibility(View.VISIBLE);


                    Toast.makeText(SendActivity.this, "price: " + price, Toast.LENGTH_LONG).show();
                    finall_price = price;

                    //sum price and insurecne


                    //ConstOffer

                    int darsadTakhfifSabet = Integer.parseInt(designedFitur.getDiscount());
                    android.util.Log.i("mohasebe", "darsadTakhfifSabet: " + darsadTakhfifSabet);
                    android.util.Log.i("mohasebe", "price: " + price);


                    Float calculate_offer = (int) price * (1 - (darsadTakhfifSabet / 100.0f));
                    mablaghTakhfifSabet = Math.round(calculate_offer);
                    android.util.Log.i("mohasebe", "mablaghTakhfifSabet: " + mablaghTakhfifSabet);
                    mablaghTakhfifSabet = Math.round(mablaghTakhfifSabet);
                    if (UserInventory < finall_price) {
                        Toast.makeText(SendActivity.this, "موجودی شما برای پرداخت آنلاین کافی نیست !", Toast.LENGTH_SHORT).show();
                        selectBoxNaghdi();
                    } else {
                        selectBoxOnline();
                    }
                    byme_price = (int) mboxInsurances_clicked.get(Insurances_id_clicked).premium;

                    finall_price = (int) (price - mablaghTakhfifSabet);
                    finall_price += byme_price;
                    totalprice.setText(formatMony(price));

                    codee_takhfif.setText(formatMony(mablaghTakhfifSabet));

                    price_pardakht.setText(formatMony(finall_price));

                    byme.setText(formatMony(byme_price));
                } else {
                    Toast.makeText(SendActivity.this, "سرویس خود را تغییر دهید", Toast.LENGTH_SHORT).show();
                }

            }
        });


        bottomNav();
        setupTabLayoutViewPager();
        setNavigitionView();

    }

    private MenuSelector selector;
    private ViewPager viewPager;
    private void setNavigitionView() {
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btn_logo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                drawer.closeDrawers();
            }
        });
        //setSupportActionBar(toolbar);


        /*ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.s1,R.string.s2);
        drawerToggle.syncState();*/
        //drwable.setDrawerListener(drawerToggle);


        // set button nav
        ConstraintLayout about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                startActivity(new Intent(getApplication(), ActivityAbout.class));
                //finish();
            }
        });


        ConstraintLayout profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                startActivity(new Intent(getApplication(), ActivityProfile.class));
            }
        });


        // set button nav
        ConstraintLayout gardeshHsab = findViewById(R.id.gardesh_hesab);
        gardeshHsab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                startActivity(new Intent(getApplication(), ActivityGardeshHesab.class));
            }
        });

        ConstraintLayout servicesPerformed = findViewById(R.id.khedemat_anjam_shode);
        servicesPerformed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                startActivity(new Intent(getApplication(), ServicesPerformedActivity.class));
            }
        });

        LinearLayout wallet_increase = findViewById(R.id.wallet_increase);
        wallet_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                design_wallet.setVisibility(View.VISIBLE);
                selector.selectMenu(0);
                viewPager.setCurrentItem(0);
                drawer.closeDrawers();

            }
        });
    }


    private void bottomNav() {
        drawer = findViewById(R.id.drawer);
        design_wallet = findViewById(R.id.design_wallet);
    }

    private void showPopupHold(String message) {
        final AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
        popupBuilder.setCancelable(false);
        View viewPopup = getLayoutInflater().inflate(R.layout.popup, null);

        TextView title = viewPopup.findViewById(R.id.textView48);
        title.setText(message);

        popupBuilder.setView(viewPopup);


        final AlertDialog popup = popupBuilder.create();
        popup.show();
        TextView ok = viewPopup.findViewById(R.id.txt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });


    }

    private void payRequest(int paymant_type, int is_pay, int box_type) {

        RequestSendRequestJson param = new RequestSendRequestJson();
        param.customer_id = userLogin.getId();
        param.ads_code = ads_code;
        param.order_feature = designedFitur.getIdFeature();
        param.start_latitude = pickUpLatLang.latitude;
        param.destination_count = DestinationNumber;
        param.start_longitude = pickUpLatLang.longitude;
        param.end_latitude = destinationLatLang.latitude;
        param.end_longitude = destinationLatLang.longitude;
        param.end_longitude_third = destinationLatLang3.longitude;
        param.end_latitude_second = destinationLatLang2.latitude;
        param.end_longitude_second = destinationLatLang2.longitude;
        param.end_latitude_third = destinationLatLang3.latitude;
        param.end_latitude_fourth = destinationLatLang4.latitude;
        param.customer_picture = userLogin.getProfilepicture();
        param.end_longitude_fourth = destinationLatLang4.longitude;
        param.mablaghTakhfifSabet = mablaghTakhfifSabet;
        param.ads_credit = designedFitur.getDiscount_id();

        param.go_back = go_back;
        param.final_price = finall_price;
        param.distance = Unit_distance;
/*
        param.price_takhfifed = price_takhfifed;
*/
        param.user_inventory = userLogin.getBalance();
/*
        param.price_takhfifed = price_takhfifed;
*/
         param.price = String.valueOf(price);
        param.byme_price = byme_price;
        param.totalPrice = String.valueOf(price);
        param.origin_address = pickUpText;
        param.destination_address = destinationText;
        param.destination_address_second = destinationText2;
        param.destination_address_third = destinationText3;
        param.destination_address_fourth = destinationText4;
        param.name_of_the_sender = Efirstname_pik.getText().toString();
        param.senders_phone = Ephone_pik.getText().toString();
        param.receiver_name = receiver_name_first;
        param.receiver_name_second = receiver_name_second;
        param.receiver_name_third = receiver_name_third;
        param.box_type = box_type;
        param.receiver_name_fourth = receiver_name_fourth;
        param.receiver_phone = receiver_phone_first;
        param.receiver_phone_second = receiver_phone_second;
        param.receiver_phone_third = receiver_phone_third;
        param.receiver_phone_fourth = receiver_phone_fourth;
        param.item_name = Edescription_pik.getText().toString();
        param.sender_plaque = Epelak_pik.getText().toString();
        param.sender_floor = Etabaghe_pik.getText().toString();
        param.insurance_id = mboxInsurances_clicked.get(Insurances_id_clicked).id;
        param.delay = StopTimeId;
        param.getStopTime = getStopTime;
        param.product_id = ProductTypeItemSelected;
        param.receiver_plaque = receiver_plaque_first;
        param.receiver_plaque_second = receiver_plaque_second;
        param.receiver_plaque_third = receiver_plaque_third;
        param.receiver_plaque_fourth = receiver_plaque_fourth;
        param.sender_unit = Evahed_pik.getText().toString();
        param.receiver_unit = receiver_unit_first;
        param.receiver_unit_second = receiver_unit_second;
        param.receiver_unit_third = receiver_unit_third;
        param.receiver_unit_fourth = receiver_unit_fourth;
        param.receiver_floor = receiver_floor_first;
        param.receiver_floor_second = receiver_floor_second;
        param.receiver_floor_third = receiver_floor_third;
        param.receiver_floor_fourth = receiver_floor_fourth;
        param.pay_type = paymant_type;


        param.is_pay = is_pay;

        Intent intentt = new Intent(SendActivity.this, SendWaitingActivity.class);
        intentt.putExtra(SendWaitingActivity.REQUEST_PARAM, param);
        intentt.putExtra(SendWaitingActivity.DRIVER_LIST, (ArrayList) driverAvailable);
        intentt.putExtra("time_distance", timeDistance);
        startActivity(intentt);

    }

    private void clearTextViewsDes() {
        Ename_des.getText().clear();
        Ephone_des.getText().clear();
        Epelak_des.getText().clear();
        Evahed_des.getText().clear();
        Etabaghe_des.getText().clear();
    }


    ArrayAdapter<String> insuranceAdapter;
    ArrayAdapter<String> productTypeLstAdapter;
    List<MboxInsurance> mboxInsurances;
    List<String> insuranceList;
    List<String> productTypeLst;

    private void DestinationNumberc(int DestinationNumber) {
        switch (DestinationNumber) {
            case 0:
                setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis));
                zoomTo(destinationLatLang, true);
                setDestinationContainer.setVisibility(View.VISIBLE);

                break;
            case 1:
                setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis2));
                zoomTo(destinationLatLang, true);
                setDestinationContainer.setVisibility(View.VISIBLE);

                break;
            case 2:
                setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis3));
                setDestinationContainer.setVisibility(View.VISIBLE);

                zoomTo(destinationLatLang2, true);

                break;
            case 3:
                setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis4));
                setDestinationContainer.setVisibility(View.VISIBLE);

                zoomTo(destinationLatLang3, true);

                break;

        }

    }

    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.insurance_spinner);
        final Spinner product_spinner = (Spinner) findViewById(R.id.product_Type_spinner);

        getAdditionalData();
        getProductData();
        insuranceList = new ArrayList<String>();
//        insuranceList.add("Rp 0, Insurance up to Rp 0");

        insuranceAdapter = new ArrayAdapter<String>(this, R.layout.item_selected_spinner, insuranceList);
        insuranceAdapter.setDropDownViewResource(R.layout.item_selected_spinner);
        spinner.setAdapter(insuranceAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Insurances_id_clicked = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        productTypeLst = new ArrayList<String>();
//        insuranceList.add("Rp 0, Insurance up to Rp 0");

        productTypeLstAdapter = new ArrayAdapter<String>(this, R.layout.item_selected_spinner, productTypeLst);
        productTypeLstAdapter.setDropDownViewResource(R.layout.item_selected_spinner);
        product_spinner.setAdapter(productTypeLstAdapter);
        product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String title = product_spinner.getSelectedItem().toString();
                ProductTypeItemSelected = hashMap.get(title);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getProductData() {
        UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.getProductType().enqueue(new Callback<GetProductResponseJson>() {
            @Override
            public void onResponse(Call<GetProductResponseJson> call, Response<GetProductResponseJson> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> data = response.body().data;


                    for (Product ins : data) {
                        productTypeLst.add(ins.product_type);
                        hashMap.put(ins.product_type, ins.id);
                    }
                    productTypeLstAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetProductResponseJson> call, Throwable t) {

            }
        });
    }

    private void getAdditionalData() {
        UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.getAdditionalMbox().enqueue(new Callback<GetAdditionalMboxResponseJson>() {
            @Override
            public void onResponse(Call<GetAdditionalMboxResponseJson> call, Response<GetAdditionalMboxResponseJson> response) {
                if (response.isSuccessful()) {

                    AdditionalMbox data = response.body().data;
                    mboxInsurances = data.insurance;

                    for (MboxInsurance ins : mboxInsurances) {
                        mboxInsurances_clicked.add(ins);
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formattedString = formatter.format(ins.estimated_costs);
                        formattedString = formattedString.replaceAll("[تومان,]", ".");

                        insuranceList.add(General.MONEY + " " + ins.premium + ", مبلغ بیمه " + General.MONEY + formattedString + " ");
                    }
                    insuranceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetAdditionalMboxResponseJson> call, Throwable t) {

            }
        });
    }


    private void setupFitur() {
        if (designedFitur.getIdFeature() == 9) {
            selectMotor();
        } /*else if (fitur.getIdFeature() == 2) {
            title.setText(R.string.home_mCar);
            logo.setImageResource(R.drawable.car);
            selectCar();
        }*/
    }

    public void selectBoxVije() {
        select_box_vije.setSelected(true);
        txt_box_vije.setSelected(true);
        icon_box_vije.setSelected(true);

        select_box_normal.setSelected(false);
        txt_box_normal.setSelected(false);
        icon_box_normal.setSelected(false);
        Edescription_pik.setVisibility(View.VISIBLE);
        c1.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }

    public void selectBoxNormal() {
        select_box_vije.setSelected(false);
        txt_box_vije.setSelected(false);
        icon_box_vije.setSelected(false);
        select_box_normal.setSelected(true);
        txt_box_normal.setSelected(true);
        icon_box_normal.setSelected(true);
        Edescription_pik.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        c1.setVisibility(View.GONE);
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
                                ActivityCompat.requestPermissions(SendActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(SendActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void dismissKeyboard() {
        Activity activity = SendActivity.this;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }


    private void getLocationFromPlaceId(String placeId, ResultCallback<PlaceBuffer> callback) {
        Places.GeoDataApi.getPlaceById(googleApiClient, placeId).setResultCallback(callback);
    }


    private LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            return p1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static LatLng search_location = null;

    //public static LatLng favorites_location = null;
    @Override
    protected void onResume() {
        super.onResume();

        updatetext();

   if (getStopTime != null){
       StopTimeId=Integer.parseInt(getStopTime.getTimeId());
        price +=  Integer.parseInt(getStopTime.getTimeCost());
   }else{
       StopTimeId=0;

   }
        /////////////////////////
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        android.util.Log.i(TAG, "search_location_111: " + search_location);
        if (search_location != null) {
            LatLng latLng = search_location;
            zoomTo(latLng, true);
        }


        UserData userLogin = GoTaxiApplication.getInstance(this).getLoginUserD();
        android.util.Log.i(TAG, "onResume: " + userLogin.getBalance());
        UserInventory = Long.parseLong(userLogin.getBalance());

        my_price.setText(formatMony(UserInventory));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation(true);
            } else {

            }
        }
    }

    private void cancelOrder() {

    }

    public String formatMony(Long price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }

    public String formatMony(int price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }


    private void updateFitur() {
        driverAvailable.clear();
        for (Marker m : driverMarkers) {
            m.remove();
        }
        driverMarkers.clear();

        if (isMapReady) updateLastLocation(false);
        fetchNearDriver();
        int DestinationNumber_for = DestinationNumber;
        priceText.setText(String.valueOf(0));
        price = 0;
        lastprice = 0;
        for (int i = 1; i <= DestinationNumber_for; i++) {

            DestinationNumber = i;
            requestRoute();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation(true);
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


        isMapReady = true;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_map));

        if (search_location != null) {
            LatLng latLng = search_location;
            zoomTo(latLng, true);
        } else {

            updateLastLocation(true);
        }

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (pickUpLatLang == null) {
                    nearServiceCamerachenge = true;
                    CurentMarkerPostion = mMap.getCameraPosition().target;
                    fetchNearDriver();
                }

            }
        });

    }

    private void getCurentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        updateLastLocation(true);

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


    private void onGetCurrentLocation(final boolean move) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (search_location != null) {
            LatLng latLng = search_location;
            zoomTo(latLng, true);
            search_location = null;
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
                            if (ActivityCompat.checkSelfPermission(SendActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SendActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            fetchNearDriver();
                        }
                    }
                }
            });
        }

    }

    private void zoomTo(LatLng latLng, boolean animate) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        if (animate) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        }
    }

    private void fetchNearDriver(double latitude, double longitude, int IsSelected) {
        if (lastKnowLocation != null) {
            UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();

            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(latitude);
            param.setLongitude(longitude);

            if (designedFitur.getIdFeature() == 9) {
                service.getNearsendMotor(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {
                            driverAvailable = response.body().getData();
                            createMarker();

                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            } else if (designedFitur.getIdFeature() == 11) {
                service.getNearsendvanet(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {

                            android.util.Log.i("resid", "resid: ");
                            driverAvailable = response.body().getData();
                            createMarker();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            } else if (designedFitur.getIdFeature() == 10) {
                service.getNearsendcar(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {
                            driverAvailable = response.body().getData();
                            createMarker();
                        }
                    }


                    @Override
                    public void onFailure(Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            }
            if (IsSelected == 1) {
                picup(pickUpLatLang, designedFitur.getIdFeature());

            }

        }
    }

    private void fetchNearDriver() {
        if (lastKnowLocation != null) {
            UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();

            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();


            if (nearServiceCamerachenge) {
                nearServiceCamerachenge = false;
                param.setLatitude(CurentMarkerPostion.latitude);
                param.setLongitude(CurentMarkerPostion.longitude);
            } else {
                param.setLatitude(lastKnowLocation.getLatitude());
                param.setLongitude(lastKnowLocation.getLongitude());
            }

            if (designedFitur.getIdFeature() == 9) {
                service.getNearsendMotor(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {
                            driverAvailable = response.body().getData();
                            createMarker();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            } else if (designedFitur.getIdFeature() == 10) {
                service.getNearsendcar(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {
                            driverAvailable = response.body().getData();
                            createMarker();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            } else if (designedFitur.getIdFeature() == 11) {
                service.getNearsendvanet(param).enqueue(new Callback<GetNearRideDriverResponseJson>() {
                    @Override
                    public void onResponse(Call<GetNearRideDriverResponseJson> call, Response<GetNearRideDriverResponseJson> response) {
                        if (response.isSuccessful()) {
                            driverAvailable = response.body().getData();
                            createMarker();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNearRideDriverResponseJson> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void createMarker() {
        if (!driverAvailable.isEmpty()) {
            isDriverAvailable = true;
            try {
                for (Marker marker : driverMarkers) {
                    marker.remove();
                }

                driverMarkers.clear();

                for (Driver driver : driverAvailable) {
                    LatLng latLng = new LatLng(Double.parseDouble(driver.getLatitude()), Double.parseDouble(driver.getLongitude()));


                    if (designedFitur.getIdFeature() == 9) {
                        driverMarkers.add(
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ride_position))));

                    } else if (designedFitur.getIdFeature() == 10) {
                        driverMarkers.add(
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position))));

                    } else if (designedFitur.getIdFeature() == 11) {
                        driverMarkers.add(
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vanet_position))));

                    }


                }

            } catch (Exception e) {
                Log.e("www", "createMarker: " + e.getMessage());
            }
        } else {

            isDriverAvailable = false;
            Toast.makeText(this, "راننده ای یافت نشد !", Toast.LENGTH_SHORT).show();
        }
    }


    private void onDestinationClick() {
        centerPos = mMap.getCameraPosition().target;
        switch (DestinationNumber) {
            case 0:
                destinationLatLang = centerPos;
                break;
            case 1:
                destinationLatLang2 = centerPos;
                break;
            case 2:
                destinationLatLang3 = centerPos;
                break;
            case 3:
                destinationLatLang4 = centerPos;
                break;
        }
        DestinationNumber++;
        requestRoute();


    }


    private void onPickUpClick() {
        if (isDriverAvailable) detail.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        if (directionLine != null)
            destinationLatLang = null;
        LatLng centerPos = mMap.getCameraPosition().target;
        pickUpLatLang = centerPos;


        selectBoxNormal();
        fetchNearDriver(pickUpLatLang.latitude, pickUpLatLang.longitude, 1);


        fillAddress(1, pickUpLatLang);


        requestRoute();
    }

    private void picup(LatLng postion, int designd) {

        if (!driverAvailable.isEmpty()) {
            top_address.setVisibility(View.GONE);
            btnTopAddress.setVisibility(View.GONE);
            pickup_datile.setVisibility(View.VISIBLE);
            scroll_view_design.setVisibility(View.VISIBLE);
            pickUpMarker = mMap.addMarker(new MarkerOptions()
                    .position(postion)
                    .title("مبدا")
                    .icon(bitmapDescriptorFromVector(SendActivity.this, R.drawable.ic_pin)));
            setDestinationContainer.setVisibility(View.VISIBLE);
        } else {
            AlertDialog dialog = new AlertDialog.Builder(SendActivity.this)
                    .setMessage("در نزدیکی شما هیچ راننده ای یافت نشد !")
                    .setPositiveButton("اوکی", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
            dialog.show();
        }
    }

    private void requestRoute() {
        switch (DestinationNumber) {
            case 1:
                if (pickUpLatLang != null && destinationLatLang != null) {
                    MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
                    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(destinationLatLang, 13);
                    mMap.animateCamera(camera);
                }
                break;
            case 2:
                if (destinationLatLang != null && destinationLatLang2 != null) {

                    MapDirectionAPI.getDirection(destinationLatLang, destinationLatLang2).enqueue(updateRouteCallback);
                    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(destinationLatLang2, 13);
                    mMap.animateCamera(camera);
                }
                break;

            case 3:
                if (destinationLatLang2 != null && destinationLatLang3 != null) {
                    android.util.Log.i("qwax", "cas3");

                    MapDirectionAPI.getDirection(destinationLatLang2, destinationLatLang3).enqueue(updateRouteCallback);
                    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(destinationLatLang3, 13);
                    mMap.animateCamera(camera);
                }
                break;
            case 4:
                if (destinationLatLang3 != null && destinationLatLang4 != null) {
                    android.util.Log.i("qwax", "cas4");

                    MapDirectionAPI.getDirection(destinationLatLang3, destinationLatLang4).enqueue(updateRouteCallback);
                    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(destinationLatLang4, 13);
                    mMap.animateCamera(camera);
                }
        }

    }

    private void makeDesTinationMarker(int DestinationNumber) {
        android.util.Log.i("DestinationNumber", "makeDesTinationMarker: " + DestinationNumber);
        setPickUpContainer.setVisibility(View.GONE);
        btnTopAddress.setVisibility(View.GONE);
        switch (DestinationNumber) {
            case 0:
                detail.setVisibility(View.GONE);
                if (destinationMarker != null) destinationMarker.remove();
                destinationMarker = mMap.addMarker(new MarkerOptions()
                        .position(centerPos)
                        .title("مقصد")
                        .icon(bitmapDescriptorFromVector(SendActivity.this, R.drawable.ic_dist)));
                destinationLatLang = centerPos;
                buttont.setVisibility(View.VISIBLE);
                setDestinationContainer.setVisibility(View.GONE);
                btnTopAddressGozineha.setVisibility(View.VISIBLE);
                fillAddress(2, destinationLatLang);
                break;
            case 1:
                detail.setVisibility(View.GONE);
                if (destinationMarker2 != null) destinationMarker2.remove();
                destinationMarker2 = mMap.addMarker(new MarkerOptions()
                        .position(centerPos)
                        .title("مقصد2")
                        .icon(bitmapDescriptorFromVector(SendActivity.this, R.drawable.ic_dist)));
                destinationLatLang2 = centerPos;
                fillAddress(3, destinationLatLang2);
                break;
            case 2:
                detail.setVisibility(View.GONE);
                if (destinationMarker3 != null) destinationMarker3.remove();
                destinationMarker3 = mMap.addMarker(new MarkerOptions()
                        .position(centerPos)
                        .title("مقصد3")
                        .icon(bitmapDescriptorFromVector(SendActivity.this, R.drawable.ic_dist)));
                destinationLatLang3 = centerPos;
                fillAddress(4, destinationLatLang3);

                break;
            case 3:
                detail.setVisibility(View.GONE);
                if (destinationMarker4 != null) destinationMarker4.remove();
                destinationMarker4 = mMap.addMarker(new MarkerOptions()
                        .position(centerPos)
                        .title("مقصد4")
                        .icon(bitmapDescriptorFromVector(SendActivity.this, R.drawable.ic_dist)));
                destinationLatLang4 = centerPos;
                fillAddress(5, destinationLatLang4);

                break;
        }

        top_address.setVisibility(View.GONE);
        des_detail.setVisibility(View.VISIBLE);
        scroll_view_design.setVisibility(View.VISIBLE);
    }

    private void fillAddress(final int i, final LatLng latLng) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latLng.latitude + "," + latLng.longitude + "&key=AIzaSyDzGThdrg8DlBY1DBcexuBU28TVqh1UKzU&language=fa";
                FormBody.Builder formbudybuilder = new FormBody.Builder();

                FormBody fmBuddy = formbudybuilder.build();

                Request request = new Request.Builder()
                        .url(url).method("POST", fmBuddy)
                        .build();

                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        Log.e("www", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    String data = response.body().string();
                                    JSONObject jsonObject = new JSONObject(data);
                                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    String address = jsonObject1.getString("formatted_address");

                                    switch (i) {
                                        case 1:
                                            pickUpText = address;
                                            android.util.Log.i("zzz", "pickUpText: " + address);
                                            break;
                                        case 2:
                                            destinationText = address;
                                            android.util.Log.i("zzz", "destinationText: " + address);
                                            break;
                                        case 3:
                                            destinationText2 = address;
                                            android.util.Log.i("zzz", "destinationText2: " + address);
                                            break;
                                        case 4:
                                            destinationText3 = address;
                                            android.util.Log.i("zzz", "destinationText3: " + address);
                                            break;
                                        case 5:
                                            destinationText4 = address;
                                            android.util.Log.i("zzz", "destinationText4: " + address);
                                            break;

                                    }


                                } catch (Exception e) {
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

/*    private void updateLineDestination(String json) {
        Directions directions = new Directions(SendActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) 
            if (routes.size() > 0) {
                directionLine = mMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(SendActivity.this, R.color.black))
                        .width(7));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    protected int calculate_smotor(Float km) {

        if (km <= 4) {
            return 2500;
        } else if (km > 4 && km <= 5) {
            return 3000;
        } else if (km > 5 && km <= 6) {
            return 3500;
        } else if (km > 6 && km <= 7) {
            return 4000;
        } else if (km > 7 && km <= 11) {
            return 5500;
        } else if (km > 11 && km <= 18) {
            return 6000;
        } else {
            return 0;
        }

    }

    protected int calculate_svanet(Float km) {

        if (km <= 6) {
            return 14000;
        } else if (km > 6 && km <= 7) {
            return 24000;
        } else if (km > 7 && km <= 11) {
            return 26000;
       /* } else if (km > 6 && km <= 7) {
            return 4000;
        } else if (km > 7 && km <= 11) {
            return 5500;
        } else if (km > 11 && km <= 18) {
            return 6000;*/
        } else {
            return 0;
        }

    }

    protected int calculate_scar(Float km) {

        if (km <= 4) {
            return 3000;
        } else if (km > 4 && km <= 5) {
            return 3500;
        } else if (km > 5 && km <= 6) {
            return 4000;
        } else if (km > 6 && km <= 7) {
            return 4500;
        } else if (km > 7 && km <= 11) {
            return 6000;
        } else if (km > 11 && km <= 18) {
            return 6500;
        } else {
            return 0;
        }

    }


    private void updateDistance(long distance) {

        detail.setVisibility(View.VISIBLE);
        float km = ((float) distance) / General.RANGE_VALUE;

        this.Unit_distance = km;
/*
        String format = String.format(Locale.US, "Distance %.2f " + General.UNIT_OF_DISTANCE, km);

        distanceText.setText(format);
*/

        km = (float) round(km, 1);

        Toast.makeText(this, "km:" + km, Toast.LENGTH_SHORT).show();
        int price_fiture = 0;
        switch (designedFitur.getIdFeature()) {
            case 9:
                price_fiture = calculate_smotor(km);
                break;

            case 10:
                price_fiture = calculate_scar(km);
                break;
            case 11:
                price_fiture = calculate_svanet(km);

                break;
        }
        int setDestinationNumber = DestinationNumber - 1;
        if (price_fiture != 0) {
            isValidDistance = true;
            makeDesTinationMarker(setDestinationNumber);
            lastDestancePrice = price_fiture;
            lastprice += price_fiture;
            price = lastprice;
            priceText.setText(formatMony(lastprice));
        } else {
            isValidDistance = false;
            pay_detail.setVisibility(View.GONE);
            DestinationNumber--;
            Toast.makeText(this, "خطا در محاسبه!", Toast.LENGTH_SHORT).show();
        }


      /*  long totalPrice = (long) (designedFitur.getPrice() * Math.ceil(km));

        if (totalPrice % 1 != 0)
            totalPrice = (1 - (totalPrice % 1)) + totalPrice;

        this.price = totalPrice;
        if (totalPrice < designedFitur.getMinimumPrice()) {
            this.price = designedFitur.getMinimumPrice();
            totalPrice = designedFitur.getMinimumPrice();
        }

//        if(mPayButton.isChecked()){
//            biayaTotal /= 2;
//        }

        String formattedTotal = String.valueOf(totalPrice);
        String formattedText = formattedTotal+" "+General.MONEY;
        priceText.setText(formattedText);
        Toast.makeText(this, ""+formattedText, Toast.LENGTH_SHORT).show();*/
//        if(saldoMpay < (harga/2)){
//            mPayButton.setEnabled(false);
//            cashButton.toggle();
//        }else {
//            mPayButton.setEnabled(true);
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
            return;
        }
        if (des_detail.getVisibility() == View.VISIBLE) {
            des_detail.setVisibility(View.GONE);
        }

        if (request_datile.getVisibility() == View.VISIBLE) {
            request_datile.setVisibility(View.GONE);
        }
        switch (DestinationNumber) {
            case 0:
                if (setPickUpContainer.getVisibility() == View.VISIBLE) {
                    if (pickup_datile.getVisibility() == View.VISIBLE) {
                        pickup_datile.setVisibility(View.GONE);
                        if (pickUpMarker != null) {
                            pickUpMarker.remove();
                        }
                        setDestinationContainer.setVisibility(View.GONE);
                        setPickUpContainer.setVisibility(View.VISIBLE);

                        return;
                    }
                    super.onBackPressed();
                    finish();
                } else {

                    if (pickUpMarker != null) pickUpMarker.remove();
                    pay_detail.setVisibility(View.GONE);
                    setDestinationContainer.setVisibility(View.GONE);
                    setPickUpContainer.setVisibility(View.VISIBLE);
                    if (directionLine != null)
                        getCurentLocation();

                }


                android.util.Log.i("back", "onBackPressed:  case : 1 DestinationNumber:" + DestinationNumber);
                break;
            case 1:


                if (destinationMarker != null) {
                    DestinationNumberc(0);
                    decraseLastPrice();
                    destinationMarker.remove();

                    DestinationNumber--;
                    clearTextViewsDes();
                    setDestinationContainer.setVisibility(View.VISIBLE);
                    zoomTo(destinationLatLang, true);

                    android.util.Log.i("back", "onBackPressed:  case : 2 DestinationNumber:" + DestinationNumber);
                    break;

                } else {
                    android.util.Log.i("back", "onBackPressed:  else case : 2 DestinationNumber:" + DestinationNumber);
                    super.onBackPressed();
                }
            case 2:
                if (destinationMarker2 != null) {
                    DestinationNumberc(1);
                    decraseLastPrice();

                    destinationMarker2.remove();

                    DestinationNumber--;
                    clearTextViewsDes();
                    setDestinationContainer.setVisibility(View.VISIBLE);
                    android.util.Log.i("back", "onBackPressed:  case : 3 DestinationNumber:" + DestinationNumber);
                    break;

                } else {
                    android.util.Log.i("back", "onBackPressed:  else case : 3 DestinationNumber:" + DestinationNumber);

                    DestinationNumber--;
                }
                break;
            case 3:
                if (destinationMarker3 != null) {
                    DestinationNumberc(2);
                    decraseLastPrice();

                    destinationMarker3.remove();

                    DestinationNumber--;
                    clearTextViewsDes();
                    setDestinationContainer.setVisibility(View.VISIBLE);
                    android.util.Log.i("back", "onBackPressed:  case : 4 DestinationNumber:" + DestinationNumber);
                    break;

                } else {
                    android.util.Log.i("back", "onBackPressed:  else case : 4 DestinationNumber:" + DestinationNumber);

                    setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis2));
                    DestinationNumber--;
                }
                break;

            case 4:
                if (destinationMarker4 != null) {
                    DestinationNumberc(3);
                    decraseLastPrice();
                    destinationMarker4.remove();

                    DestinationNumber--;
                    clearTextViewsDes();
                    setDestinationContainer.setVisibility(View.VISIBLE);
                    android.util.Log.i("back", "onBackPressed:  case : 4 DestinationNumber:" + DestinationNumber);
                    break;

                } else {
                    android.util.Log.i("back", "onBackPressed:  else case : 4 DestinationNumber:" + DestinationNumber);

                    setDestinationButton.setImageDrawable(getResources().getDrawable(R.drawable.icm_dis2));
                    DestinationNumber--;
                }
                break;
        }


    }

    public void decraseLastPrice() {
        lastprice -= lastDestancePrice;
        price = lastprice;
        priceText.setText(formatMony(lastprice));
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

    /* @Override
    public void onBackPressed(){
        Intent home = new Intent(SendActivity.this, MainActivity.class);
        startActivity(home);

    } */


    /**
     * box keraye
     **/
    @BindView(R.id.select_box_pish_keraye)
    LinearLayout select_box_pish_keraye;
    @BindView(R.id.txt_box_pish_keraye)
    TextView txt_box_pish_keraye;

    @BindView(R.id.select_box_pas_keraye)
    LinearLayout select_box_pas_keraye;
    @BindView(R.id.txt_box_pas_keraye)
    TextView txt_box_pas_keraye;


    /**
     * box type peyment
     **/
    @BindView(R.id.select_box_naghdi)
    LinearLayout select_box_naghdi;
    @BindView(R.id.icon_box_naghdi)
    ImageView icon_box_naghdi;
    @BindView(R.id.txt_box_naghdi)
    TextView txt_box_naghdi;

    @BindView(R.id.select_box_online)
    LinearLayout select_box_online;
    @BindView(R.id.icon_box_online)
    ImageView icon_box_online;
    @BindView(R.id.txt_box_online)
    TextView txt_box_online;

    @BindView(R.id.select_box_kartkhon)
    LinearLayout select_box_kartkhon;
    @BindView(R.id.icon_box_kartkhon)
    ImageView icon_box_kartkhon;
    @BindView(R.id.txt_box_kartkhon)
    TextView txt_box_kartkhon;


    /**
     * box byme
     **/


    @BindView(R.id.byme)
    TextView byme;
    @BindView(R.id.textView2)
    TextView txt_byme;


    /**
     * box method keraye
     **/
    public void selectBoxPishKeraye() {
        select_box_pish_keraye.setSelected(true);
        txt_box_pish_keraye.setSelected(true);
        select_box_pas_keraye.setSelected(false);
        txt_box_pas_keraye.setSelected(false);
        select_box_online.setVisibility(View.VISIBLE);
    }

    public void selectBoxPasKeraye() {
        select_box_pish_keraye.setSelected(false);
        txt_box_pish_keraye.setSelected(false);
        select_box_pas_keraye.setSelected(true);
        txt_box_pas_keraye.setSelected(true);
        select_box_online.setVisibility(View.GONE);
    }


    /**
     * box method type peyment
     **/
    public void selectBoxNaghdi() {
        select_box_naghdi.setSelected(true);
        txt_box_naghdi.setSelected(true);
        icon_box_naghdi.setSelected(true);
        select_box_online.setSelected(false);
        txt_box_online.setSelected(false);
        icon_box_online.setSelected(false);

        select_box_kartkhon.setSelected(false);
        txt_box_kartkhon.setSelected(false);
        icon_box_kartkhon.setSelected(false);
    }

    public void selectBoxOnline() {
        select_box_naghdi.setSelected(false);
        txt_box_naghdi.setSelected(false);
        icon_box_naghdi.setSelected(false);
        select_box_online.setSelected(true);
        txt_box_online.setSelected(true);
        icon_box_online.setSelected(true);

        select_box_kartkhon.setSelected(false);
        txt_box_kartkhon.setSelected(false);
        icon_box_kartkhon.setSelected(false);
    }

    public void selectBoxKartkhon() {
        select_box_naghdi.setSelected(false);
        txt_box_naghdi.setSelected(false);
        icon_box_naghdi.setSelected(false);
        select_box_online.setSelected(false);
        txt_box_online.setSelected(false);
        icon_box_online.setSelected(false);

        select_box_kartkhon.setSelected(true);
        txt_box_kartkhon.setSelected(true);
        icon_box_kartkhon.setSelected(true);
    }


    /**
     * box method byme
     **/




    ///////////////////////////////////////////
    // editText price
    @BindView(R.id.editText3)
    EditText edtTextPrice;

    // button price custom
    @BindView(R.id.btnWallet10)
    TextView btnWallet10Toman;
    // button price custom
    @BindView(R.id.txtBalance)
    TextView txtBalance;
    @BindView(R.id.btnWallet20)
    TextView btnWallet20Toman;
    @BindView(R.id.btnWallet50)
    TextView btnWallet50Toman;


    @BindView(R.id.button5)
    Button button_p;
    @BindView(R.id.imageView18)
    ImageView increase;
    // btn price --
    @BindView(R.id.imageView10)
    ImageView reduce;
    private Boolean isFirst = false;


    private void updatetext(){
        userLogin = GoTaxiApplication.getInstance(getApplication()).getLoginUserD();
        txtBalance.setText(formatMony(userLogin.getBalance()));
    }


    private void selectorBtnWallet10Toman(){
        btnWallet10Toman.setSelected(true);
        btnWallet20Toman.setSelected(false);
        btnWallet50Toman.setSelected(false);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("10000");
    }

    private void selectorBtnWallet20Toman(){
        btnWallet10Toman.setSelected(false);
        btnWallet20Toman.setSelected(true);
        btnWallet50Toman.setSelected(false);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("20000");
    }

    private void selectorBtnWallet50Toman(){
        btnWallet10Toman.setSelected(false);
        btnWallet20Toman.setSelected(false);
        btnWallet50Toman.setSelected(true);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("50000");
    }


    public String formatMony(String price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }
    public static void saveUser(Context context, UserData user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(UserData.class);
        realm.copyToRealm(user);
        realm.commitTransaction();

        GoTaxiApplication.getInstance(context).setLoginUserD(user);
    }

}
