package com.rachcode.peykman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GozinehaActivity extends AppCompatActivity {

    @BindView(R.id.select_box_rafto_bargasht)
    LinearLayout select_box_rafto_bargasht;
    @BindView(R.id.select_box_yek_masire)
    LinearLayout select_box_yek_masire;

    @BindView(R.id.txt_box_rafto_bargasht)
    TextView txt_box_rafto_bargasht;
    @BindView(R.id.txt_box_yek_masire)
    TextView txt_box_yek_masire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gozineha);
        ButterKnife.bind(this);

        select_box_yek_masire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect_boxYekMasire();
            }
        });

        select_box_rafto_bargasht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxRaftoBargasht();
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
