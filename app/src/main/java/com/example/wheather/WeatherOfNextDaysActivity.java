package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherOfNextDaysActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtCityName;
    ListView lvNextDays;
    ArrayList<Weather> arrayWeather;
    NextDaysAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_of_next_days);
        AnhXa();

        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        Get7DaysData(city);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        imgBack=findViewById(R.id.imageViewBack);
        txtCityName=findViewById(R.id.textViewCityName);
        lvNextDays=findViewById(R.id.listViewNextDays);
        arrayWeather =new ArrayList<>();
        adapter =new NextDaysAdapter(this,R.layout.row_listview_day,arrayWeather);
        lvNextDays.setAdapter(adapter);
    }

    private void Get7DaysData(String city) {
        String url="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&appid=c64faba234f89db0ba7c858b28469f4f";
        RequestQueue requestQueue= Volley.newRequestQueue(WeatherOfNextDaysActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectCity=response.getJSONObject("city");
                            String cityName =jsonObjectCity.getString("name");
                            txtCityName.setText(cityName);

                            JSONArray jsonArrayList = response.getJSONArray("list");
                            for(int i =0; i<jsonArrayList.length();i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");
                                long l=Long.valueOf(ngay);
                                Date date=new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE yyyy-MM-dd HH");
                                String day=simpleDateFormat.format(date);

                                JSONObject jsonObjectMain=jsonObjectList.getJSONObject("main");
                                String max= jsonObjectMain.getString("temp_max");
                                String min =jsonObjectMain.getString("temp_min");

                                Double a=Double.valueOf(max);
                                String maxTemp=String.valueOf(a.intValue());
                                Double b=Double.valueOf(min);
                                String minTemp=String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather= jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                                String state =jsonObjectWeather.getString("description");
                                String icon =jsonObjectWeather.getString("icon");

                                arrayWeather.add(new Weather(day,state,icon,maxTemp,minTemp));
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Log.d("AAA", "Loi: "+e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WeatherOfNextDaysActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Loi: "+error);
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
