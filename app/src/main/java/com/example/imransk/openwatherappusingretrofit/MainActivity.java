package com.example.imransk.openwatherappusingretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imransk.openwatherappusingretrofit.Api.WeatherService;
import com.example.imransk.openwatherappusingretrofit.PojoClass.CurrentWeatherResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tem_TV;
    TextView sunrise_TV;
    TextView sunset_TV;
    TextView max_tem_TV;
    TextView min_tem_TV;
    ImageView imageView;

    public static String BASE_URL="http://api.openweathermap.org/data/2.5/";
//    weather?lat=23.810331&lon=90.412521&units=metric&appid=ab1f919d021e76086bbdf2761777438d
    public double latitude=23.810331;
    private double longitude=90.412521;
    private String units="metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tem_TV=findViewById(R.id.textview_temparature_ID);
        sunrise_TV=findViewById(R.id.textView_sunrise_ID);
        sunset_TV=findViewById(R.id.textView_sunset_ID);
        max_tem_TV=findViewById(R.id.textView_maxTemp_ID);
        min_tem_TV=findViewById(R.id.textView_minTemp_id);

        imageView=findViewById(R.id.imageView_ID);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String stringUrl = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s",latitude,longitude,units,getResources().getString(R.string.Weather_app_ID));

        WeatherService weatherService=retrofit.create(WeatherService.class);
        Call<CurrentWeatherResponse>currentWeatherResponseCall = weatherService.getCurrentWeather(stringUrl);
        currentWeatherResponseCall.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                CurrentWeatherResponse currentWeatherResponse=response.body();

                tem_TV.setText("Temp  : "+currentWeatherResponse.getMain().getTemp()+"° C");
                sunrise_TV.setText("sunrise : "+currentWeatherResponse.getSys().getSunrise());
                sunset_TV.setText("sunrise : "+currentWeatherResponse.getSys().getSunset());
                String iconUrl= "https://openweathermap.org/img/w/"+currentWeatherResponse.getWeather().get(0).getIcon()+".png";
                Picasso.get().load(iconUrl).into(imageView);

                max_tem_TV.setText("Max Temp : "+currentWeatherResponse.getMain().getTempMax()+"° C");
                min_tem_TV.setText("Min Temp : "+currentWeatherResponse.getMain().getTempMin()+"° C");

                Log.e("Main temp - - -", "onResponse: "+currentWeatherResponse.getMain().getTemp() );
                Log.e("Main temp Max - - -", "onResponse: "+currentWeatherResponse.getMain().getTempMax() );
                Log.e("Main  temp Min - - -", "onResponse: "+currentWeatherResponse.getMain().getTempMin() );
                Log.e("Main  Humidity - - -", "onResponse: "+currentWeatherResponse.getMain().getHumidity() );
                Log.e("Main  Pressure - - -", "onResponse: "+currentWeatherResponse.getMain().getPressure() );


                Log.e("Sys   country - - -", "onResponse: "+currentWeatherResponse.getSys().getCountry() );
                Log.e("Sys   message- - -", "onResponse: "+currentWeatherResponse.getSys().getMessage() );
                Log.e("Sys   sunrise- - -", "onResponse: "+currentWeatherResponse.getSys().getSunrise() );
                Log.e("Sys   sunset- - -", "onResponse: "+currentWeatherResponse.getSys().getSunset() );
                Log.e("Sys  type - - -", "onResponse: "+currentWeatherResponse.getSys().getType() );


                Log.e("Sys  type - - -", "onResponse: "+currentWeatherResponse.getSys().getType() );
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

            }
        });



    }
}
