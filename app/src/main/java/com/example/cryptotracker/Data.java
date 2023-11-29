package com.example.cryptotracker;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("total_supply")
    private Double totalSupply;
    @SerializedName("quote")
    private Quote quote = new Quote();

    // Getters and Setters for all fields

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }
    public Double getTotalSupply() {
        return totalSupply;
    }
    public Quote getQuote() {
        return quote;
    }

}

