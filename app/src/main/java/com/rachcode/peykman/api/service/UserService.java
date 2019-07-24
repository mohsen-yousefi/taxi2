package com.rachcode.peykman.api.service;

import com.google.android.gms.maps.model.LatLng;
import com.rachcode.peykman.model.Status;
import com.rachcode.peykman.model.json.book.AddFavoriteAddressResponseJson;
import com.rachcode.peykman.model.json.book.GetDriverLatLongResponseJson;
import com.rachcode.peykman.model.json.book.GetFavoriteAddressResponseJson;
import com.rachcode.peykman.model.json.book.RateDriverRequestJson;
import com.rachcode.peykman.model.json.book.RateDriverResponseJson;
import com.rachcode.peykman.model.json.fcm.CancelBookRequestJson;
import com.rachcode.peykman.model.json.fcm.CancelBookResponseJson;
import com.rachcode.peykman.model.json.menu.HelpRequestJson;
import com.rachcode.peykman.model.json.menu.HelpResponseJson;
import com.rachcode.peykman.model.json.menu.HistoryRequestJson;
import com.rachcode.peykman.model.json.menu.HistoryResponseJson;
import com.rachcode.peykman.model.json.menu.VersionRequestJson;
import com.rachcode.peykman.model.json.menu.VersionResponseJson;
import com.rachcode.peykman.model.json.user.ChangePasswordRequestJson;
import com.rachcode.peykman.model.json.user.ChangePasswordResponseJson;
import com.rachcode.peykman.model.json.user.CheangePayResponse;
import com.rachcode.peykman.model.json.user.GetBannerResponseJson;
import com.rachcode.peykman.model.json.user.GetFiturResponseJson;
import com.rachcode.peykman.model.json.user.GetSaldoRequestJson;
import com.rachcode.peykman.model.json.user.GetSaldoResponseJson;
import com.rachcode.peykman.model.json.user.LoginRequestJson;
import com.rachcode.peykman.model.json.user.LoginResponseJson;
import com.rachcode.peykman.model.json.user.PulsaRequestJson;
import com.rachcode.peykman.model.json.user.PulsaResponseJson;
import com.rachcode.peykman.model.json.user.RegisterResponseJson;
import com.rachcode.peykman.model.json.user.TopupRequestJson;
import com.rachcode.peykman.model.json.user.TopupResponseJson;
import com.rachcode.peykman.model.json.user.UpdateProfileRequestJson;
import com.rachcode.peykman.model.json.user.UpdateProfileResponseJson;
import com.rachcode.peykman.model.json.user.UserDataResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Androgo on 10/13/2018.
 */

public interface UserService {

    /*MY APP*/
    @FormUrlEncoded
    @POST("Activation/add")
    Call<Status> activationAdd(@Field("user_phone") String user_phone);


    @FormUrlEncoded
    @POST("Activation/check")
    Call<UserDataResponseJson> activationCheck(@Field("user_phone") String user_phone, @Field("active_code") String active_code, @Field("reg_id") String reg_id);

    @FormUrlEncoded
    @POST("customer/profile")
    Call<UserDataResponseJson> getUserData(@Field("phone") String user_phone);

    /*END*/

    @POST("customer/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @FormUrlEncoded
    @POST("customer/register_user")
    Call<RegisterResponseJson> register(@Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("reg_id") String reg_id,
                                        @Field("phone") String phone);
    @FormUrlEncoded
    @POST("Book/change_payment_type_by_customer")
    Call<CheangePayResponse> cheangePayment(@Field("transaction_id") String transaction_id,
                                      @Field("value") String value);




    @POST("pelanggan/get_saldo")
    Call<GetSaldoResponseJson> getSaldo(@Body GetSaldoRequestJson param);

    @GET("customer/feature_details")
    Call<GetFiturResponseJson> getFitur();

    @POST("pelanggan/user_send_help")
    Call<HelpResponseJson> sendHelp(@Body HelpRequestJson param);

    @POST("customer/update_profile")
    Call<UpdateProfileResponseJson> updateProfile(@Body UpdateProfileRequestJson param);

    @POST("pelanggan/change_password")
    Call<ChangePasswordResponseJson> changePassword(@Body ChangePasswordRequestJson param);

    @POST("book/user_cancel_transaction")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("customer/check_version")
    Call<VersionResponseJson> checkVersion(@Body VersionRequestJson param);

    @POST("book/user_rate_driver")
    Call<RateDriverResponseJson> rateDriver(@Body RateDriverRequestJson param);

    @FormUrlEncoded
    @POST("book/get_favorite_addresses")
    Call<GetFavoriteAddressResponseJson> getFavoriteAddress(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("Driver/get_one_driver_lat_long")
    Call<GetDriverLatLongResponseJson> getDriverLtLong(@Field("driver_id") String driver_id);

    @FormUrlEncoded
    @POST("book/insert_favorite_addresses")
    Call<AddFavoriteAddressResponseJson> insert_favorite(@Field("address") String address, @Field("customer_id") String customer_id, @Field("latitude") Double latitude, @Field("longitude") Double longitude);

    @POST("pelanggan/verifikasi_topup")
    Call<TopupResponseJson> topUp(@Body TopupRequestJson param);

    @POST("pelanggan/verifikasi_isipulsa")
    Call<PulsaResponseJson> isipulsa(@Body PulsaRequestJson param);

    @POST("pelanggan/complete_transaksi")
    Call<HistoryResponseJson> getCompleteHistory(@Body HistoryRequestJson param);

    @POST("pelanggan/inprogress_transaksi")
    Call<HistoryResponseJson> getOnProgressHistory(@Body HistoryRequestJson param);
    @GET("customer/ads_banner")
    Call<GetBannerResponseJson> getBanner();

}
