package com.rachcode.peykman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.rachcode.peykman.model.ItemHistory;

import butterknife.BindView;

public class ActivityTransactionDetail extends AppCompatActivity {
    @BindView(R.id.textView23)
    TextView Driver_Name;
    @BindView(R.id.driver_image)
    de.hdodenhof.circleimageview.CircleImageView driverImage;
    @BindView(R.id.ratingBar)
    com.github.ornolfr.ratingview.RatingView ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        ItemHistory itemHistory = (ItemHistory) getIntent().getSerializableExtra("itemHistory");
        Driver_Name.setText(itemHistory.driver_first_name+" "+itemHistory.driver_last_name);
        Glide.with(getApplicationContext()).load(itemHistory.photo).into(driverImage);
        ratingBar.setRating(Float.parseFloat(itemHistory.rating));

    }
}
