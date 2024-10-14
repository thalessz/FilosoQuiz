package com.thalessz.filosoquiz.api;

import com.thalessz.filosoquiz.models.Resultado;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {

    @POST("/submit_resultado")
    Call<Void> submit_resultado(@Body Resultado resultado);

    @GET("/fetch_resultado")
    Call<Resultado> fetch_resultados();

}
