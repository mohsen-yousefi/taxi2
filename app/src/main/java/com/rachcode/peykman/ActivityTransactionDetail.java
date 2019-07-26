package com.rachcode.peykman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.rachcode.peykman.model.ItemHistory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTransactionDetail extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        ButterKnife.bind(this);
        ItemHistory itemHistory = (ItemHistory) getIntent().getSerializableExtra("itemHistory");
        Driver_Name.setText(itemHistory.driver_first_name+" "+itemHistory.driver_last_name);
        Log.i("itemHistory", "onCreate: driver_first_name "+itemHistory.driver_first_name+" driver_last_name"+itemHistory.driver_last_name);
        Glide.with(getApplicationContext()).load(itemHistory.photo).into(driverImage);
        ratingBar.setRating(Float.parseFloat(itemHistory.rating));
        driver_car.setText("نوع پیک: " + itemHistory.brand + " " + itemHistory.type + " (" + itemHistory.color + ")");
        originAddres.setText(itemHistory.origin_address);
        originAddres.setText(itemHistory.origin_address);
        destriginAddres.setText(itemHistory.destination_address);
        switch (itemHistory.order_feature) {
            case "Send-Motor":
                // set peyk motori
                 String[] p = itemHistory.number_of_vehicle.split("-");
                driverPoliceNumber1.setText(p[0].replace("", " "));
                driverPoliceNumber2.setText(p[1].replace("", " "));

                break;

            case "scar":
                // set peyk mashini
                /*plauqeMashin.setVisibility(View.VISIBLE);
                String[] numberOfVehicle = driver.getNumberOfVehicle().split("-");
                driverPoliceNumber.setText(numberOfVehicle[0].replace(""," "));
                driverPoliceNumberr.setText(numberOfVehicle[1].replace(""," "));
*/
                //plauqeMashin.setVisibility(View.VISIBLE);
                //driverPoliceNumber.setText(driver.getNumberOfVehicle());
                break;
        }


    }
}
