package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fathony on 11/02/2017.
 */

public class CheckStatusTransaksiRequest {

    @Expose
    @SerializedName("transaction_id")
    private String transaction_id;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void settransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
