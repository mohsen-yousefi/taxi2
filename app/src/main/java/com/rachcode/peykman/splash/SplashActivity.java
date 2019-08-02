package com.rachcode.peykman.splash;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rachcode.peykman.mRideCar.InProgressActivity;
import com.rachcode.peykman.mRideCar.RateDriverActivity;
import com.rachcode.peykman.mSend.SendActivity;
import com.rachcode.peykman.model.Fitur;
import com.rachcode.peykman.model.RateDriverS;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.fcm.DriverRequest;
import com.rachcode.peykman.model.json.user.InprogressTransaction;
import com.rachcode.peykman.model.json.user.LoginRequestJson;
import com.rachcode.peykman.model.json.user.LoginResponseJson;
import com.rachcode.peykman.signUp.VerificationActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import com.rachcode.peykman.BuildConfig;
import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.json.menu.VersionRequestJson;
import com.rachcode.peykman.model.json.menu.VersionResponseJson;
import com.rachcode.peykman.signIn.SignInActivity;
import com.rachcode.peykman.utils.ConnectivityUtils;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.SnackbarController;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView VersionName;
    @BindView(R.id.progressBarSplash)
    ProgressBar progressBar;
    private boolean connectionAvailable;
    private GoogleApiClient googleApiClient;
    private SnackbarController snackbarController;
    PendingResult<LocationSettingsResult> result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        android.util.Log.e("www", "token: "+ FirebaseInstanceId.getInstance().getToken());
        //android.util.Log.e("www", "id123 : "+ GoTaxiApplication.getInstance(this).getLoginUserD().getId());

        connectionAvailable = false;
        android.util.Log.i("ppp", "1");
        connectionAvailable = ConnectivityUtils.isConnected(this);
        if (!connectionAvailable) {
            showPopupHold("اینترنت شما قطع است !");
        } else {
            //check runtime premission
            checkLocationPermission();
            //chek Location is ON or No

        }


    }

    public void getLocation() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(SplashActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //**************************
            result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    //final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            android.util.Log.i("llllllll", "dadddddddd");
                            //...
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        SplashActivity.this,
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            //...
                            break;
                    }
                }
            });
        }

    }

    public void chekAppVersion() {
        PackageInfo pInfo;
        VersionRequestJson request = new VersionRequestJson();
        VersionName = (TextView)

                findViewById(R.id.VersionName);

        String version = BuildConfig.VERSION_NAME;
        VersionName.setText("نسخه: " + version);

        int versiterbaru = BuildConfig.VERSION_CODE;
        request.version = String.valueOf(versiterbaru);
        request.application = "0";
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            request.version = pInfo.versionCode + "";
        } catch (
                PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.checkVersion(request).

                enqueue(new Callback<VersionResponseJson>() {
                    @Override
                    public void onResponse
                            (Call<VersionResponseJson> call, Response<VersionResponseJson> response) {
                        if (response.isSuccessful()) {

                            if (response.body().new_version.equals("yes")) {
                                showPopupUpdate(response.body().message);
                            } else if (response.body().new_version.equals("no")) {
                                start();
                            }
                        } else {

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int arg1) {
                                            dialog.dismiss();
                                            start();
                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    start();
                                }
                            });
                            alertDialogBuilder.setMessage(response.body().message);
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VersionResponseJson> call, Throwable t) {
                        t.printStackTrace();


                    }
                });
    }
public void ChekUserStatus() {
    LoginRequestJson request = new LoginRequestJson();
    Realm realm = Realm.getDefaultInstance();
    UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();
     request.setphone(loginUser.getPhone());
     request.setRegId(null);


    final UserService service = ServiceGenerator.createService(UserService.class);
    service.login(request).enqueue(new Callback<LoginResponseJson>() {
        @Override
        public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
            if (response.isSuccessful()) {
                if (response.body().getMessage().equalsIgnoreCase("found")) {

                    service.driver_request(response.body().getData().get(0).getId()).enqueue(new Callback<InprogressTransaction>() {
                        @Override
                        public void onResponse(Call<InprogressTransaction> call, Response<InprogressTransaction> response) {

                            android.util.Log.i("ddddddddddddsq", "onResponse: "+response.body().toString());
                            if (response.isSuccessful()){
                                InprogressTransaction inprogressTransaction = response.body();
                                if (inprogressTransaction.getstatus().equals("fail")){
                                    Intent   intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (inprogressTransaction.getstatus().equals("success")){
                                    Intent intent = new Intent(SplashActivity.this, InProgressActivity.class);
                                    intent.putExtra("driver", inprogressTransaction.getdriver());
                                    intent.putExtra("request", inprogressTransaction.getData());
                                     startActivity(intent);
                                }
                              }

                        }

                        @Override
                        public void onFailure(Call<InprogressTransaction> call, Throwable t) {
                            android.util.Log.i("ddddddddddddsq", "onFailure: "+t.getMessage());
                        }
                    });

                } else {
                    Toast.makeText(SplashActivity.this,"مشکلی  پیش آمده با پشتیبانی در ارتباط باشید !", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }
            }
        }

        @Override
        public void onFailure(Call<LoginResponseJson> call, Throwable t) {
             t.printStackTrace();
            Toast.makeText(SplashActivity.this,"مشکلی  پیش آمده با پشتیبانی در ارتباط باشید !", Toast.LENGTH_SHORT).show();
        }
    });
    }
    private MaterialDialog showPopupUpdate(String message) {
        final MaterialDialog md = new MaterialDialog.Builder(this)
                .title("New Apps Available")
                .content(message)
                .icon(new IconicsDrawable(this)
                        .icon(FontAwesome.Icon.faw_google)
                        .color(Color.RED)
                        .sizeDp(24))
                .positiveText(R.string.update_now)
                .negativeText(R.string.text_cancel)
                .cancelable(false)
                .positiveColor(Color.BLUE)
                .negativeColor(Color.RED)
                .show();

        View positive = md.getActionButton(DialogAction.POSITIVE);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                Log.d("AppPackage", appPackageName);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }
        });
        View negative = md.getActionButton(DialogAction.NEGATIVE);
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
                finish();
            }

        });
        return md;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 99) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            chekAppVersion();
            } else {
                showPopupHold("برای اجرای این نرم افزار نیازمند لوکیشن هستید  !");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
///if don have permission for ACCESS_COARSE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            // Should we show an explanation?
            // No explanation needed, we can request the permission.
            android.util.Log.i("perrrrr", "checkLocationPermission: perrrrr");

            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.Group.LOCATION)
                   .onGranted(new Action<List<String>>() {
                       @Override
                       public void onAction(List<String> data) {
                            chekAppVersion();
                       }
                   })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            Toast.makeText(SplashActivity.this, "برای استفاده از نرم افزار  باید لوکیشن شما فعال باشد", Toast.LENGTH_LONG).show();
                             new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            },2000);
                            finish();
                        }
                    })

                    .start();
    /*                ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);*/


            return false;
        } else {
            if (!isLocationEnabled(this)) {

                getLocation();
            }else{

                chekAppVersion();
            }

            return true;
        }
    }

    /*private MaterialDialog showPopupHold(String message) {
        final MaterialDialog md = new MaterialDialog.Builder(this)
                .title("اطلاعیه")
                .content(message)
                .icon(new IconicsDrawable(this)
                        .icon(FontAwesome.Icon.faw_android)
                        .color(Color.BLUE)
                        .sizeDp(24))
                .positiveText("باشه")
                .negativeText(R.string.no)
                .cancelable(false)
                .positiveColor(Color.BLUE)
                .negativeColor(Color.RED)
                .show();

        View positive = md.getActionButton(DialogAction.POSITIVE);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
                start();
                finish();
            }
        });
        View negative = md.getActionButton(DialogAction.NEGATIVE);
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
                finish();
            }

        });

        return md;
    }*/


    private void showPopupHold(String message) {
        final AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
        final AlertDialog popup = popupBuilder.create();
        View viewPopup = getLayoutInflater().inflate(R.layout.popup,null);

        TextView title = viewPopup.findViewById(R.id.textView48);
        title.setText(message);

        TextView ok = viewPopup.findViewById(R.id.txt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                start();
                finish();
            }
        });


        popupBuilder.setView(viewPopup);
        popupBuilder.show();
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void start() {
        progressBar.setVisibility(View.GONE);
        UserData user = GoTaxiApplication.getInstance(this).getLoginUserD();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RateDriverS> rateDriverS=realm.where(RateDriverS.class).findAll();

        if (!rateDriverS.toString().equals("[]")){
            RateDriverS rateDriverS1 = rateDriverS.get(0);
            Intent intent = new Intent(SplashActivity.this, RateDriverActivity.class);
            intent.putExtra("transaction_id", rateDriverS1.getTransaction_id());
            intent.putExtra("customer_id", rateDriverS1.getCustomer_id());
            intent.putExtra("driver_photo", rateDriverS1.getDriver_photo());
            intent.putExtra("dfirst_name", rateDriverS1.getDfirst_name());
            intent.putExtra("dlast_name", rateDriverS1.getDlast_name());
            intent.putExtra("brand", rateDriverS1.getBrand());
            intent.putExtra("type", rateDriverS1.getType());
            intent.putExtra("color", rateDriverS1.getColor());
            intent.putExtra("id_driver", rateDriverS1.getId_driver());
             startActivity(intent);
            finish();
          }else{
            Intent intent;

            if (user != null) {

                ChekUserStatus();
            } else {
                intent = new Intent(SplashActivity.this, VerificationActivity.class);
                startActivity(intent);
                finish();

            }
        }



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
