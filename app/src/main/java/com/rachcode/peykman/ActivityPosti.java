package com.rachcode.peykman;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPosti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posti);
        ButterKnife.bind(this);


        //
        ConstraintLayout designHeaderPeyment = findViewById(R.id.designHeaderPeyment);
        ConstraintLayout designHeaderDes = findViewById(R.id.designHeaderDes);
        ConstraintLayout designHeaderPik = findViewById(R.id.designHeaderPik);


        ///
        final ConstraintLayout design_peyment = findViewById(R.id.design_peyment);
        final ConstraintLayout design_des = findViewById(R.id.design_des);
        final ConstraintLayout design_pickup = findViewById(R.id.design_pickup);



        designHeaderPeyment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                design_peyment.setVisibility(View.VISIBLE);
                design_des.setVisibility(View.GONE);
                design_pickup.setVisibility(View.GONE);
            }
        });

        designHeaderDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                design_peyment.setVisibility(View.GONE);
                design_des.setVisibility(View.VISIBLE);
                design_pickup.setVisibility(View.GONE);
            }
        });

        designHeaderPik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                design_peyment.setVisibility(View.GONE);
                design_des.setVisibility(View.GONE);
                design_pickup.setVisibility(View.VISIBLE);
            }
        });



        /////////
        select_box_naghdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxNaghdi();
            }
        });
        select_box_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxOnline();
            }
        });


        //
        select_box_ba_byme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxBaByme();
            }
        });
        select_box_no_byme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBoxNoByme();
            }
        });


        //
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


    }

    @BindView(R.id.select_box_vije)
    LinearLayout select_box_vije;
    @BindView(R.id.icon_box_vije)
    ImageView icon_box_vije;
    @BindView(R.id.btn_logo)
    ImageView btn_logo;
    @BindView(R.id.txt_box_vije)
    TextView txt_box_vije;

    @BindView(R.id.select_box_normal)
    LinearLayout select_box_normal;
    @BindView(R.id.icon_box_normal)
    ImageView icon_box_normal;
    @BindView(R.id.txt_box_normal)
    TextView txt_box_normal;

    /**
     * box type peyment
     **/
    @BindView(R.id.select_box_ba_byme)
    LinearLayout select_box_ba_byme;
    @BindView(R.id.txt_box_ba_byme)
    TextView txt_box_ba_byme;

    @BindView(R.id.select_box_no_byme)
    LinearLayout select_box_no_byme;
    @BindView(R.id.txt_box_no_byme)
    TextView txt_box_no_byme;


    public void selectBoxBaByme() {
        select_box_ba_byme.setSelected(true);
        txt_box_ba_byme.setSelected(true);
        select_box_no_byme.setSelected(false);
        txt_box_no_byme.setSelected(false);
    }

    public void selectBoxNoByme() {
        select_box_ba_byme.setSelected(false);
        txt_box_ba_byme.setSelected(false);
        select_box_no_byme.setSelected(true);
        txt_box_no_byme.setSelected(true);
    }





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
    }

    public void selectBoxOnline() {
        select_box_naghdi.setSelected(false);
        txt_box_naghdi.setSelected(false);
        icon_box_naghdi.setSelected(false);
        select_box_online.setSelected(true);
        txt_box_online.setSelected(true);
        icon_box_online.setSelected(true);
    }





    @BindView(R.id.Edescription)
    EditText Edescription_pik;

    public void selectBoxVije() {
        select_box_vije.setSelected(true);
        txt_box_vije.setSelected(true);
        icon_box_vije.setSelected(true);
        select_box_normal.setSelected(false);
        txt_box_normal.setSelected(false);
        icon_box_normal.setSelected(false);
        Edescription_pik.setVisibility(View.VISIBLE);
    }

    public void selectBoxNormal() {
        select_box_vije.setSelected(false);
        txt_box_vije.setSelected(false);
        icon_box_vije.setSelected(false);
        select_box_normal.setSelected(true);
        txt_box_normal.setSelected(true);
        icon_box_normal.setSelected(true);
        Edescription_pik.setVisibility(View.GONE);

    }

}
