package com.rachcode.peykman;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.iid.FirebaseInstanceId;

import com.rachcode.peykman.model.DiscountMpay;
import com.rachcode.peykman.model.FirebaseToken;
import com.rachcode.peykman.model.MfoodPartner;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Androgo on 10/13/2018.
 */

public class GoTaxiApplication extends Application {

    private static final int SCHEMA_VERSION = 0;

    private static final String TAG = "GoTaxiApplication";

    private User loginUser;
    private UserData loginUserD;

    private Realm realmInstance;

    private DiscountMpay diskonMpay;

    private MfoodPartner mfoodMitra;

    public static GoTaxiApplication getInstance(Context context) {
        return (GoTaxiApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();

        FirebaseToken token = new FirebaseToken(FirebaseInstanceId.getInstance().getToken());

        Realm.setDefaultConfiguration(config);

//       realmInstance = Realm.getInstance(config);
        realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        realmInstance.delete(FirebaseToken.class);
        realmInstance.copyToRealm(token);
        realmInstance.commitTransaction();

        start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public User getLoginUser() {
        return loginUser;
    }
    public UserData getLoginUserD() {
        return loginUserD;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
    public void setLoginUserD(UserData loginUser) {
        this.loginUserD = loginUser;
    }

    public final Realm getRealmInstance() {
        return realmInstance;
    }

    private void start() {
        Realm realm = getRealmInstance();
        UserData user = realm.where(UserData.class).findFirst();
        if (user != null) {
            setLoginUserD(user);
        }
    }

    public DiscountMpay getDiskonMpay() {
        return diskonMpay;
    }

    public void setDiskonMpay(DiscountMpay diskonMpay) {
        this.diskonMpay = diskonMpay;
    }

    public MfoodPartner getMfoodMitra() {
        return mfoodMitra;
    }

    public void setMfoodMitra(MfoodPartner mfoodMitra) {
        this.mfoodMitra = mfoodMitra;
    }


}
