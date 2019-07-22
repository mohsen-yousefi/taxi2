package com.rachcode.peykman.api;

import com.rachcode.peykman.signUp.beans.CountryList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Androgo on 06 Sep 2018
 */

public interface ApiCountryCode {


    @GET("apicountry/list_country.json")
    Call<CountryList> getMyJSON();
}