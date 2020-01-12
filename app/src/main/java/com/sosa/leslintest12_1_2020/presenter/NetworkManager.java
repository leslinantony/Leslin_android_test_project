package com.sosa.leslintest12_1_2020.presenter;
import com.sosa.leslintest12_1_2020.Retrofit.API_List;
import com.sosa.leslintest12_1_2020.Retrofit.Retrofit_models.FilmResponse;
import com.sosa.leslintest12_1_2020.Utils.NetworkListner;
import com.sosa.leslintest12_1_2020.Utils.Response;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager sManager;
    private static API_List mAPIInstance;
    private NetworkListner mListener;
    public static NetworkManager getInstance()
    {

        if (sManager==null)
        {
            sManager=new NetworkManager();
        }
        return sManager;
    }

    public void setNetworkListener(NetworkListner mListener) {
        this.mListener = mListener;
    }

    private NetworkManager()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_List.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)

                .build();

        mAPIInstance = retrofit.create(API_List.class);
    }

    public void callFilmApi()
    {
        Call<FilmResponse> responseCall=mAPIInstance.getFilm();
        responseCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, retrofit2.Response<FilmResponse> response) {
                if (response.code()==200)
                {
                    API_success(response);
                }

            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                API_failure(t);

            }
        });
    }


    private void API_success(retrofit2.Response response) {
        Response res = new Response();
        res.body = response.body();
        res.status = response.isSuccessful();
        res.code = response.code();
        try {
            res.errorBody = (response.errorBody() == null ? null : response.errorBody().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.body != null) {
            if (mListener != null) {
                mListener.successResponse(res);
            }
        } else {
            if (mListener != null) {
                mListener.failureResponse(res);
            }
        }
    }

    private void API_failure(Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            if (mListener != null) {
                mListener.noInternet();
            }
        } else {
            Response response = new Response();
            response.errorBody = t.toString();
            if (mListener != null) {
                mListener.failureResponse(response);
            }
        }
    }




}
