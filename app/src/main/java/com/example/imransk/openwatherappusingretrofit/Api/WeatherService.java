package com.example.imransk.openwatherappusingretrofit.Api;

import com.example.imransk.openwatherappusingretrofit.PojoClass.CurrentWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherService {


    @GET()
    Call<CurrentWeatherResponse> getCurrentWeather(@Url String stringUrl);
}
