package com.rachcode.peykman.model.json.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haris on 11/28/16.
 */

public class CancelBookRequestJson {
   /* @Expose
    @SerializedName("id")
    public String id;*/

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("driver_id")
    public String driver_id;


}
