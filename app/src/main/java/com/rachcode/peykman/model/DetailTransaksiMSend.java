package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by fathony on 24/02/2017.
 */

public class DetailTransaksiMSend {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_pelanggan")
    @Expose
    private String idPelanggan;
    @SerializedName("id_driver")
    @Expose
    private String idDriver;
    @SerializedName("order_fitur")
    @Expose
    private String orderFitur;
    @SerializedName("start_latitude")
    @Expose
    private double startLatitude;
    @SerializedName("start_longitude")
    @Expose
    private double startLongitude;
    @SerializedName("end_latitude")
    @Expose
    private double endLatitude;
    @SerializedName("end_longitude")
    @Expose
    private double endLongitude;
    @SerializedName("jarak")
    @Expose
    private double jarak;
    @SerializedName("harga")
    @Expose
    private long harga;
    @SerializedName("waktu_order")
    @Expose
    private Date waktuOrder;
    @SerializedName("waktu_selesai")
    @Expose
    private Date waktuSelesai;
    @SerializedName("alamat_asal")
    @Expose
    private String alamatAsal;
    @SerializedName("alamat_tujuan")
    @Expose
    private String alamatTujuan;
    @SerializedName("kode_promo")
    @Expose
    private String kodePromo;
    @SerializedName("kredit_promo")
    @Expose
    private String kreditPromo;
    @SerializedName("biaya_akhir")
    @Expose
    private String biayaAkhir;
    @SerializedName("pakai_mpay")
    @Expose
    private boolean pakaiMpay;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("id_transaksi")
    @Expose
    private String idTransaksi;
    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;
    @SerializedName("nama_pengirim")
    @Expose
    private String namaPengirim;
    @SerializedName("telepon_pengirim")
    @Expose
    private String teleponPengirim;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("telepon_penerima")
    @Expose
    private String teleponPenerima;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_transaksi")
    @Expose
    private String statusTransaksi;

    public String getId() {
        return id;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public String getOrderFitur() {
        return orderFitur;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public double getJarak() {
        return jarak;
    }

    public long getHarga() {
        return harga;
    }

    public Date getWaktuOrder() {
        return waktuOrder;
    }

    public Date getWaktuSelesai() {
        return waktuSelesai;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public String getKreditPromo() {
        return kreditPromo;
    }

    public String getBiayaAkhir() {
        return biayaAkhir;
    }

    public boolean isPakaiMpay() {
        return pakaiMpay;
    }

    public double getRate() {
        return rate;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getNamaPengirim() {
        return namaPengirim;
    }

    public String getTeleponPengirim() {
        return teleponPengirim;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public String getTeleponPenerima() {
        return teleponPenerima;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }
}
