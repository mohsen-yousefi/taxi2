package com.rachcode.peykman.api.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.json.GetNearRideDriverResponseJson;
import com.rachcode.peykman.model.json.book.CheckStatusTransaksiRequest;
import com.rachcode.peykman.model.json.book.CheckStatusTransaksiResponse;
import com.rachcode.peykman.model.json.book.GetAdditionalMboxResponseJson;
import com.rachcode.peykman.model.json.book.GetDataMserviceResponseJson;
import com.rachcode.peykman.model.json.book.GetDataPulsaResponseJson;
import com.rachcode.peykman.model.json.book.GetDataRestoByKategoriRequestJson;
import com.rachcode.peykman.model.json.book.GetDataRestoByKategoriResponseJson;
import com.rachcode.peykman.model.json.book.GetDataRestoRequestJson;
import com.rachcode.peykman.model.json.book.GetDataRestoResponseJson;
import com.rachcode.peykman.model.json.book.GetFoodRestoRequestJson;
import com.rachcode.peykman.model.json.book.GetFoodRestoResponseJson;
import com.rachcode.peykman.model.json.book.GetKendaraanAngkutResponseJson;
import com.rachcode.peykman.model.json.book.GetNearBoxRequestJson;
import com.rachcode.peykman.model.json.book.GetNearBoxResponseJson;
import com.rachcode.peykman.model.json.book.GetNearRideCarRequestJson;
import com.rachcode.peykman.model.json.book.GetNearRideCarResponseJson;
import com.rachcode.peykman.model.json.book.GetNearServiceRequestJson;
import com.rachcode.peykman.model.json.book.GetNearServiceResponseJson;
import com.rachcode.peykman.model.json.book.GetProductResponseJson;
import com.rachcode.peykman.model.json.book.LiatLokasiDriverResponse;
import com.rachcode.peykman.model.json.book.MboxRequestJson;
import com.rachcode.peykman.model.json.book.MboxResponseJson;
import com.rachcode.peykman.model.json.book.MserviceRequestJson;
import com.rachcode.peykman.model.json.book.MserviceResponseJson;
import com.rachcode.peykman.model.json.book.OffecrcodeequestJson;
import com.rachcode.peykman.model.json.book.RequestFoodRequestJson;
import com.rachcode.peykman.model.json.book.RequestFoodResponseJson;
import com.rachcode.peykman.model.json.book.RequestMartRequestJson;
import com.rachcode.peykman.model.json.book.RequestMartResponseJson;
import com.rachcode.peykman.model.json.book.RequestRideCarRequestJson;
import com.rachcode.peykman.model.json.book.RequestRideCarResponseJson;
import com.rachcode.peykman.model.json.book.RequestSendRequestJson;
import com.rachcode.peykman.model.json.book.RequestSendResponseJson;
import com.rachcode.peykman.model.json.book.SearchRestoranFoodRequest;
import com.rachcode.peykman.model.json.book.SearchRestoranFoodResponse;
import com.rachcode.peykman.model.json.book.detailTransaksi.GetDataTransaksiMMartResponse;
import com.rachcode.peykman.model.json.book.detailTransaksi.GetDataTransaksiMSendResponse;
import com.rachcode.peykman.model.json.book.detailTransaksi.GetDataTransaksiRequest;
import com.rachcode.peykman.model.json.book.massage.DetailTransaksiRequest;
import com.rachcode.peykman.model.json.book.massage.DetailTransaksiResponse;
import com.rachcode.peykman.model.json.book.massage.GetLayananMassageResponseJson;
import com.rachcode.peykman.model.json.book.massage.RequestMassageRequestJson;
import com.rachcode.peykman.model.json.book.massage.RequestMassageResponseJson;
import com.rachcode.peykman.model.json.book.offerCodeResponseJson;
import com.rachcode.peykman.model.json.user.GetStopTimeResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Androgo on 10/17/2018.
 */

public interface BookService {

    @GET("book/get_stop_time")
    Call<GetStopTimeResponseJson> getStopTime();

    @POST("book/list_driver_mride")
    Call<GetNearRideCarResponseJson> getNearRide(@Body GetNearRideCarRequestJson param);

    @POST("book/list_driver_send_motor")
    Call<GetNearRideDriverResponseJson> getNearsendMotor(@Body GetNearRideCarRequestJson param);

    @POST("book/list_driver_send_car")
    Call<GetNearRideDriverResponseJson> getNearsendcar(@Body GetNearRideCarRequestJson param);

    @POST("book/list_driver_send_vanet")
    Call<GetNearRideDriverResponseJson> getNearsendvanet(@Body GetNearRideCarRequestJson param);

    @POST("book/list_driver_mcar")
    Call<GetNearRideCarResponseJson> getNearCar(@Body GetNearRideCarRequestJson param);

    @POST("book/request_transaksi")
    Call<RequestRideCarResponseJson> requestTransaksi(@Body RequestRideCarRequestJson param);

    @POST("book/request_transaksi_mmart")
    Call<RequestMartResponseJson> requestTransaksiMMart(@Body RequestMartRequestJson param);

    @POST("book/request_transaction_send")
    Call<RequestSendResponseJson> requestTransMSend(@Body RequestSendRequestJson param);

    @GET("book/get_kendaraan_angkut")
    Call<GetKendaraanAngkutResponseJson> getKendaraanAngkut();

    @POST("book/list_driver_mbox")
    Call<GetNearBoxResponseJson> getNearBox(@Body GetNearBoxRequestJson param);

    @POST("book/request_transaksi_mbox")
    Call<MboxResponseJson> requestTransaksiMbox(@Body MboxRequestJson param);

    @GET("book/get_additional_send")
    Call<GetAdditionalMboxResponseJson> getAdditionalMbox();

    @GET("book/get_product_type")
    Call<GetProductResponseJson> getProductType();

    @POST("book/list_driver_mservice")
    Call<GetNearServiceResponseJson> getNearService(@Body GetNearServiceRequestJson param);

    @POST("book/coupon_serial")
    Call<offerCodeResponseJson> getOfferCode(@Body OffecrcodeequestJson param);

    @POST("book/request_transaksi_mservice")
    Call<MserviceResponseJson> requestTransaksi(@Body MserviceRequestJson param);

    @GET("book/get_data_mservice_ac")
    Call<GetDataMserviceResponseJson> getDataMservice();

    @GET("book/get_data_pulsa")
    Call<GetDataPulsaResponseJson> getDataPulsa();


    @GET("book/get_layanan_massage")
    Call<GetLayananMassageResponseJson> getLayananMassage();

    @POST("book/request_transaksi_mmassage")
    Call<RequestMassageResponseJson> requestTransaksiMMassage(@Body RequestMassageRequestJson param);

    @POST("book/list_driver_mmassage")
    Call<GetNearRideCarResponseJson> getNearMassage(@Body GetNearRideCarRequestJson param);

    @POST("book/get_data_transaksi_mmassage")
    Call<DetailTransaksiResponse> getDetailTransaksiMassage(@Body DetailTransaksiRequest param);

    @POST("book/get_data_restoran")
    Call<GetDataRestoResponseJson> getDataRestoran(@Body GetDataRestoRequestJson param);

    @POST("book/get_food_in_resto")
    Call<GetFoodRestoResponseJson> getFoodResto(@Body GetFoodRestoRequestJson param);

    @POST("book/search_restoran_or_food")
    Call<SearchRestoranFoodResponse> searchRestoranOrFood(@Body SearchRestoranFoodRequest param);

    @POST("book/get_resto_by_kategori")
    Call<GetDataRestoByKategoriResponseJson> getDataRestoranByKategori(@Body GetDataRestoByKategoriRequestJson param);

    @POST("book/request_transaksi_mfood")
    Call<RequestFoodResponseJson> requestTransaksiMFood(@Body RequestFoodRequestJson param);

    @POST("book/check_transaction_status")
    Call<CheckStatusTransaksiResponse> checkStatusTransaksi(@Body CheckStatusTransaksiRequest param);

    @GET("book/liat_lokasi_driver/{id}")
    Call<LiatLokasiDriverResponse> liatLokasiDriver(@Path("id") String idDriver);

    @POST("book/get_data_order_mmassage")
    Call<String> getDataOrderMMassage(@Body GetDataTransaksiRequest param);

    @POST("book/get_data_transaksi_mfood")
    Call<String> getDataTransaksiMFood(@Body GetDataTransaksiRequest param);

    @POST("book/get_data_transaksi_mservice")
    Call<String> getDataTransaksiMService(@Body GetDataTransaksiRequest param);

    @POST("book/get_data_transaksi_mmart")
    Call<GetDataTransaksiMMartResponse> getDataTransaksiMMart(@Body GetDataTransaksiRequest param);

    @POST("book/get_data_transaksi_mbox")
    Call<String> getDataTransaksiMBox(@Body GetDataTransaksiRequest param);

    @POST("book/get_data_transaksi_msend")
    Call<GetDataTransaksiMSendResponse> getDataTransaksiMSend(@Body GetDataTransaksiRequest param);
}
