package com.example.cryptotracker;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class USD implements Serializable {
    @SerializedName("price")
    private Double price;

    @SerializedName("volume_24h")
    private Double volume24h;

    @SerializedName("volume_change_24h")
    private Double volumeChange24h;

    @SerializedName("percent_change_1h")
    private Double percentChange1h;

    @SerializedName("percent_change_24h")
    private Double percentChange24h;

    @SerializedName("percent_change_7d")
    private Double percentChange7d;

    @SerializedName("percent_change_30d")
    private Double percentChange30d;
    @SerializedName("market_cap")
    private Double marketCap;
    public Double getPrice() {
        return price;
    }

    public Double getVolume24h() {
        return volume24h;
    }

    public Double getVolumeChange24h() {
        return volumeChange24h;
    }

    public Double getPercentChange1h() {
        return percentChange1h;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public Double getPercentChange7d() {
        return percentChange7d;
    }

    public Double getPercentChange30d() {
        return percentChange30d;
    }

    public Double getMarketCap() {
        return marketCap;
    }

}
