package com.thalessz.filosoquiz.api;

import com.thalessz.filosoquiz.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    @POST("/submit_resultado")
    Call<Result> submit_resultado(@Body Result resultado);

    @GET("fetch_resultados/{nome}")
    Call <List<Result>> fetchResultados(@Path("nome") String nome);

}
