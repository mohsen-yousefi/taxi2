package com.rachcode.peykman.mService;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rachcode.peykman.config.General;
import com.makeramen.roundedimageview.RoundedImageView;

import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.FCMHelper;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.home.ChatActivity;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.mRideCar.RateDriverActivity;
import com.rachcode.peykman.model.Driver;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.json.fcm.CancelBookRequestJson;
import com.rachcode.peykman.model.json.fcm.CancelBookResponseJson;
import com.rachcode.peykman.model.json.fcm.DriverRequest;
import com.rachcode.peykman.model.json.fcm.DriverResponse;
import com.rachcode.peykman.model.json.fcm.FCMMessage;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.db.DBHandler;
import com.rachcode.peykman.utils.db.Queries;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
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

public class mServiceProgress extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CALL = 992;
    @BindView(R.id.btn_home)
    ImageView btnHome;
    @BindView(R.id.text_ordernum)
    TextView textOrdernum;
    @BindView(R.id.driver_image)
    RoundedImageView driverImage;
    @BindView(R.id.driver_name)
    TextView driverName;
    @BindView(R.id.driver_number)
    TextView driverNumber;
    @BindView(R.id.btn_chat)
    ImageView btnChat;
    @BindView(R.id.btn_call)
    ImageView btnCall;
    @BindView(R.id.value_service)
    TextView textService;
    @BindView(R.id.value_actype)
    TextView textActype;
    @BindView(R.id.value_quantity)
    TextView textQuantity;
    @BindView(R.id.value_problem)
    TextView textProblem;
    @BindView(R.id.value_location)
    TextView textLocation;
    @BindView(R.id.value_price)
    TextView textPrice;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Bundle orderBundle;
    Driver driver;
    DriverRequest request;
    User loginUser;
    Realm realm;
    private Context context;
    private boolean isCancelable = true;
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
        setContentView(R.layout.activity_mservice_progress);
        ButterKnife.bind(this);

        context = getApplicationContext();
        realm = Realm.getDefaultInstance();
        loginUser = GoTaxiApplication.getInstance(mServiceProgress.this).getLoginUser();

        driver = (Driver) getIntent().getSerializableExtra("driver");
        request = (DriverRequest) getIntent().getSerializableExtra("request");

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(mServiceProgress.this, MainActivity.class);
                startActivity(home);
            }
        });

        textOrdernum.setText("Order no. " + request.getid());
        Glide.with(getApplicationContext()).load(driver.getPhoto()).into(driverImage);
        driverName.setText(driver.getFirstName() + " " + driver.getFirstName());
        driverNumber.setText(driver.getPhone());

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("reg_id", driver.getRegId());
                startActivity(intent);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mServiceProgress.this);
                alertDialogBuilder.setTitle("Menghubungi Driver");
                alertDialogBuilder.setMessage("Apa anda yakin ingin menghubungi No. " + driver.getPhone() + "?");
                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (ActivityCompat.checkSelfPermission(mServiceProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(mServiceProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + driver.getPhone()));
                                startActivity(callIntent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        /*textService.setText(request.getJenisService());
        textActype.setText(request.getAcType());
        textQuantity.setText("" + request.getQuantity());
        textProblem.setText(request.getProblem());
        textLocation.setText(request.getAlamatAsal());*/

        String formattedTotal = NumberFormat.getNumberInstance(Locale.US).format(request.getPrice());
        String formattedText = String.format(Locale.US, General.MONEY +" %s.00", formattedTotal);
        textPrice.setText(formattedText);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mServiceProgress.this);
                    alertDialogBuilder.setTitle("Cancel Orders");
                    alertDialogBuilder.setMessage("Are you sure you want to cancel this order?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    cancelOrder();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "You can't cancel order, trip already started!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cancelOrder() {
        User loginUser = GoTaxiApplication.getInstance(mServiceProgress.this).getLoginUser();
        CancelBookRequestJson request = new CancelBookRequestJson();
/*
        request.id = loginUser.getId();
*/
        request.transaction_id = this.request.getid();

        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.cancelOrder(request).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(Call<CancelBookResponseJson> call, Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().mesage.equals("Order canceled")) {
                        Toast.makeText(mServiceProgress.this, "Order canceled!", Toast.LENGTH_SHORT).show();
                        new Queries(new DBHandler(getApplicationContext())).truncate(DBHandler.TABLE_CHAT);
                        finish();
                    } else {
                        Toast.makeText(mServiceProgress.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelBookResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mServiceProgress.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        DriverResponse response = new DriverResponse();
        response.type = ORDER;
/*
        response.setIdTransaksi(this.request.getIdTransaksi());
*/
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
                Toast.makeText(getApplicationContext(), "Your trip is started", Toast.LENGTH_SHORT).show();
                break;
            case FINISH:
                Log.e("DRIVER RESPONSE", "finish");
                isCancelable = false;
//                new Queries(new DBHandler(getApplicationContext())).truncate(DBHandler.TABLE_CHAT);
                Toast.makeText(getApplicationContext(), "Your trip is finished", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RateDriverActivity.class);
/*
                intent.putExtra("id_transaksi", request.getIdTransaksi());
*/
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
}
