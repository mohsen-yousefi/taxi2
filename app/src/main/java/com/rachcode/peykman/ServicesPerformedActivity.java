package com.rachcode.peykman;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.rachcode.peykman.adapter.HistoryAdapter;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.submenu.home.HomeFragment;
import com.rachcode.peykman.model.ItemHistory;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.menu.HistoryRequestJson;
import com.rachcode.peykman.model.json.menu.HistoryResponseJson;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.MenuSelector;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesPerformedActivity extends AppCompatActivity {

    @BindView(R.id.main_tabLayout)
    SmartTabLayout mainTabLayout;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_performed);
        ButterKnife.bind(this);
        requestData();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setupTabLayoutViewPager();
    }

    private void setupTabLayoutViewPager() {
        GoTaxiTabProvider tabProvider = new GoTaxiTabProvider(this);
        final MenuSelector selector = (MenuSelector) tabProvider;
        mainTabLayout.setCustomTabView(tabProvider);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("", Fragment.class)
                .add("", Fragment.class)
                .add("", Fragment.class)
                .create());


        ViewPager viewPager = new ViewPager(getApplicationContext());
        viewPager.setAdapter(adapter);
        mainTabLayout.setViewPager(viewPager);
        //mainViewPager.setPagingEnabled(false);

        mainTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                selector.selectMenu(position);
            }
        });

    }


    private void requestData() {
        UserData user = GoTaxiApplication.getInstance(this).getLoginUserD();
        HistoryRequestJson request = new HistoryRequestJson();
        request.id = user.getId();

        android.util.Log.i("www", "requestData: user_id:"+request.id);
        UserService service = ServiceGenerator.createService(UserService.class, user.getEmail(), user.getPassword());
        service.getCompleteHistory(request).enqueue(new Callback<HistoryResponseJson>() {
            @Override
            public void onResponse(Call<HistoryResponseJson> call, Response<HistoryResponseJson> response) {
                JSONArray j = new JSONArray();
                j.put(response.body().data);
                android.util.Log.i("www", "onResponse123: "+j);
                if (response.isSuccessful()) {
                    ArrayList<ItemHistory> data = response.body().data;

                    recyclerView.setVisibility(View.VISIBLE);

                    for (int i = 0; i < data.size(); i++) {
                        android.util.Log.i("HISTORY", "color: " + data.get(i).color);

                        switch (data.get(i).order_feature) {
                        /*    case "Go-Moto":
                                data.get(i).image_id = R.drawable.ride;
                                break;
                            case "Go-Cab":
                                data.get(i).image_id = R.drawable.car;
                                break;*/

                           /* case "GO-Service":
                                data.get(i).image_id = R.drawable.ic_mservice;
                                break;
                            case "Go-Massage":
                                data.get(i).image_id = R.drawable.massage;
                                break;
                            case "Go-Food":
                                data.get(i).image_id = R.drawable.ic_mfood;
                                break;*/

                            default:
                                data.get(i).image_id = R.drawable.car;
                                break;
                        }
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(ServicesPerformedActivity.this);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(new Adapter(ServicesPerformedActivity.this, data));

                    if (response.body().data.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        Log.d("HISTORY", "Empty");
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryResponseJson> call, Throwable t) {
                t.printStackTrace();
//                Toast.makeText(getActivity(), "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("System error:", t.getLocalizedMessage());
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<ItemHistory> mDataSet;
        private Context context;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ServicesPerformedActivity.this).inflate(R.layout.row_services_performed, parent, false);
            return new ViewHolder(v);
        }

        public Adapter(Context context, ArrayList<ItemHistory> data) {
            this.mDataSet = data;
            this.context = context;

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            switch (mDataSet.get(position).order_feature) {
                case "Send-Motor":
                    holder.transActionType.setText("سرویس پیک");
                    break;
                case "Send-Car":
                    holder.transActionType.setText("سرویس پیک ماشین");
                    break;
                case "Send-Vanet":
                    holder.transActionType.setText("سرویس پیک وانت");
                    break;
            }
            holder.origin_address.setText("مبدا:"+mDataSet.get(position).origin_address);
            holder.destination_address.setText("مقصد:"+mDataSet.get(position).destination_address);
            holder.price.setText(formatMony(String.valueOf(mDataSet.get(position).price)));
            String[] date = mDataSet.get(position).order_start_time.split("-");
            holder.date.setText(date[0]);
            holder.time.setText(date[1]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemHistory itemHistory = mDataSet.get(position);
                    Intent intent = new Intent(getApplication(), ActivityTransactionDetail.class);
                    intent.putExtra("itemHistory", itemHistory);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView transActionType;
        TextView origin_address;
        TextView destination_address;
        TextView price;
        TextView date;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            transActionType = itemView.findViewById(R.id.textView49);
            origin_address = itemView.findViewById(R.id.textView54);
            destination_address = itemView.findViewById(R.id.textView56);
            price = itemView.findViewById(R.id.textView57);
            date = itemView.findViewById(R.id.textView51);
            time = itemView.findViewById(R.id.textView53);

        }
    }

    public String formatMony(String price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }
}
