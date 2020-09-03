package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch,btnChangeActivity;
    TextView txtCity,txtCountry,txtTemp,txtState,txtHumidity,txtCloud,txtWind,txtDay;
    ImageView imgIcon;
    String City="Saigon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        edtSearch.setText(City);
        GetCurrentWeatherData(City);


       btnSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(edtSearch.getText().toString().equals("")){
                   edtSearch.setText(City);
                   GetCurrentWeatherData(City);
               }
               else {
                   GetCurrentWeatherData(edtSearch.getText().toString());
               }
           }
       });

       btnChangeActivity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent =new Intent(MainActivity.this,WeatherOfNextDaysActivity.class);
               if(edtSearch.getText().toString().length() == 0){
                   intent.putExtra("city",City);
               }else {
                   intent.putExtra("city",edtSearch.getText().toString());
               }
               startActivity(intent);
           }
       });

    }

    public void GetCurrentWeatherData(String location){

        String url="https://api.openweathermap.org/data/2.5/weather?q="+location+"&units=metric&appid=c64faba234f89db0ba7c858b28469f4f";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String day=response.getString("dt");
                            String name=response.getString("name");
                            txtCity.setText("Tên thành phố :"+name);

                            long l=Long.valueOf(day);
                            Date date=new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day=simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather=response.getJSONArray("weather");
                            JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                            String state=jsonObjectWeather.getString("main");
                            String icon=jsonObjectWeather.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            txtState.setText(state);

                            JSONObject jsonObjectMain=response.getJSONObject("main");
                            String nhietDo=jsonObjectMain.getString("temp");
                            String doAm=jsonObjectMain.getString("humidity");

                            Double a=Double.valueOf(nhietDo);
                            String temp=String.valueOf(a.intValue());
                            txtTemp.setText(temp+"℃");
                            txtHumidity.setText(doAm+"%");

                            JSONObject jsonObjectWind=response.getJSONObject("wind");
                            String wind =jsonObjectWind.getString("speed");
                            txtWind.setText(wind+"m/s");

                            JSONObject jsonObjectClouds=response.getJSONObject("clouds");
                            String clouds= jsonObjectClouds.getString("all");
                            txtCloud.setText(clouds+"%");

                            JSONObject jsonObjectSys= response.getJSONObject("sys");
                            String country=jsonObjectSys.getString("country");
                            txtCountry.setText("Tên quốc gia: "+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    private void AnhXa() {
        edtSearch           =findViewById(R.id.editTextSearch);
        btnSearch           =findViewById(R.id.buttonSearch);
        btnChangeActivity   =findViewById(R.id.buttonChangeActivity);
        txtCity             =findViewById(R.id.textViewCity);
        txtCountry          =findViewById(R.id.textViewCountry);
        txtTemp             =findViewById(R.id.textViewTemp);
        txtState            =findViewById(R.id.textViewState);
        txtHumidity         =findViewById(R.id.textViewHumidity);
        txtCloud            =findViewById(R.id.textViewCloud);
        txtWind             =findViewById(R.id.textViewWind);
        txtDay              =findViewById(R.id.textViewDay);
        imgIcon             =findViewById(R.id.imageViewIcon);
    }

}
