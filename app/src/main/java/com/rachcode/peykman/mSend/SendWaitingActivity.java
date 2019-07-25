package com.rachcode.peykman.mSend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.FCMHelper;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.BookService;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.mRideCar.InProgressActivity;
import com.rachcode.peykman.model.Driver;
import com.rachcode.peykman.model.Transaksi;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.book.CheckStatusTransaksiRequest;
import com.rachcode.peykman.model.json.book.CheckStatusTransaksiResponse;
import com.rachcode.peykman.model.json.book.RequestSendRequestJson;
import com.rachcode.peykman.model.json.book.RequestSendResponseJson;
import com.rachcode.peykman.model.json.fcm.CancelBookRequestJson;
import com.rachcode.peykman.model.json.fcm.CancelBookResponseJson;
import com.rachcode.peykman.model.json.fcm.DriverRequest;
import com.rachcode.peykman.model.json.fcm.DriverResponse;
import com.rachcode.peykman.model.json.fcm.FCMMessage;
import com.rachcode.peykman.utils.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.config.General.FCM_KEY;
import static com.rachcode.peykman.model.FCMType.ORDER;
import static com.rachcode.peykman.model.json.fcm.DriverResponse.REJECT;

/**
 * Created by Androgo on 10/17/2018.
 */

public class SendWaitingActivity extends AppCompatActivity {

    public static final String REQUEST_PARAM = "RequestParam";
    public static final String DRIVER_LIST = "DriverList";
    AppCompatActivity activity;
    Transaksi transaksi;
    Thread thread;
    boolean threadRun = true;
    @BindView(R.id.waiting_cancel)
    Button cancelButton;
    private List<Driver> driverList;
    private RequestSendRequestJson param;
    private DriverRequest request;
    private int currentLoop;
    private Driver driver;
    private double timeDistance;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ButterKnife.bind(this);

        if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        activity = this;
        param = (RequestSendRequestJson) getIntent().getSerializableExtra(REQUEST_PARAM);
        driverList = (List<Driver>) getIntent().getSerializableExtra(DRIVER_LIST);

        timeDistance = getIntent().getDoubleExtra("time_distance", 0);
        currentLoop = 0;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request != null) {

                    cancelOrder();

                }


            }
        });

        sendRequestTransaksi();
    }

    private void sendRequestTransaksi() {
        UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.requestTransMSend(param).enqueue(new Callback<RequestSendResponseJson>() {
            @Override
            public void onResponse(Call<RequestSendResponseJson> call, Response<RequestSendResponseJson> response) {
                if (response.isSuccessful()) {


                    buildDriverRequest(response.body());


                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < driverList.size(); i++) {
                                if (threadRun) {

                                    fcmBroadcast(currentLoop);

                                }
                            }
                            try {
                                Thread.sleep(25000);
//                                    if(!threadRun){
//                                        thread.stop();
//                                    }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (threadRun) {
                                CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                                param.settransaction_id(transaksi.getCustomerId());
                                service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                    @Override
                                    public void onResponse(Call<CheckStatusTransaksiResponse> call, Response<CheckStatusTransaksiResponse> response) {
                                        if (response.isSuccessful()) {
                                            CheckStatusTransaksiResponse checkStatus = response.body();
                                            if (checkStatus.isStatus()) {

                                                // mmd
                                                if (checkStatus.getListDriver().size() > 0) {
                                                    Intent intent = new Intent(activity, InProgressActivity.class);
                                                    intent.putExtra("driver", checkStatus.getListDriver().get(0));
                                                    intent.putExtra("request", request);
                                                    intent.putExtra("time_distance", timeDistance);
                                                    startActivity(intent);
                                                } else {
                                                    activity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(SendWaitingActivity.this, "راننده ای یافت نشد، با تشکراز صبر و حوصله شما.", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }

                                            } else {
                                                Log.e("DRIVER STATUS", "راننده ای یافت نشد!");
                                                activity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(SendWaitingActivity.this, "راننه ای درخواست شمارانپذیرفت !", Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                                finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CheckStatusTransaksiResponse> call, Throwable t) {
                                        Log.e("DRIVER STATUS", "Driver not found!");
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SendWaitingActivity.this, "Don't get a driver, please try again.", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        finish();
                                    }
                                });
                            }
                        }
                    });
                    thread.start();


                }


                android.util.Log.i("www", "onResponse: error");
            }

            @Override
            public void onFailure(Call<RequestSendResponseJson> call, Throwable t) {

                android.util.Log.i("www", "onFailureeee: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


    private void cancelOrder() {
        UserData loginUser = GoTaxiApplication.getInstance(SendWaitingActivity.this).getLoginUserD();
        CancelBookRequestJson request = new CancelBookRequestJson();

/*
        request.id = loginUser.getId();
*/
        request.transaction_id = this.request.getid();
        request.driver_id = "D0";

        Log.d("0", this.request.getid());
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.cancelOrder(request).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(Call<CancelBookResponseJson> call, Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().mesage.equals("order canceled")) {
                        Toast.makeText(SendWaitingActivity.this, "سفر شما کنسل شد!", Toast.LENGTH_SHORT).show();
                        threadRun = false;
                        finish();
                    } else {
                        Toast.makeText(SendWaitingActivity.this, "خطا!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelBookResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SendWaitingActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(this.request.getid());
        response.setResponse(REJECT);

        FCMMessage message = new FCMMessage();
        message.setTo(driverList.get(currentLoop - 1).getRegId());
        message.setData(response);


        FCMHelper.sendMessage(FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.e("CANCEL REQUEST", "sent");
                threadRun = false;

            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                Log.e("CANCEL REQUEST", "failed");
            }
        });
    }


    private void buildDriverRequest(RequestSendResponseJson response) {
        transaksi = response.getData().get(0);
        UserData loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();
        if (request == null) {
            request = new DriverRequest();
            request.setid(transaksi.getid());
            request.setCustomerId(transaksi.getCustomerId());
            request.setRegId(loginUser.getRegId());
            request.setdestination_count(transaksi.getdestination_count());
            request.setBox_type(transaksi.getBox_type());
            request.setOrderFeature(transaksi.getOrderFeature());
            request.setInsuranceId(transaksi.getInsuranceId());
            request.setEstimated_costs(transaksi.getEstimated_costs());
            request.setProduct_type(transaksi.getProduct_type());
            request.setProduct_description(transaksi.getProduct_description());
            request.setPremium(transaksi.getPremium());
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setProduct_type(transaksi.getProduct_type());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setPrice(param.price);
            request.setByme_price(param.byme_price);
            request.setTotalPrice(param.totalPrice);
            request.setPrice_takhfifed(param.price_takhfifed);
            request.setDistance(transaksi.getDistance());
            request.setPrice(transaksi.getPrice());
            request.setorder_start_time(transaksi.getorder_start_time());
            request.setOriginAddress(transaksi.getOriginAddress());
            request.setDestinationAddress(transaksi.getDestinationAddress());
            request.setads_code(transaksi.getads_code());
            request.setads_credit(transaksi.getads_credit());
            request.setIsPay(transaksi.getIsPay());
            request.setpay_type(transaksi.getpay_type());
            android.util.Log.i("xxxxxxxxxxxxxxxxxx", "send watitng acttivty param:" + transaksi.getpay_type());


            String namaLengkap = String.format("%s %s", loginUser.getFirstName(), loginUser.getLastName());
            request.setCustomer_name(namaLengkap);
            request.setCustomer_phone(loginUser.getPhone());
            request.setType(ORDER);

            request.setItemName(transaksi.getItemName());
            request.setSenderFloor(transaksi.getSenderFloor());
            request.setSenderPlaque(transaksi.getSenderPlaque());
            request.setSenderUnit(transaksi.getSenderUnit());
            request.setReceiverFloor(param.receiver_floor);
            request.setReceiverPlaque(param.receiver_plaque);
            request.setReceiverUnit(param.receiver_unit);
             request.Setsender_name(param.name_of_the_sender);
            request.setDestination_address_second(param.destination_address_second);
            request.setDestination_address_third(param.destination_address_third);
            request.setDestination_address_fourth(param.destination_address_fourth);
            request.setReceiver_floor_second(param.receiver_floor_second);
            request.setReceiver_floor_third(param.receiver_floor_third);
            request.setReceiver_floor_fourth(param.receiver_floor_fourth);
            request.setReceiver_unit_second(param.receiver_unit_second);
            request.setReceiver_unit_third(param.receiver_unit_third);
            request.setReceiver_unit_fourth(param.receiver_unit_fourth);
             request.setReceiverPhone(param.receiver_phone);
            request.setReceiverName(param.receiver_name);
            request.setReceiver_plaque_second(param.receiver_plaque_second);
            request.setReceiver_plaque_third(param.receiver_plaque_third);
            request.setReceiver_plaque_fourth(param.receiver_plaque_fourth);
            request.setReceiver_phone_second(param.receiver_phone_second);
            request.setEnd_latitude_second(param.end_latitude_second);
            request.setEnd_longitude_second(param.end_longitude_second);
            request.setEnd_latitude_third(param.end_latitude_third);
            request.setEnd_longitude_third(param.end_longitude_third);
            request.setEnd_latitude_fourth(param.end_latitude_fourth);
            request.setEnd_longitude_fourth(param.end_longitude_fourth);
            request.setReceiver_phone_third(param.receiver_phone_third);
            request.setReceiver_phone_fourth(param.receiver_phone_fourth);
            request.setSender_phone(param.senders_phone);

        }
    }


    private void fcmBroadcast(int index) {
        android.util.Log.i("qqq", "fcmBroadcast:send ");
        Driver driverToSend = driverList.get(index);
        currentLoop++;
        request.setTime_accept(new Date().getTime() + "");
        FCMMessage message = new FCMMessage();
        message.setTo(driverToSend.getRegId());
        message.setData(request);

//        Log.e("REQUEST TO DRIVER", message.getData().toString());
        driver = driverToSend;

        FCMHelper.sendMessage(FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                android.util.Log.i("qqq", "onResponse fcm: " + response.body().toString());
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                android.util.Log.e("qqq", "onFailure: " + e.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {

    }


    @SuppressWarnings("unused")
    @Subscribe
    public void onMessageEvent(final DriverResponse response) {
        Log.e("DRIVER RESPONSE (W)", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());
//        if (currentLoop < driverList.size()) {
        if (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT)) {
            Log.d("DRIVER RESPONSE", "Terima");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    threadRun = false;

                    for (Driver cDriver : driverList) {
                        if (cDriver.getId().equals(response.getId())) {
                            driver = cDriver;
                        }
                    }
//                        saveTransaction(transaksi);
//                        saveDriver(driver);
//                        Toast.makeText(getApplicationContext(), "Transaksi " + response.getIdTransaksi() + " ada yang mau!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, InProgressActivity.class);
                    intent.putExtra("driver", driver);
                    intent.putExtra("request", request);


                    intent.putExtra("time_distance", timeDistance);
                    startActivity(intent);
                    finish();
                }
            });
        }
//            else {
//                Log.d("DRIVER RESPONSE", "Tolak");
//                if(currentLoop == (driverList.size()-1)){
//                    Intent intent = new Intent(activity, InProgressActivity.class);
//                    intent.putExtra("driver", driver);
//                    intent.putExtra("request", request);
//                    intent.putExtra("time_distance", timeDistance);
//                    threadRun = false;
//                    startActivity(intent);
//                    finish();
//                }else{
////                    fcmBroadcast(++currentLoop);
//                    currentLoop++;
//                }
//
//            }
//        }
    }

    private void saveTransaction(Transaksi transaksi) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(transaksi);
        realm.commitTransaction();
    }

    private void saveDriver(Driver driver) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Driver.class);
        realm.insert(driver);
        realm.commitTransaction();
    }


}
