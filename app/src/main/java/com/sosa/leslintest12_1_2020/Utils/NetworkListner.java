package com.sosa.leslintest12_1_2020.Utils;

public interface NetworkListner {
    void successResponse(Response response);

    void failureResponse(Response response);

    void noInternet();
}
