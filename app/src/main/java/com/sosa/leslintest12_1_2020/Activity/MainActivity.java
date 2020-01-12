package com.sosa.leslintest12_1_2020.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.sosa.leslintest12_1_2020.Adapter.FilmAdapter;
import com.sosa.leslintest12_1_2020.R;
import com.sosa.leslintest12_1_2020.Retrofit.Retrofit_models.FilmResponse;
import com.sosa.leslintest12_1_2020.Retrofit.Retrofit_models.Result;
import com.sosa.leslintest12_1_2020.Utils.NetworkListner;
import com.sosa.leslintest12_1_2020.Utils.Response;
import com.sosa.leslintest12_1_2020.presenter.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkListner {

    NetworkManager manager;
    List<Result> list=new ArrayList<>();
    FilmAdapter adapter;
    RecyclerView rv_film;
    ImageView img_refresh;
    ConstraintLayout layout_main;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager=NetworkManager.getInstance();
        manager.setNetworkListener(this);
        layout_main=findViewById(R.id.layout_main);
        rv_film=findViewById(R.id.rv_films);
        img_refresh=findViewById(R.id.img_refresh);
        progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        img_refresh.setVisibility(View.GONE);

        rv_film.setHasFixedSize(true);
        rv_film.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        manager.callFilmApi();

    }

    @Override
    public void successResponse(Response response) {

        rv_film.setVisibility(View.VISIBLE);
        img_refresh.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if (response.body instanceof FilmResponse)
        {
            FilmResponse response1= (FilmResponse) response.body;

            list=response1.getResults();
            adapter=new FilmAdapter(MainActivity.this,list);
            rv_film.setAdapter(adapter);

        }

    }

    @Override
    public void failureResponse(Response response) {
        progressBar.setVisibility(View.GONE);
        rv_film.setVisibility(View.GONE);
        img_refresh.setVisibility(View.VISIBLE);
        snakbar(response.errorBody);
    }

    @Override
    public void noInternet() {

        progressBar.setVisibility(View.GONE);
        rv_film.setVisibility(View.GONE);
        img_refresh.setVisibility(View.VISIBLE);
        snakbar("No Internet!! \n Please connect to Internet to continue");
    }

    public void snakbar(String message) {
        Snackbar snackbar = Snackbar.make(layout_main, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    public void refresh(View view) {
        manager.callFilmApi();
    }
}
