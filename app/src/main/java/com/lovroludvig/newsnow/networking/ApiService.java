package com.lovroludvig.newsnow.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v2/top-headlines")
    Call<GenericResponse> getTopNews(@Query("sources") String source, @Query("apiKey") String apiKey, @Query("sortBy") String sortBy, @Query("pageSize") String pageSize, @Query("page") String page);

    @GET("/v2/top-headlines")
    Call<GenericResponse> getNewsByCategory(@Query("category") String category, @Query("apiKey") String apiKey, @Query("sortBy") String sortBy, @Query("pageSize") String pageSize, @Query("page") String page);

    @GET("/v2/everything")
    Call<GenericResponse> getNewsByQuery(@Query("sources") String source, @Query("q") String query, @Query("apiKey") String apiKey, @Query("sortBy") String sortBy, @Query("pageSize") String pageSize, @Query("page") String page);
}
