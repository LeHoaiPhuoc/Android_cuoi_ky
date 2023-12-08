/*
package example.de_tai.View;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.de_tai.Controller.Nam_GD2nAdapter;
import example.de_tai.Controller.Nam_MoonAdapter;
import example.de_tai.Controller.Nam_WindAdapter;
import example.de_tai.Model.Nam_Weather_GD2_next;
import example.de_tai.R;

public class Nam_GD2_next extends AppCompatActivity {

    ListView  lvMattroi, lvMattrang, lvGio;

    ImageView imgMattroi, imgGio;

    Nam_GD2nAdapter gd2nAdapter;

    Nam_MoonAdapter moonAdapter;

    Nam_WindAdapter windAdapter;

    ArrayList<Nam_Weather_GD2_next> lsGD2_next = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsMoon = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsWind = new ArrayList<>();

    String url ="https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q=London&days=1&aqi=no&alerts=no";

    String url2="https://api.weatherapi.com/v1/current.json?key=0f4ce91ee1a24deebce53135232211&q=London&aqi=no";


    //---animation sun
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        AnimationDrawable animationDrawable =(AnimationDrawable) imgMattroi.getBackground();
        AnimationDrawable animationDrawable2 =(AnimationDrawable) imgGio.getBackground();

        if(hasFocus) {
            animationDrawable.start();
            animationDrawable2.start();
        }
        else {
            animationDrawable.start();
            animationDrawable2.stop();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd2_next);


        imgGio =(ImageView)findViewById(R.id.imgGio);
        imgMattroi =(ImageView)findViewById(R.id.imgMattroi);

        lvMattroi =(ListView) findViewById(R.id.lvMattroi);
        lvMattrang =(ListView) findViewById(R.id.lvMattrang);
        lvGio =(ListView) findViewById(R.id.lvGio);

        //--animation

        imgMattroi.setBackgroundResource(R.drawable.animation);


        imgGio.setBackgroundResource(R.drawable.animation2);






        getAllDataWeather_GD2_nextMT(url);

        getAllDataWeather_GD2_nextMoon(url);

        getAllDataWeather_GD2_next2(url2);


    }

    // ---- mặt trời
    public void getAllDataWeather_GD2_nextMT(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Nam_GD2_next.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataMT(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }
    // ---- mặt trời ----

    public void parseJsonDataMT(String response)throws JSONException{

        JSONObject jsonRespond = new JSONObject(response);

        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");


        for (int i =0; i<forecastdayArray.length(); i++ ) {

            JSONObject forecastday = forecastdayArray.getJSONObject(i);
            Nam_Weather_GD2_next a = new Nam_Weather_GD2_next();

            JSONObject astroArray =forecastday.getJSONObject("astro");
            a.sunrise = astroArray.getString("sunrise");
            a.sunset = astroArray.getString("sunset");
          */
/*  a.moonrise =astroArray.getString("moonrise");
            a.moonset =astroArray.getString("moonset");
            a.moonphase =astroArray.getString("moon_phase");*//*


            lsGD2_next.add(a);

        }
        gd2nAdapter = new Nam_GD2nAdapter(getApplicationContext(), R.layout.itemlayout_mattroi, lsGD2_next);
        lvMattroi.setAdapter(gd2nAdapter);
    }


    //-- mặt trăng ---

    public void getAllDataWeather_GD2_nextMoon(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Nam_GD2_next.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonDataMoon(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    //--- mặt trăng ----
    public void parseJsonDataMoon(String response)throws JSONException{

        JSONObject jsonRespond = new JSONObject(response);

        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");


        for (int i =0; i<forecastdayArray.length(); i++ ) {

            JSONObject forecastday = forecastdayArray.getJSONObject(i);
            Nam_Weather_GD2_next a = new Nam_Weather_GD2_next();

            JSONObject astroArray =forecastday.getJSONObject("astro");

            a.moonrise =astroArray.getString("moonrise");
            a.moonset =astroArray.getString("moonset");
            a.moonphase =astroArray.getString("moon_phase");

            lsMoon.add(a);

        }
        moonAdapter = new Nam_MoonAdapter(getApplicationContext(), R.layout.itemlayout_mattrang, lsMoon);
        lvMattrang.setAdapter(moonAdapter);
    }


    /// tốc độ - hướng gió


    public void getAllDataWeather_GD2_next2(String url2)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Nam_GD2_next.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonData2(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }
    /// tốc độ - hướng gió
    public void parseJsonData2(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject current = jsonResponse.getJSONObject("current");

        Nam_Weather_GD2_next e = new Nam_Weather_GD2_next();
        e.wind_kph = current.getString("wind_kph");
        e.win_dir = current.getString("wind_dir");

        lsWind.add(e);

        windAdapter = new Nam_WindAdapter(getApplicationContext(), R.layout.itemlayout_gio, lsWind);
        lvGio.setAdapter(windAdapter);
    }



}*/
