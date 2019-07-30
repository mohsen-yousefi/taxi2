package com.rachcode.peykman.home;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rachcode.peykman.ActivityAbout;
import com.rachcode.peykman.ActivityGardeshHesab;
import com.rachcode.peykman.ActivityProfile;
import com.rachcode.peykman.FragmentSetting;
import com.rachcode.peykman.FragmentWallet;
import com.rachcode.peykman.ServicesPerformedActivity;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.model.DiscountMpay;
import com.rachcode.peykman.model.MfoodPartner;
import com.rachcode.peykman.model.UserData;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.home.submenu.home.HomeFragment;
import com.rachcode.peykman.model.Fitur;
import com.rachcode.peykman.model.json.user.GetFiturResponseJson;
import com.rachcode.peykman.model.json.user.UserDataResponseJson;
import com.rachcode.peykman.utils.GoTaxiTabProvider;
import com.rachcode.peykman.utils.MenuSelector;
import com.rachcode.peykman.utils.SnackbarController;
import com.rachcode.peykman.utils.Utils;
import com.rachcode.peykman.utils.view.CustomViewPager;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Androgo on 10/10/2018.
 */

public class MainActivity extends AppCompatActivity implements SnackbarController {

    @BindView(R.id.main_container)
    LinearLayout mainLayout;
    UserData loginUser;
    @BindView(R.id.main_tabLayout)
    SmartTabLayout mainTabLayout;
    @BindView(R.id.textView8)
    TextView textView8;
    private Bitmap bitmap;

    @BindView(R.id.main_viewPager)
    CustomViewPager mainViewPager;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.txtBalance)
    TextView txtBalance;
    boolean doubleBackToExitPressedOnce = false;
    private Snackbar snackBar;
    private MenuSelector selector;
    private SmartTabLayout.TabProvider tabProvider;
    private FragmentPagerItemAdapter adapter;
    private Locale locale;
    private Toolbar toolbar;
    @BindView(R.id.imageView21)
    de.hdodenhof.circleimageview.CircleImageView profile_image;

    int itemSelectedBottomNavigation = 0;
    DrawerLayout drwable;

    private void setNavigitionView() {
        drwable = findViewById(R.id.drwable);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drwable,toolbar,R.string.s1,R.string.s2);
        drawerToggle.syncState();
        //drwable.setDrawerListener(drawerToggle);


        // set button nav
        ConstraintLayout about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityAbout.class));
                //finish();
            }
        });


        ConstraintLayout profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityProfile.class));
             }
        });


        // set button nav
        ConstraintLayout gardeshHsab = findViewById(R.id.gardesh_hesab);
        gardeshHsab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityGardeshHesab.class));
            }
        });

        ConstraintLayout servicesPerformed = findViewById(R.id.khedemat_anjam_shode);
        servicesPerformed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ServicesPerformedActivity.class));
            }
        });

        LinearLayout wallet_increase = findViewById(R.id.wallet_increase);
        wallet_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.selectMenu(0);
                mainViewPager.setCurrentItem(0);
                drwable.closeDrawers();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupTabLayoutViewPager();
        setNavigitionView();


        ImageView logo = findViewById(R.id.imageView7);
        logo.setImageResource(R.drawable.ic_logo);

        if (General.ENABLE_RTL_MODE) {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }*/


            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selector.selectMenu(0);

                }
            });

        }
        updateFeature();

/*
        // change language
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = settings.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        findViewById(R.id.languange).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showChangeLangDialog();
            }
        });
*/
        loadImageFromStorage();
    }

/*
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle(getResources().getString(R.string.lang_dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.lang_dialog_message));
        dialogBuilder.setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                changeLang(langpos);
            }
        });
        dialogBuilder.setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }*/

/*
    void changeLang(int langpos){
        switch (langpos) {
            case 0: //Arabic
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "ar").commit();
                setLangRecreate("ar");

                return;
            case 1: //Spanish
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "es").commit();
                setLangRecreate("es");
                return;
            case 2: //French
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "fr").commit();
                setLangRecreate("fr");
                return;
            case 3: //Portuguese
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "pt").commit();
                setLangRecreate("pt");
                return;
            case 4: //Indonesian
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "in").commit();
                setLangRecreate("in");
                return;
            default: //By default set to english
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                setLangRecreate("en");
                return;
        }
    }
*/

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


        // tab home
        if (drwable.isDrawerOpen(Gravity.RIGHT)) drwable.closeDrawers();
        mainViewPager.setCurrentItem(1);
        mainTabLayout.getTabAt(1).setSelected(true);
        selector.selectMenu(1);
        itemSelectedBottomNavigation = 1;
    }


    private void setupTabLayoutViewPager() {
        tabProvider = new GoTaxiTabProvider(this);
        selector = (MenuSelector) tabProvider;
        mainTabLayout.setCustomTabView(tabProvider);




        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("", FragmentWallet.class)
                .add("", HomeFragment.class)
                .add("", FragmentSetting.class)
                .create());


        mainViewPager.setAdapter(adapter);
        mainTabLayout.setViewPager(mainViewPager);
        //mainViewPager.setPagingEnabled(false);

        mainTabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                selector.selectMenu(position);
            }
        });


        // TODO MMD ++
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2){
                    drwable.openDrawer(Gravity.RIGHT);

                    if (itemSelectedBottomNavigation == 0){
                        mainViewPager.setCurrentItem(0);
                        mainTabLayout.getTabAt(0).setSelected(true);
                        selector.selectMenu(0);
                    }else if (itemSelectedBottomNavigation == 1){
                        mainViewPager.setCurrentItem(1);
                        mainTabLayout.getTabAt(1).setSelected(true);
                        selector.selectMenu(1);
                    }

                }else {
                    itemSelectedBottomNavigation = i;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        // set default
        mainViewPager.setCurrentItem(1);
        mainTabLayout.getTabAt(1).setSelected(true);
        selector.selectMenu(1);
        itemSelectedBottomNavigation = 1;


    }


    /*private void setupTabLayoutViewPager() {
        BottomNavigationViewEx main_tabLayout = (BottomNavigationViewEx) findViewById(R.id.main_tabLayout);
        main_tabLayout.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_wallet:
                        startFragment(new FragmentWallet());
                        break;
                    case R.id.ic_home_bt:
                        startFragment(new HomeFragment());
                        break;
                    case R.id.ic_settings:
                        break;
                }

                return true;
            }
        });
    }*/

    private void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("");
        fragmentTransaction.add(R.id.frame_container, fragment).commit();
    }

    private void loadImageFromStorage() {

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("photoDriver", Context.MODE_PRIVATE);
            File f = new File(directory, "profile.jpg");
            Bitmap tryDec = decodeFile(f.toString(),200);
        profile_image.setImageBitmap(tryDec);

    }
    private Bitmap decodeFile(final String path, final int thumbnailSize) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, o);
        if ((o.outWidth == -1) || (o.outHeight == -1)) {
            bitmap = null;
        }

        int originalSize = (o.outHeight > o.outWidth) ? o.outHeight
                : o.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }
    @Override
    protected void onResume() {
        super.onResume();

        updateMPayBalance();
    }

    @Override
    public void showSnackbar(@StringRes int stringRes, int duration, @StringRes int actionResText, View.OnClickListener onClickListener) {
        snackBar = Snackbar.make(mainLayout, stringRes, duration);
        if (actionResText != -1 && onClickListener != null) {
            snackBar.setAction(actionResText, onClickListener)
                    .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        snackBar.show();
    }
    private void updateMPayBalance() {
        UserService userService = ServiceGenerator.createService(UserService.class);

         userService.getUserData(String.valueOf(loginUser.getPhone())).enqueue(new Callback<UserDataResponseJson>() {
            @Override
            public void onResponse(Call<UserDataResponseJson> call, Response<UserDataResponseJson> response) {
                UserDataResponseJson responseUser = response.body();
                if (responseUser.getStatus().equals("success")) {
                    UserData user = response.body().getData().get(0);
                    Utils.saveUser(getApplication(),user);
                    updateFeature();

                }
            }

            @Override
            public void onFailure(Call<UserDataResponseJson> call, Throwable t) {

            }
        });

    }
    private void updateFeature() {
        loadImageFromStorage();
          loginUser = GoTaxiApplication.getInstance(this).getLoginUserD();
        UserService userService = ServiceGenerator.createService(UserService.class,
                loginUser.getEmail(), loginUser.getPassword());

        Log.i("www", "updateFeatureParam: username:"+loginUser.getEmail()+" pass:"+ loginUser.getPassword());
        userService.getFitur().enqueue(new Callback<GetFiturResponseJson>() {
            @Override
            public void onResponse(Call<GetFiturResponseJson> call, Response<GetFiturResponseJson> response) {
                if (response.isSuccessful()) {
                    Realm realm = GoTaxiApplication.getInstance(MainActivity.this).getRealmInstance();
                    realm.beginTransaction();
                    realm.delete(Fitur.class);
                    realm.copyToRealm(response.body().getData());
                    Log.i("wwww", "onResponseFitur_MSendMotor(9): "+response.body().getData().get(9).getFinalPrice());
                    realm.commitTransaction();

                    DiscountMpay diskonMpay = response.body().getDiscountMpay();
                    realm.beginTransaction();
                    realm.delete(DiscountMpay.class);
                    realm.copyToRealm(response.body().getDiscountMpay());
                    realm.commitTransaction();
                    GoTaxiApplication.getInstance(MainActivity.this).setDiskonMpay(diskonMpay);

                    MfoodPartner mfoodMitra = response.body().getMfoodPartner();
                    realm.beginTransaction();
                    realm.delete(MfoodPartner.class);
                    realm.copyToRealm(response.body().getMfoodPartner());
                    realm.commitTransaction();
                    GoTaxiApplication.getInstance(MainActivity.this).setMfoodMitra(mfoodMitra);

                    txtBalance.setText(formatMony(Integer.valueOf(loginUser.getBalance())));
                    textView8.setText(loginUser.getFirstName()+" "+loginUser.getLastName());

                }
            }

            @Override
            public void onFailure(Call<GetFiturResponseJson> call, Throwable t) {

            }
        });
    }
    public String formatMony(Long price){
        String formattedText = price + " " + General.MONEY;

        return  formattedText;
    }
    public String formatMony(int price){
        String formattedText = price + " " + General.MONEY;

        return  formattedText;
    }
}
