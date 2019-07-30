package com.rachcode.peykman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import com.rachcode.peykman.model.CountData;
import com.rachcode.peykman.model.ItemHistory;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.menu.HistoryRequestJson;
import com.rachcode.peykman.model.json.menu.HistoryResponseJson;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.MenuSelector;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesPerformedActivity extends AppCompatActivity {

    @BindView(R.id.main_tabLayout)
    SmartTabLayout mainTabLayout;
    @BindView(R.id.txtCountServicePeyk)
    TextView txtCountServicePeyk;

    @BindView(R.id.txtCountServicePost)
    TextView txtCountServicePost;
    @BindView(R.id.txtCountServiceFood)
    TextView txtCountServiceFood;
    @BindView(R.id.txtCountServiceStore)
    TextView txtCountServiceStore;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    DrawerLayout drawer;
    ConstraintLayout design_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_performed);
        ButterKnife.bind(this);
        requestData();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setupTabLayoutViewPager();
        bottomNav();
    }



    private void requestData() {
        UserData user = GoTaxiApplication.getInstance(this).getLoginUserD();
        HistoryRequestJson request = new HistoryRequestJson();
        request.id = user.getId();

        android.util.Log.i("www", "requestData: user_id:" + request.id);
        UserService service = ServiceGenerator.createService(UserService.class, user.getEmail(), user.getPassword());
        service.getCompleteHistory(request).enqueue(new Callback<HistoryResponseJson>() {
            @Override
            public void onResponse(Call<HistoryResponseJson> call, Response<HistoryResponseJson> response) {
                JSONArray j = new JSONArray();
                j.put(response.body().data);
                android.util.Log.i("www", "onResponse123: " + j);
                if (response.isSuccessful()) {
                    ArrayList<ItemHistory> data = response.body().data;
                    ArrayList<CountData> countData = response.body().count_data;

                    recyclerView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < countData.size(); i++) {
                        switch (countData.get(i).driver_job) {
                            case "peyk":
                                txtCountServicePeyk.setText(countData.get(i).COUNT+" سرویس");
                                break;
                                case "post":
                                    txtCountServicePost.setText(countData.get(i).COUNT+" سرویس");
                                break;
                                case "food":
                                    txtCountServiceFood.setText(countData.get(i).COUNT+" سرویس");
                                break;
                                case "shop":
                                    txtCountServiceStore.setText(countData.get(i).COUNT+" سرویس");
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

    private void setupTabLayoutViewPager() {
        GoTaxiTabProvider tabProvider = new GoTaxiTabProvider(this);
        final MenuSelector selector = (MenuSelector) tabProvider;
        mainTabLayout.setCustomTabView(tabProvider);

        /*adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.main_menuHome, HomeFragment.class)
                .add(R.string.main_menuHistory, HistoryFragment.class)
                .add(R.string.main_menuHelp, HelpFragment.class)
                .add(R.string.main_menuSetting, SettingFragment.class)
                .create());*/


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

                // remove shodow
                drawer.setScrimColor(Color.TRANSPARENT);
                drawer.setDrawerElevation(0);

                // bottom nav item click
                if (position == 0) {
                    design_wallet.setVisibility(View.VISIBLE);
                    drawer.closeDrawers();
                } else if (position == 1) {
                    design_wallet.setVisibility(View.GONE);
                    drawer.closeDrawers();
                } else if (position == 2) {
                    drawer.openDrawer(Gravity.RIGHT);
                    //design_wallet.setVisibility(View.GONE);
                }
            }
        });

    }

    private void bottomNav() {
        drawer = findViewById(R.id.drawer);
        design_wallet = findViewById(R.id.design_wallet);
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
            holder.origin_address.setText("مبدا:" + mDataSet.get(position).origin_address);
            holder.destination_address.setText("مقصد:" + mDataSet.get(position).destination_address);
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
