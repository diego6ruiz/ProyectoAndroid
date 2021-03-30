package com.example.covid.ui.fragments.responses;

import com.example.covid.ui.fragments.models.Emergency;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmergencyResponse {
    @SerializedName("whatsapp_phone")
    @Expose
    private String whatsappPhone;
    @SerializedName("data")
    @Expose
    private List<Emergency> data = null;

    public String getWhatsappPhone() {
        return whatsappPhone;
    }

    public void setWhatsappPhone(String whatsappPhone) {
        this.whatsappPhone = whatsappPhone;
    }

    public List<Emergency> getData() {
        return data;
    }

    public void setData(List<Emergency> data) {
        this.data = data;
    }
}
