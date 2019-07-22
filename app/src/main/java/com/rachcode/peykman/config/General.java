package com.rachcode.peykman.config;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rachcode.peykman.R;


public class General {
    //please insert Server_KEY CLOUD_MESSAGING
    public static final String FCM_KEY = "AAAAQAgP5oI:APA91bGPeEGIIXXQaqVQIhUVhJrVAL5b0SEYh_TADaQZKkZUwDSeuV_y1KNvo5MS-nq8KmbrNQr6wNryhKZu3GcL5mJvroIg_-XBfpw33rVs0UrFET9YMvhNMbuODLuXnjtYUOfwbHpq";
    public static final LatLngBounds BOUNDS = new LatLngBounds(
            new LatLng(28.9233837, 50.82031400000005),
            new  LatLng(28.9233837, 50.82031400000005));


    // Currency settings
    public static final String MONEY = "تومان";

    //number sos
    public static final String NUMBER_SOS = "999";

    //if you use RTL Language e.g : Arabic Language or other, set true
    public static final boolean ENABLE_RTL_MODE = false;

    // if you use distance in KM then
    public static final String UNIT_OF_DISTANCE = "Km"; //if you use km or miles
    public static final Float RANGE_VALUE = 1000f; //if using km (1000f) or Miles using 1609f

    //Setting menu names on Home
    public static final String Name_GOCAB = "GO-CAB";
    public static final String Name_GOMOTO = "GO-MOTO";
    public static final String Name_GOSEND = "GO-SEND";
    public static final String Name_GOFOOD = "GO-FOOD";
    public static final String Name_GOMART = "GO-MART";
    public static final String Name_GOMASSAGE = "GO-MASSAGE";
    public static final String Name_GOBOX = "GO-BOX";
    public static final String Name_GOSERVICE = "GO-SERVICE";


}
