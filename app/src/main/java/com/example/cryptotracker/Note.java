package com.example.cryptotracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "notes_table")
public class Note implements Serializable {

    @PrimaryKey
    private Integer id;
    private ArrayList<Data> meanings;

    public Note(Integer id, ArrayList<Data> meanings) {
        this.id = id;
        this.meanings = meanings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Data> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Data> meanings) {
        this.meanings = meanings;
    }
}
