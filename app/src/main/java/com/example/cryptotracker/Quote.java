package com.example.cryptotracker;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Quote implements Serializable {
    @SerializedName("USD")
    private USD USD = new USD();
    public USD getUSD() {
        return USD;
    }

}
