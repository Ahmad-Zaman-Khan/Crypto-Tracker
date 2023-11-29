package com.example.cryptotracker;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Example implements Serializable {
    @SerializedName("data")
    private ArrayList<Data> data = new ArrayList<>();
    public ArrayList<Data> getData() {
        return data;
    }

}
