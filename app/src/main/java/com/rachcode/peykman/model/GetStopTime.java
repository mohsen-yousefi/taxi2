package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStopTime {
    @SerializedName("time_id")
    @Expose
    private String timeId;
    @SerializedName("time_premium")
    @Expose
    private String timePremium;
    @SerializedName("time_cost")
    @Expose
    private String timeCost;

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getTimePremium() {
        return timePremium;
    }

    public void setTimePremium(String timePremium) {
        this.timePremium = timePremium;
    }

    public String getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost;
    }
}
