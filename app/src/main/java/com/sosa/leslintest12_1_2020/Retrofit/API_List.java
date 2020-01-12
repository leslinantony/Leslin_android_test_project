package com.sosa.leslintest12_1_2020.Retrofit;

import com.sosa.leslintest12_1_2020.Retrofit.Retrofit_models.FilmResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API_List {
    public String BASE_URL="https://swapi.co/api/";

    @GET("films")
    Call<FilmResponse> getFilm();
}
