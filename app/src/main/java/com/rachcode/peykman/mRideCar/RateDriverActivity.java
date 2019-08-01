package com.rachcode.peykman.mRideCar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ornolfr.ratingview.RatingView;
import com.bumptech.glide.Glide;
import com.github.ornolfr.ratingview.RatingView;
import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.book.RateDriverRequestJson;
import com.rachcode.peykman.model.json.book.RateDriverResponseJson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateDriverActivity extends AppCompatActivity {

    RateDriverActivity activity;
    float nilai;
    @BindView(R.id.driver_image)
    de.hdodenhof.circleimageview.CircleImageView driverImage;
    @BindView(R.id.driver_name)
    TextView driver_name;
    @BindView(R.id.driver_job)
    TextView driver_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);
        ButterKnife.bind(this);
        if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }


        activity = RateDriverActivity.this;
        TextView butSubmit = (TextView) findViewById(R.id.butSubmit);
        final RatingView ratingBar = (RatingView) findViewById(R.id.ratingBar);
        final EditText addComment = (EditText) findViewById(R.id.addComment);

        final String idTransaksi = getIntent().getStringExtra("id_transaksi");
        final String idPelanggan = getIntent().getStringExtra("id_pelanggan");
        final String idDriver = getIntent().getStringExtra("id_driver");
        final String driver_photo = getIntent().getStringExtra("driver_photo");

        final String driver_name = getIntent().getStringExtra("dfirst_name")+" "+getIntent().getStringExtra("dlast_name");

        final String driver_type ;

        String driverType =  "نوع سرویس: " + getIntent().getStringExtra("brand")+ " " +  getIntent().getStringExtra("type")+ " (" + getIntent().getStringExtra("color") + ")";

        this.driver_name.setText(driver_name);



        this.driver_job.setText(driverType);

        /*
        Log.i(",,,,,", "onCreate: "+driver_photo);
*/

        Glide.with(getApplicationContext()).load(driver_photo).into(driverImage);



          ratingBar.setOnRatingChangedListener(new RatingView.OnRatingChangedListener() {
            @Override
            public void onRatingChange(float oldRating, float newRating) {
                nilai = newRating;
            }
        });

//        final JSONObject jRate = new JSONObject();
//        try {
//            jRate.put("id_transaksi", idTransaksi);
//            jRate.put("id_pelanggan", idPelanggan);
//            jRate.put("id_driver", idDriver);
//            jRate.put("rating", (int)nilai);
//            jRate.put("catatan", addComment.getText().toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateDriverRequestJson request = new RateDriverRequestJson();
                request.transaction_id = idTransaksi;
                request.customer_id = idPelanggan;
                request.driver_id = idDriver;
                request.rating = nilai + "";
                request.note = addComment.getText().toString();

                ratingUser(request);
//                Toast.makeText(RatingUserActivity.this, "Rating : "+(int)nilai+"\nKomentar : "+addComment.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ratingUser(RateDriverRequestJson request) {

        UserData loginUser = GoTaxiApplication.getInstance(RateDriverActivity.this).getLoginUserD();

        final ProgressDialog pd = showLoading();
        UserService service = ServiceGenerator.createService(UserService.class);
        service.rateDriver(request).enqueue(new Callback<RateDriverResponseJson>() {
            @Override
            public void onResponse(Call<RateDriverResponseJson> call, Response<RateDriverResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().mesage.equals("success")) {
                        finishDialog();
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<RateDriverResponseJson> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                Toast.makeText(RateDriverActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private ProgressDialog showLoading() {
        ProgressDialog ad = ProgressDialog.show(activity, "", "Loading...", true);
        return ad;
    }


    private void finishDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(R.string.app_name);
        alertDialogBuilder.setMessage(R.string.rate_message);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
