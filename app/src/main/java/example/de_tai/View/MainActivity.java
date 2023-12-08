package example.de_tai.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Area;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import example.de_tai.Controller.Nam_GD2nAdapter;
import example.de_tai.Controller.Nam_MoonAdapter;
import example.de_tai.Controller.Nam_WindAdapter;
import example.de_tai.Model.Nam_Weather_GD2_next;
import example.de_tai.R;
import example.de_tai.Controller.WeatherRVAdaper;
import example.de_tai.Model.WeatherRVModel;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    RelativeLayout RLHome;
    ProgressBar PBLoading;
    TextView TVCityName, TVTemperature1, TVCondition;
    TextInputEditText EdtCity;
    ImageView IVBack, IVIcon, IVSearch;
    RecyclerView RVWeather;
    ArrayList<WeatherRVModel> weatherRVModelArrayList;
    WeatherRVAdaper weatherRVAdaper;
    LocationManager locationManager;
    LinearLayout LLChart;

    AnyChartView any_chart_view;
    private int PERMISSION_CODE = 1;
    private String cityName;

    GoogleMap myMap;

    //----- NAM----
    ListView lvMattroi, lvMattrang, lvGio;

    ImageView imgMattroi, imgGio;

    Nam_GD2nAdapter gd2nAdapter;

    Nam_MoonAdapter moonAdapter;

    Nam_WindAdapter windAdapter;

    ArrayList<Nam_Weather_GD2_next> lsGD2_next = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsMoon = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsWind = new ArrayList<>();

    String urlnam ="https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q="+cityName+"&days=1&aqi=no&alerts=no";

    String url2 ="https://api.weatherapi.com/v1/current.json?key=0f4ce91ee1a24deebce53135232211&q=London&aqi=no";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addControl();
        addEvent();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);

        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null){cityName = getCityName(location.getLongitude(),location.getLatitude());
            getWeatherInfo(cityName);
        } else {
            cityName = "Saigon";
            getWeatherInfo(cityName);
        }

//        Set background
        ConstraintLayout constraintLayout = findViewById(R.id.mainlayout);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if(currentHour >= 5 && currentHour <= 11) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_morning);
            constraintLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if (currentHour >= 12 && currentHour <= 16) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_afternoon);
            constraintLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if(currentHour >= 17 && currentHour <= 19) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_evening);
            constraintLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if (currentHour >= 20 || currentHour <= 4) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_night);
            constraintLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);






        //--animation-- NAM ---

        imgMattroi.setBackgroundResource(R.drawable.animation);
        imgGio.setBackgroundResource(R.drawable.animation2);


        getAllDataWeather_GD2_nextMT(urlnam);
        getAllDataWeather_GD2_nextMoon(urlnam);
        getAllDataWeather_GD2_next2(url2);


    }
    /// ---- NAM----
    //-- ANIMATION --
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


    public void addControl(){
        setContentView(R.layout.activity_main_new);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        RLHome = (RelativeLayout) findViewById(R.id.RLHome);
//        PBLoading = (ProgressBar) findViewById(R.id.PBLoading);
        TVCityName = (TextView) findViewById(R.id.TVCityName);
        any_chart_view = (AnyChartView) findViewById(R.id.any_chart_view);
        TVTemperature1 = (TextView) findViewById(R.id.TVTemperature1);
        TVCondition = (TextView) findViewById(R.id.TVCondition);
        EdtCity = (TextInputEditText) findViewById(R.id.EdtCity);
        IVIcon = (ImageView) findViewById(R.id.IVIcon);
        IVSearch = (ImageView) findViewById(R.id.IVSearch);
        IVBack = (ImageView) findViewById(R.id.IVBack);
        RVWeather = (RecyclerView) findViewById(R.id.RVWeather);
        LLChart = (LinearLayout) findViewById(R.id.LLChart);
        weatherRVModelArrayList = new ArrayList<>();
        weatherRVAdaper = new WeatherRVAdaper(this,weatherRVModelArrayList);
        RVWeather.setAdapter(weatherRVAdaper);
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // --- NAM ---
        imgGio =(ImageView)findViewById(R.id.imgGio);
        imgMattroi =(ImageView)findViewById(R.id.imgMattroi);

        lvMattroi =(ListView) findViewById(R.id.lvMattroi);
        lvMattrang =(ListView) findViewById(R.id.lvMattrang);
        lvGio =(ListView) findViewById(R.id.lvGio);

    }

    public void addEvent(){
        IVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = EdtCity.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter city name", Toast.LENGTH_SHORT).show();

                }else {
                    TVCityName.setText(cityName);
                    getWeatherInfo(city);
                }
            }
        });

        // --- TƯỜNG ---

        LLChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String push = TVCityName.getText().toString();
//                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
//                intent.putExtra("MY_STRING", push); // Pass the cityName to ChartActivity
//                startActivity(intent);

                String push2 = TVCityName.getText().toString();
                Intent intent2 = new Intent(MainActivity.this, HangGioActivity.class);
                intent2.putExtra("MY_STRING_2", push2); // Pass the cityName to ChartActivity
                startActivity(intent2);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void getWeatherInfo(String cityName){
        String url = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q="+cityName+"&days=3&aqi=no&alerts=no";
        TVCityName.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        Intent intent = new Intent(MainActivity.this, HangGioActivity.class);
        intent.putExtra("locate", cityName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                PBLoading.setVisibility(View.GONE);
//                RLHome.setVisibility(View.VISIBLE);
                weatherRVModelArrayList.clear();

                try {
                    List<DataEntry> data = new ArrayList<>();

                    String temperature = response.getJSONObject("current").getString("temp_c");
                    TVTemperature1.setText(temperature+"°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(IVIcon);
                    TVCondition.setText(condition);


                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");

                    Calendar now = Calendar.getInstance();
                    int currentHour = now.get(Calendar.HOUR_OF_DAY);
                    int hoursAdded = 0;
                    for(int i = 0; i<hourArray.length();i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        int hour = Integer.parseInt(time.substring(11, 13));

                        if (i == 0 && hour < currentHour) {
                            continue;
                        }

                        if (hoursAdded >= 48) {
                            break;
                        }
                        String hourOnly = time.substring(11, 16);
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        String rain = hourObj.getString("chance_of_rain");
                        weatherRVModelArrayList.add(new WeatherRVModel(time, temper,img, wind));
                        weatherRVAdaper.notifyDataSetChanged();

                        data.add(new ValueDataEntry(hourOnly, Integer.parseInt(rain)));
                    }

                    //VẼ SƠ ĐỒ
                    AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                    Cartesian cartesian = AnyChart.area();
                    Area area = cartesian.area(data);
                    cartesian.yScale().maximum(100);
                    cartesian.animation(true);
                    cartesian.title("Chance of Rain");
                    anyChartView.setChart(cartesian);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public String getCityName(double longtitude, double latitude){
        String cityName = "Not found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(latitude, longtitude,10);
            for (Address adr : addresses){
                if (adr != null){
                    String city = adr.getLocality();
                    if(city !=null && !city.equals("")){
                        cityName = city;
                    }else {
                        Log.d("TAG", "CITY NOT FOUND");
                        Toast.makeText(this,"User City Not Found..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(getApplicationContext(), RadarActivity.class);
                startActivity(intent);
            }
        });
    }


    // -- NAM--
    // ---- mặt trời
    public void getAllDataWeather_GD2_nextMT(String urlnam)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlnam,
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


            lsGD2_next.add(a);

        }
        gd2nAdapter = new Nam_GD2nAdapter(getApplicationContext(), R.layout.itemlayout_mattroi, lsGD2_next);
        lvMattroi.setAdapter(gd2nAdapter);
    }


    //-- mặt trăng ---

    public void getAllDataWeather_GD2_nextMoon(String urlnam)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlnam,
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
}

