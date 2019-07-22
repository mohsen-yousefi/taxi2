package com.rachcode.peykman;

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
import com.rachcode.peykman.home.submenu.home.HomeFragment;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.MenuSelector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesPerformedActivity extends AppCompatActivity {

    @BindView(R.id.main_tabLayout)
    SmartTabLayout mainTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_performed);
        ButterKnife.bind(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new Adapter());

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



    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ServicesPerformedActivity.this).inflate(R.layout.row_services_performed,parent,false);
            return new ViewHolder(v);
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
