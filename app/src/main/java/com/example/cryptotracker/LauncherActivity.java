package com.example.cryptotracker;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {

    private ArrayList<Data> items = new ArrayList<>();
    private NoteViewModel viewModel;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_TIME_KEY = "isFirstTime";
    boolean isFirstTime;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        settings = getSharedPreferences(PREFS_NAME, 0);
        isFirstTime = settings.getBoolean(FIRST_TIME_KEY, true);

        if (isFirstTime) {
            // It's the first time, check for internet connectivity
            if (!isNetworkAvailable()) {
                showNoInternetDialog();
                return;
            }
            else {
                getNames();

            }
        }

        else {
            getNames();
            Intent intent1 = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent1);
        }

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    private void getNames() {

        Call<Example> news = NewsService.inst.getProperties();
        news.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example body = response.body();
                items = body != null ? body.getData() : new ArrayList<>();

                for (int id = 0; id < items.size() - 1; id++) {
                    viewModel.insertNote(
                            new Note(
                                    id,
                                    items
                            )
                    );
                }

                   if( isFirstTime) {
                       Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                       startActivity(intent);
                   }
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(FIRST_TIME_KEY, false);
                editor.apply();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(LauncherActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Close the app if there is no internet connection
                    }
                })
                .setCancelable(false)
                .show();
    }

}
