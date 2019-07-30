package com.rachcode.peykman.mRideCar;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rachcode.peykman.Favorites;
import com.rachcode.peykman.config.General;
import com.makeramen.roundedimageview.RoundedImageView;

import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.FCMHelper;
import com.rachcode.peykman.api.MapDirectionAPI;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.gmap.directions.Directions;
import com.rachcode.peykman.gmap.directions.Route;
import com.rachcode.peykman.home.ChatActivity;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.mSend.SendActivity;
import com.rachcode.peykman.model.Driver;
import com.rachcode.peykman.model.FavoriteAddress;
import com.rachcode.peykman.model.Transaksi;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.book.GetDataRestoRequestJson;
import com.rachcode.peykman.model.json.book.GetDriverLatLongRequest;
import com.rachcode.peykman.model.json.book.GetDriverLatLongResponseJson;
import com.rachcode.peykman.model.json.book.GetFavoriteAddressResponseJson;
import com.rachcode.peykman.model.json.fcm.CancelBookRequestJson;
import com.rachcode.peykman.model.json.fcm.CancelBookResponseJson;
import com.rachcode.peykman.model.json.fcm.DriverRequest;
import com.rachcode.peykman.model.json.fcm.DriverResponse;
import com.rachcode.peykman.model.json.fcm.FCMMessage;
import com.rachcode.peykman.model.json.user.CheangePayResponse;
import com.rachcode.peykman.model.json.user.RegisterResponseJson;
import com.rachcode.peykman.signUp.SignUpActivity;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.Utils;
import com.rachcode.peykman.utils.db.DBHandler;
import com.rachcode.peykman.utils.db.Queries;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.config.General.FCM_KEY;
import static com.rachcode.peykman.model.FCMType.ORDER;
import static com.rachcode.peykman.model.ResponseCode.ACCEPT;
import static com.rachcode.peykman.model.ResponseCode.CANCEL;
import static com.rachcode.peykman.model.ResponseCode.FINISH;
import static com.rachcode.peykman.model.ResponseCode.REJECT;
import static com.rachcode.peykman.model.ResponseCode.START;
import static com.rachcode.peykman.service.GoTaxiMessagingService.BROADCAST_ORDER;

//import com.gumcode.mangjek.utils.Log;

public class InProgressActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private static final int REQUEST_PERMISSION_CALL = 992;
    @BindView(R.id.rideCar_pickUpText)
    EditText pickUpText;
    @BindView(R.id.rideCar_destinationText)
    EditText destinationText;
    @BindView(R.id.rideCar_distance)
    TextView distanceText;
    @BindView(R.id.rideCar_price)
    TextView priceText;
    Marker marker;
    Boolean isFirst = false;
    @BindView(R.id.driver_image)
    de.hdodenhof.circleimageview.CircleImageView driverImage;
    @BindView(R.id.driver_name)
    TextView driverName;
    @BindView(R.id.order_number)
    TextView orderNumber;
    Handler mainHandler;
    Boolean is =false;

    // pelaq motor
    @BindView(R.id.icon_box_kartkhon)
    ImageView icon_box_kartkhon;
    @BindView(R.id.txt_box_kartkhon)
    TextView txt_box_kartkhon;
    @BindView(R.id.select_box_naghdi)
    LinearLayout select_box_naghdi;
    @BindView(R.id.icon_box_naghdi)
    ImageView icon_box_naghdi;
    @BindView(R.id.txt_box_naghdi)
    TextView txt_box_naghdi;
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

    @BindView(R.id.select_box_online)
    LinearLayout select_box_online;
    @BindView(R.id.icon_box_online)
    ImageView icon_box_online;
    @BindView(R.id.txt_box_online)
    TextView txt_box_online;
    @BindView(R.id.plauqeMotor)
    ConstraintLayout plauqeMotor;
    @BindView(R.id.driver_police_number1)
    TextView driverPoliceNumber1;


    @BindView(R.id.select_box_kartkhon)
    LinearLayout select_box_kartkhon;
    @BindView(R.id.driver_police_number2)
    TextView driverPoliceNumber2;
    @BindView(R.id.driver_car)
    TextView driverCar;

    // pelaq moshin
    @BindView(R.id.plauqeMashin)
    ConstraintLayout plauqeMashin;


    @BindView(R.id.driver_police_numberA)
    TextView driver_police_numberA;
    @BindView(R.id.driver_police_numberB)
    TextView driver_police_numberB;
    @BindView(R.id.driver_police_numberC)
    TextView driver_police_numberC;




    @BindView(R.id.driver_police_numberr)
    TextView driverPoliceNumberr;

    @BindView(R.id.constraintLayout5)
    LinearLayout btnRequest;
    @BindView(R.id.mSend_price)
    TextView mSend_price;
    @BindView(R.id.btn_request)
    ConstraintLayout btn_request;


    @BindView(R.id.scroll_view_design)
    ScrollView scroll_view_design;
    @BindView(R.id.design_request)
    ConstraintLayout design_request;


    @BindView(R.id.driver_arriving_time)
    TextView driverArrivingTime;

    @BindView(R.id.call_pyk)
    android.support.constraint.ConstraintLayout call_pyk;
    @BindView(R.id.cancelBook)
    CardView cancelBook;
    Bundle orderBundle;
    @BindView(R.id.price_wallet)
    TextView my_price;
    Driver driver;
    @BindView(R.id.byme)
    TextView byme;
    @BindView(R.id.price)
    TextView totalprice;
    @BindView(R.id.code_takhfif)
    TextView codee_takhfif;
    @BindView(R.id.price_pardakht)
    TextView price_pardakht;
    DriverRequest request;
    UserData loginUser;
    Realm realm;
    private GoogleMap gMap;
    private boolean isMapReady = false;
    private Location lastKnownLocation;
    private GoogleApiClient googleApiClient;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private Context context;
    private boolean isCancelable = true;
    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(okhttp3.Call call, IOException e) {

        }

        @Override
        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = response.body().string();
                final long distance = MapDirectionAPI.getDistance(InProgressActivity.this, json);
                if (distance >= 0) {
                    InProgressActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
/*
                            updateLineDestination(json);
*/
//                            updateDistance(distance);
                        }
                    });
                }
            }
        }
    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderBundle = intent.getExtras();
            orderHandler(orderBundle.getInt("code"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);
        ButterKnife.bind(this);
        mainHandler = new Handler(Looper.getMainLooper());
        /*if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
*/

        btnRequest.setSelected(true);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_view_design.setVisibility(View.VISIBLE);
                design_request.setVisibility(View.VISIBLE);
            }
        });


        select_box_naghdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDoialg("2");
                    selectBoxNaghdi();

             }
        });



        select_box_kartkhon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        ShowDoialg("3");
                    selectBoxKartkhon();

            }
        });

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_view_design.setVisibility(View.GONE);
                design_request.setVisibility(View.GONE);
            }
        });

        context = getApplicationContext();
        realm = Realm.getDefaultInstance();

//        readTransaction();

        loginUser = GoTaxiApplication.getInstance(InProgressActivity.this).getLoginUserD();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rideCar_mapView);
        mapFragment.getMapAsync(this);

        driver = (Driver) getIntent().getSerializableExtra("driver");

        request = (DriverRequest) getIntent().getSerializableExtra("request");
        mSend_price.setText(String.valueOf(request.getPrice()));
        android.util.Log.i("driverLog", " drivergetPhoto: " + driver.getPhoto());

        Log.e("DATA DRIVER", driver.getFirstName() + " " + driver.getLastName());
        Log.e("DATA REQUEST", request.getOriginAddress() + " to " + request.getDestinationAddress());


        pickUpText.setText(request.getOriginAddress());
        destinationText.setText(request.getDestinationAddress());
        String format = String.format(Locale.US, "Distance %.2f " + General.UNIT_OF_DISTANCE, request.getDistance());
        distanceText.setText(format);



        priceText.setText(formatMony(request.getPrice()));

        //Glide.with(getApplicationContext()).load(driver.getPhoto()).into(driverImage);
        driverName.setText(driver.getFirstName() + " " + driver.getLastName());
        orderNumber.setText("Order no. " + request.getid());


        price_pardakht.setText(formatMony(request.getPrice()));

        byme.setText(formatMony(String.valueOf(request.getByme_price())));
        codee_takhfif.setText(formatMony(String.valueOf(request.getPrice_takhfifed())));
        totalprice.setText(formatMony(request.getTotalPrice()));
        my_price.setText(formatMony(String.valueOf(request.getUser_inventory())));

        Toast.makeText(context, "00000000000", Toast.LENGTH_SHORT).show();

            select_box_online.setClickable(false);
            Toast.makeText(InProgressActivity.this, "موجودی شما برای پرداخت آنلاین کافی نیست !", Toast.LENGTH_SHORT).show();
               switch (request.getPay_type())
            {
                case 0:
                    selectBoxPishKeraye();
                    break;
                case 1:

                    selectBoxPasKeraye();
                    break;

            }


            switch (request.getIsPay())
            {
                case 1:
                    selectBoxOnline();
                    break;
                case 2:
                    selectBoxNaghdi();
                    break;
                case 3:
                    selectBoxKartkhon();
                    break;
            }

        // set pelaq
        switch (driver.getFeature()) {
            case "smotor":
                // set peyk motori
                plauqeMotor.setVisibility(View.VISIBLE);
                String[] p = driver.getNumberOfVehicle().split("-");
                driverPoliceNumber1.setText(p[0].replace("", " "));
                driverPoliceNumber2.setText(p[1].replace("", " "));

                android.util.Log.i("www", "NumberOfVehicle: "+driver.getNumberOfVehicle());
                break;

            case "scar":
                // set peyk mashini
                android.util.Log.i("www", "NumberOfVehicle: "+driver.getNumberOfVehicle());
                plauqeMashin.setVisibility(View.VISIBLE);
                String[] numberOfVehicle = driver.getNumberOfVehicle().replace(""," ").split("-");
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

        }
        /** box selector type peyment **/




        select_box_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(loginUser.getBalance()) < Integer.parseInt(request.getPrice())) {
                    Toast.makeText(InProgressActivity.this, "موجودی شما برای پرداخت آنلاین کافی نیست !", Toast.LENGTH_SHORT).show();
                } else {
                  ShowDoialg("1");

                        selectBoxOnline();



                }
            }
        });



        driverCar.setText("نوع پیک: " + driver.getBrand() + " " + driver.getType() + " (" + driver.getColor() + ")");

        driverArrivingTime.setText("Estimated trip time " + getIntent().getDoubleExtra("time_distance", 0) + " menit");

        new Thread(updateLocationOtime(driver.getId())).start();
        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

/*
        chatttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Chat with driver", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("reg_id", driver.getRegId());
                startActivity(intent);
            }
        });
*/

        call_pyk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InProgressActivity.this);
                alertDialogBuilder.setTitle("تماس با راننده");
                alertDialogBuilder.setMessage("ایا شما میخواهید با راننده تماس بگیرید" + driver.getPhone() + "?");
                alertDialogBuilder.setPositiveButton("بله",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (ActivityCompat.checkSelfPermission(InProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(InProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + driver.getPhone()));
                                startActivity(callIntent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        cancelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancelable) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InProgressActivity.this);
                    alertDialogBuilder.setTitle("لغو سفر");
                    alertDialogBuilder.setMessage("آیا شما از لغو سفر خود اطمینان دارید ؟");
                    alertDialogBuilder.setPositiveButton("بله",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    cancelOrder();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can not be undone, trip has started!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

private void ShowDoialg(final String is_pay){
    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InProgressActivity.this);
    alertDialogBuilder.setTitle("آپدیت سفر");
    alertDialogBuilder.setMessage("آیا از آپدیت اطلاعات سفر مطمئن هستید ؟");
    alertDialogBuilder.setPositiveButton("بله",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                    UpdateTransActionType(is_pay);

                    switch (Integer.parseInt(is_pay)){

                        case 1:
                            selectBoxOnline();
                            break;
                        case 2:
                            selectBoxNaghdi();
                            break;
                        case 3:
                            selectBoxKartkhon();
                            break;
                        }

                }
            });

    alertDialogBuilder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();

        }
    });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.setCancelable(false);
    alertDialog.show();

}

    public Runnable updateLocationOtime(final String driver_id) {
        android.util.Log.i("immsmdsm", "firsttttttt: ");

        return new Runnable() {
            @Override
            public void run() {

                android.util.Log.i("immsmdsm", "towwwwwww: ");
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        GetLastLocation(driver_id);

                    }
                });

                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.w("www", "run: locccc");
                        android.util.Log.i("immsmdsm", "threeeee: ");

                        Thread myt = new Thread(updateLocationOtime(driver_id));
                        myt.start();
                    }
                }, 20000);


                //   new GetDeviceLocationOnline().execute(cacheDevice);


            }


        };

    }
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
    public String formatMony(String price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = gMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    public void UpdateTransActionType(String type_id){

        UserService service = ServiceGenerator.createService(UserService.class);
        service.cheangePayment(request.getid(),type_id).enqueue(new Callback<CheangePayResponse>() {
            @Override
            public void onResponse(Call<CheangePayResponse> call, Response<CheangePayResponse> response) {
                 if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("sucess")) {

                        Toast.makeText(InProgressActivity.this, "عملیات با موفقیت انجام شد ", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(InProgressActivity.this, " مشکلی در فرایند به وجود آمده ! ", Toast.LENGTH_LONG).show();
                    }
                } else {
                     Toast.makeText(InProgressActivity.this, " مشکلی در فرایند به وجود آمده ! ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CheangePayResponse> call, Throwable t) {

            }


        });
    }

    public void GetLastLocation(String driver_id) {
        android.util.Log.i("immsmdsm", "xxxxxx: ");

        UserService service = ServiceGenerator.createService(UserService.class);
        service.getDriverLtLong(driver_id).enqueue(new Callback<GetDriverLatLongResponseJson>() {
            @Override
            public void onResponse(Call<GetDriverLatLongResponseJson> call, Response<GetDriverLatLongResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getstatus().equals("success")) {
                         GetDriverLatLongResponseJson responseJson = new GetDriverLatLongResponseJson();
                        responseJson = response.body();
                        Toast.makeText(context, "lat: " + responseJson.getData().get(0).getLatitude(), Toast.LENGTH_SHORT).show();
                       //IF FOR MAKE driver marker Onmap For First Time
                        if (!isFirst) {
                            isFirst = true;
                            android.util.Log.i("driverjobbb", "onResponse: "+driver.getDriverJob());
                            switch (driver.getFeature()) {
                                case "Send-Motor":
                                    marker = gMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(responseJson.getData().get(0).getLatitude(), responseJson.getData().get(0).getLongitude()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ride_position)));
                                    break;

                                case "Send-Car":
                                    marker = gMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(responseJson.getData().get(0).getLatitude(), responseJson.getData().get(0).getLongitude()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position)));
                                    break;
                                    case "Send-Vanet":
                                    marker = gMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(responseJson.getData().get(0).getLatitude(), responseJson.getData().get(0).getLongitude()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vanet_position)));
                                    break;

                            }


                        } else {
                            animateMarker(marker, new LatLng(responseJson.getData().get(0).getLatitude(), responseJson.getData().get(0).getLongitude()), false);
                        }
                    } else {
                        Toast.makeText(context, "get lat long erroooooor", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDriverLatLongResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void cancelOrder() {
        UserData loginUser = GoTaxiApplication.getInstance(InProgressActivity.this).getLoginUserD();
        CancelBookRequestJson request = new CancelBookRequestJson();
/*
        request.id = loginUser.getId();
*/
        request.transaction_id = this.request.getid();
        request.driver_id = driver.getId();

        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.cancelOrder(request).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(Call<CancelBookResponseJson> call, Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().mesage.equals("order canceled")) {
                        Toast.makeText(InProgressActivity.this, "سفرشما کنسل شد!", Toast.LENGTH_SHORT).show();
                        new Queries(new DBHandler(getApplicationContext())).truncate(DBHandler.TABLE_CHAT);
                        finish();
                    } else {
                        Toast.makeText(InProgressActivity.this, "خطا!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelBookResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(InProgressActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(this.request.getid());
        response.setResponse(DriverResponse.REJECT);

        FCMMessage message = new FCMMessage();
        message.setTo(driver.getRegId());
        message.setData(response);


        FCMHelper.sendMessage(FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.e("CANCEL REQUEST", "sent");
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                Log.e("CANCEL REQUEST", "failed");
            }
        });
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
        super.onStop();
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
        gMap = googleMap;

        isMapReady = true;

        updateLastLocation(true);
        requestRoute();

        /*if (pickUpMarker != null) pickUpMarker.remove();
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(pickUpLatLng)
                .title("مبدا")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_pik)));


        if (destinationMarker != null) destinationMarker.remove();
        destinationMarker = gMap.addMarker(new MarkerOptions()
                .position(destinationLatLng)
                .title("مقصد")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_dis)));*/


        setMarker();
    }

    private void setMarker() {
        LatLng latLngPikup = null;
        LatLng dis1 = null;
        LatLng dis2 = null;
        LatLng dis3 = null;
        LatLng dis4 = null;
        switch (request.getdestination_count()) {
            case 1:
                latLngPikup = new LatLng(request.getStartLatitude(),request.getStartLongitude());
                dis1 = new LatLng(request.getEndLatitude(), request.getEndLongitude());
                break;


            case 2:
                latLngPikup = new LatLng(request.getStartLatitude(),request.getStartLongitude());
                dis1 = new LatLng(request.getEndLatitude(), request.getEndLongitude());
                dis2 = new LatLng(request.getEnd_latitude_second(), request.getEnd_longitude_second());
                break;


            case 3:
                latLngPikup = new LatLng(request.getStartLatitude(),request.getStartLongitude());
                dis1 = new LatLng(request.getEndLatitude(), request.getEndLongitude());
                dis2 = new LatLng(request.getEnd_latitude_second(), request.getEnd_longitude_second());
                dis3 = new LatLng(request.getEnd_latitude_third(), request.getEnd_longitude_third());
                break;


            case 4:
                latLngPikup = new LatLng(request.getStartLatitude(),request.getStartLongitude());
                dis1 = new LatLng(request.getEndLatitude(), request.getEndLongitude());
                dis2 = new LatLng(request.getEnd_latitude_second(), request.getEnd_longitude_second());
                dis3 = new LatLng(request.getEnd_latitude_third(), request.getEnd_longitude_third());
                dis4 = new LatLng(request.getEnd_latitude_fourth(), request.getEnd_longitude_fourth());

                break;
        }

        switch (request.getdestination_count()) {
            case 0:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 14f));
                break;
            case 1:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 14f));



                break;
            case 2:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(1, dis1));
                gMap.addMarker(createMark(2, dis2));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 14f));


                break;
            case 3:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(2, dis1));
                gMap.addMarker(createMark(2, dis2));
                gMap.addMarker(createMark(3, dis3));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 14f));


                break;
            case 4:
                gMap.addMarker(createMark(0, latLngPikup));
                gMap.addMarker(createMark(3, dis1));
                gMap.addMarker(createMark(3, dis2));
                gMap.addMarker(createMark(3, dis3));
                gMap.addMarker(createMark(4, dis4));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPikup, 14f));

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
                        pickUpLatLng, 15f)
                );

                gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            }
//            fetchNearDriver();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation(true);
            } else {
                // TODO: 10/15/2018 Tell user to use GPS
            }
        }

        if (requestCode == REQUEST_PERMISSION_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Call permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Call permission restricted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestRoute() {
        if (pickUpLatLng != null && destinationLatLng != null) {
            MapDirectionAPI.getDirection(pickUpLatLng, destinationLatLng).enqueue(updateRouteCallback);
        }
    }

 /*   private void updateLineDestination(String json) {
        Directions directions = new Directions(InProgressActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(InProgressActivity.this, R.color.colorAccent))
                        .width(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void orderHandler(int code) {
        switch (code) {
            case REJECT:
                Log.e("DRIVER RESPONSE", "reject");
                isCancelable = false;
                break;
            case ACCEPT:
                Log.e("DRIVER RESPONSE", "accept");
                break;
            case CANCEL:
                Log.e("DRIVER RESPONSE", "cancel");
                finish();
                break;

            case START:
                Log.e("DRIVER RESPONSE", "start");
                isCancelable = false;
                Toast.makeText(getApplicationContext(), "سفرشما شروع شد ", Toast.LENGTH_SHORT).show();
                break;
            case FINISH:
                Log.e("DRIVER RESPONSE", "finish");
                isCancelable = false;
//                new Queries(new DBHandler(getApplicationContext())).truncate(DBHandler.TABLE_CHAT);
                Toast.makeText(getApplicationContext(), "سفر شما تمام شد", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RateDriverActivity.class);
                intent.putExtra("id_transaksi", request.getid());
                intent.putExtra("id_pelanggan", loginUser.getId());
                intent.putExtra("driver_photo", driver.getPhoto());
                intent.putExtra("id_driver", driver.getId());
                startActivity(intent);
                finish();
                break;
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());

    }

    private void readTransaction() {
        RealmResults<Transaksi> results = realm.where(Transaksi.class).findAll();

        Log.e("ALL TRANSACTION", results.toString());
        Log.e("TRANSACTION SIZE", results.size() + "");
        for (int i = 0; i < results.size(); i++) {
            Log.e("TRANSACTION ID", results.get(i).getCustomerId() + "");
        }
    }
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    private MarkerOptions createMark(int positionMarker, final LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        switch (positionMarker) {
            case 0:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icm_pik));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
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
}
