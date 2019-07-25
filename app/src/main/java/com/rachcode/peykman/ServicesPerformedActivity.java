package com.rachcode.peykman;

import android.content.Context;
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

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.rachcode.peykman.adapter.HistoryAdapter;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.home.submenu.home.HomeFragment;
import com.rachcode.peykman.model.ItemHistory;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.menu.HistoryRequestJson;
import com.rachcode.peykman.model.json.menu.HistoryResponseJson;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.Log;
import com.rachcode.peykman.utils.MenuSelector;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

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

        UserService service = ServiceGenerator.createService(UserService.class, user.getEmail(), user.getPassword());
        service.getCompleteHistory(request).enqueue(new Callback<HistoryResponseJson>() {
            @Override
            public void onResponse(Call<HistoryResponseJson> call, Response<HistoryResponseJson> response) {
                if (response.isSuccessful()) {
                    ArrayList<ItemHistory> data = response.body().data;

                    recyclerView.setVisibility(View.VISIBLE);

                    for (int i = 0; i < data.size(); i++) {
                        switch (data.get(i).order_feature) {
                        /*    case "Go-Moto":
                                data.get(i).image_id = R.drawable.ride;
                                break;
                            case "Go-Cab":
                                data.get(i).image_id = R.drawable.car;
                                break;*/
                            case "smotor":
                                data.get(i).image_id = R.drawable.send;
                                break;
                            case "scar":
                                data.get(i).image_id = R.drawable.car;
                                break;
                            case "svanet":
                                data.get(i).image_id = R.drawable.ic_mbox;
                                break;
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
/*
                    recyclerView.setAdapter(ServicesPerformedActivity.this,data);
*/
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

    class Adapter extends RecyclerView.Adapter<ViewHolder>{
        private ArrayList<ItemHistory> mDataSet;
        private Context context;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ServicesPerformedActivity.this).inflate(R.layout.row_services_performed,parent,false);
            return new ViewHolder(v);
        }
        public Adapter(Context context,ArrayList<ItemHistory> data){
            this.mDataSet = data;
            this.context = context;

        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
