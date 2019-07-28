package com.rachcode.peykman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.BookService;
import com.rachcode.peykman.model.GetStopTime;
import com.rachcode.peykman.model.json.user.GetStopTimeResponseJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GozinehaActivity extends AppCompatActivity {

    @BindView(R.id.select_box_rafto_bargasht)
    LinearLayout select_box_rafto_bargasht;
    @BindView(R.id.select_box_yek_masire)
    LinearLayout select_box_yek_masire;

    @BindView(R.id.txt_box_rafto_bargasht)
    TextView txt_box_rafto_bargasht;
    @BindView(R.id.txt_box_yek_masire)
    TextView txt_box_yek_masire;

    @BindView(R.id.spinner)
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gozineha);
        ButterKnife.bind(this);

        // get intent
        final int DestinationNumber = getIntent().getIntExtra("DestinationNumber", 0);

        // set default
        setSelect_boxYekMasire();

        // set onclick
        select_box_yek_masire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DestinationNumber == 1) {
                    setSelect_boxYekMasire();
                }
            }
        });

        select_box_rafto_bargasht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DestinationNumber == 1) {
                    selectBoxRaftoBargasht();
                }else {
                    Toast.makeText(GozinehaActivity.this, "متاسفم! چند مسیره فقط درصورت انتخاب یک مقصد میتوان فعال باشد.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //
        BookService service = ServiceGenerator.createService(BookService.class);
        service.getStopTime().enqueue(new Callback<GetStopTimeResponseJson>() {
            @Override
            public void onResponse(Call<GetStopTimeResponseJson> call, Response<GetStopTimeResponseJson> response) {
                Log.i("www", "onResponse: getStopTime:");

                if (response.isSuccessful()){
                    setSpinner(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GetStopTimeResponseJson> call, Throwable t) {
                Log.i("www", "onFailure: getStopTime:");
            }
        });
    }

    private void setSpinner(List<GetStopTime> response) {
        ArrayList<String> premiums = new ArrayList<String>();
        for (GetStopTime row: response) {
            premiums.add(row.getTimePremium());
        }



//        insuranceList.add("Rp 0, Insurance up to Rp 0");

        ArrayAdapter premiumAdapter = new ArrayAdapter<String>(this, R.layout.item_selected_spinner_center, premiums);
        premiumAdapter.setDropDownViewResource(R.layout.item_selected_spinner_center);
        spinner.setAdapter(premiumAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String title = spinner.getSelectedItem().toString();
                //ProductTypeItemSelected = hashMap.get(title);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void selectBoxRaftoBargasht() {
        select_box_rafto_bargasht.setSelected(true);
        txt_box_rafto_bargasht.setSelected(true);
        select_box_yek_masire.setSelected(false);
        txt_box_yek_masire.setSelected(false);
    }

    public void setSelect_boxYekMasire() {
        select_box_rafto_bargasht.setSelected(false);
        txt_box_rafto_bargasht.setSelected(false);
        select_box_yek_masire.setSelected(true);
        txt_box_yek_masire.setSelected(true);
    }
}
