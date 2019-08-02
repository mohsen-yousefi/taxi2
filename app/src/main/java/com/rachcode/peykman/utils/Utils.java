package com.rachcode.peykman.utils;

import android.content.Context;

import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.model.RateDriverS;
import com.rachcode.peykman.model.UserData;

import io.realm.Realm;

/**
 * Created by Androgo on 12/23/2018.
 */

public class Utils {

    private Utils() {

    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static void saveUser(Context context, UserData user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(UserData.class);
        realm.copyToRealm(user);
        realm.commitTransaction();

        GoTaxiApplication.getInstance(context).setLoginUserD(user);
    }

    public static void saveRateDriverS(Context context, RateDriverS rateDriverS) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(RateDriverS.class);
        realm.copyToRealm(rateDriverS);
        realm.commitTransaction();

        GoTaxiApplication.getInstance(context).setRateDriverS(rateDriverS);
    }

    public static void removeRateDriverS(Context context) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(RateDriverS.class);
        realm.commitTransaction();
     }

}
