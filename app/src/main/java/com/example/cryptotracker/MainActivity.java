package com.example.cryptotracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.graphics.Color;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NotesRvAdaapter.ItemClicked {
    private NotesRvAdaapter adapter;
    private long pressedTime = 0;
    private ProgressBar progressBar;
    private NoteDatabase database;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesRvAdaapter(this, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, this::onNotesChanged);

        database = NoteDatabase.getDatabase(this);

    }

    private void onNotesChanged(List<Note> list) {
        if (list != null && !list.isEmpty()) {
            adapter.updateList(list);
            processNoteData(list.get(0));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void processNoteData(Note note) {
        if (note != null) {
            USD jsonData = note.getMeanings().get(0).getQuote().getUSD();
            double price = jsonData.getPrice();

            double percentChange = jsonData.getPercentChange24h();
            double numericChange = (percentChange / 100) * price;
            DecimalFormat str3 = (DecimalFormat) DecimalFormat.getInstance();
            str3.setMaximumFractionDigits(2);
            NumberFormat str = NumberFormat.getCurrencyInstance();

            TextView textView7 = findViewById(R.id.textView7);
            TextView textView2 = findViewById(R.id.textView2);
            TextView textView4 = findViewById(R.id.textView4);

            if (!Double.isNaN(price)) {
                textView2.setText(str.format(price));
                textView4.setText(str3.format(numericChange) + "    " + "(" + str3.format(percentChange) + "%)");
                textView7.setText("Past day");

                textView4.setTextColor(
                        Color.parseColor(percentChange < 0 ? "#ff0000" : "#32CD32")
                );

            }
        }
    }


    @Override
    public void onItemClicked(Note item) {
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        intent.putExtra("Key", item);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}

