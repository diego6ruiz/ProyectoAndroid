package com.example.covid.ui.fragments.services;

import com.example.covid.ui.fragments.models.Maps;
import com.example.covid.ui.fragments.models.News;
import com.example.covid.ui.fragments.models.Statistic;
import com.example.covid.ui.fragments.responses.EmergencyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("information")
    Call<EmergencyResponse> getEmergencies();

    @GET("covid")
    Call<Statistic> getStatistics();

    @GET("news")
    Call<List<News>> getNews();

    @GET("centres")
    Call<List<Maps>> getMaps();
}
