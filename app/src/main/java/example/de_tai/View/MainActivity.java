package example.de_tai.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
import example.de_tai.Controller.Phuoc_GridViewThongSoThoiTietDiaDiemHienTaiAdapter;
import example.de_tai.Model.Nam_Weather_GD2_next;
import example.de_tai.Model.Phuoc_GridViewThongSoThoiTietDiaDiemHienTai;
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
    ImageView IVCondition;

    ArrayList<Nam_Weather_GD2_next> lsGD2_next = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsMoon = new ArrayList<>();

    ArrayList<Nam_Weather_GD2_next> lsWind = new ArrayList<>();

    String urlnam ="https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q="+cityName+"&days=1&aqi=no&alerts=no";

    String url2 ="https://api.weatherapi.com/v1/current.json?key=0f4ce91ee1a24deebce53135232211&q=London&aqi=no";


     //=====================PHƯỚC=================


    // GridView
     GridView gvThongSoThoiTietDiaDiemHienTai; // lv hiển thị dữ liệu
    Phuoc_GridViewThongSoThoiTietDiaDiemHienTaiAdapter gridViewThongSoThoiTietDiaDiemHienTaiAdapter;
    ArrayList<Phuoc_GridViewThongSoThoiTietDiaDiemHienTai> grvGridViewThongSoThoiTietDiaDiemHienTai = new ArrayList<>();
    String url_gv = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q=Saigon&days=1&aqi=yes&alerts=no";

    // Barchart
    BarChart barChart;
    List<String> lsNhietDoCaoNhat = new ArrayList<>();
    List<String> lsNhietDoThapNhat = new ArrayList<>();
    String url_barchart = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q=Saigon&days=3&aqi=yes&alerts=no";


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

        //===========PHƯỚC============

        // GridView
        getAllDataForGridViewForecastCurretnly(url_gv);

        // Barchart
        getAllDataForecastCurretnly(url_barchart);
        //===========PHƯỚC============




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


    @SuppressLint("MissingInflatedId")
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

        IVCondition = (ImageView) findViewById(R.id.IVCondition);

        //==========PHƯỚC==========
        gvThongSoThoiTietDiaDiemHienTai = findViewById(R.id.gvThongSoThoiTietDiaDiemHienTai);
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
                    // Tải hình ảnh và đặt vào ImageView sử dụng Picasso
                    Picasso.get().load(conditionIcon).into(IVIcon);
                    // Picasso.get().load("http:".concat(conditionIcon)).into(IVIcon);
                    Picasso.get().load("https:" + conditionIcon).into(IVIcon);
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
                       // hoursAdded++;
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

    //=================PHƯỚC==============

    // GridView
    // Phương thức lấy dữ liệu thông số thời tiết địa điểm hiện tại
    public void getAllDataForGridViewForecastCurretnly(String url) {
        // Yêu cầu sử dụng thư viện volley
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Tạo dối tượng trong lớp StringRequest để lấy dữ liệu từ url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() { // Định nghĩa 1 trình nghe mới khi có phản hồi từ yêu cầu
                    @Override
                    public void onResponse(String response) { // Phương thức này được gọi khi có phản hồi từ yêu cầu
                        try {
                            parseJsonDataForGridViewForecastCurretnly(response); // Phân tích dữ liệu JSON từ phản hồi
                        } catch (
                                JSONException e) { // Xử lý ngoại lệ trong quá trình phân tích dữ liệu
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // Khi có lỗi thì phương thức này sẽ được gọi để xử lý yêu cầu
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest); // Yêu cầu hàng đợi (trong trường hợp này là lấy yêu cầu từ file JSON)


    }

    // Phương thức phân tích dữ liệu JSON từ phản hồi
    public void parseJsonDataForGridViewForecastCurretnly(String response) throws JSONException {


        // Tạo 1 đối tượng JSON chính từ chuỗi phản hồi
        JSONObject jsonRespond = new JSONObject(response);
        // Truy cập đối tượng current
        JSONObject currentOject = jsonRespond.getJSONObject("current");

        // Lấy thông số "Cảm giác như"
        // Tạo đối tượng lấy thông số
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai camGiacNhu = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int feelslikecObject = currentOject.getInt("feelslike_c");
        camGiacNhu.icon = R.drawable.img_cam_giac_nhu;
        camGiacNhu.moTaIcon = "Cảm giác như";
        camGiacNhu.thongSoThoiTietDiaDiemHienTai = feelslikecObject + "°C";
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(camGiacNhu);

        // Lấy thông số "Độ ẩm"
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai doAm = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int humidityObject = currentOject.getInt("humidity");
        doAm.icon = R.drawable.img_do_am;
        doAm.moTaIcon = "Độ ẩm";
        doAm.thongSoThoiTietDiaDiemHienTai = humidityObject + "%";
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(doAm);

        // Lấy thông số "Chỉ số uv"
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai uv = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int uvObject = currentOject.getInt("uv");
        uv.icon = R.drawable.img_uv;
        uv.moTaIcon = "Chỉ số uv";
        uv.thongSoThoiTietDiaDiemHienTai = String.valueOf(uvObject);
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(uv);

        // Lấy thông số "Tầm nhìn"
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai vis = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int visObject = currentOject.getInt("vis_km");
        vis.icon = R.drawable.img_tam_nhin;
        vis.moTaIcon = "Tầm nhìn";
        vis.thongSoThoiTietDiaDiemHienTai = String.valueOf(visObject + " Km");
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(vis);

        // Lấy thông số "Tốc độ gió"
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai windKph = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int windKphObject = currentOject.getInt("wind_kph");
        windKph.icon = R.drawable.img_toc_do_gio;
        windKph.moTaIcon = "Tốc độ gió";
        windKph.thongSoThoiTietDiaDiemHienTai = String.valueOf(windKphObject + " Km/h");
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(windKph);

        // Lấy thông số "Áp suất không khí"
        Phuoc_GridViewThongSoThoiTietDiaDiemHienTai pressureMb = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTai();
        int pressureMbObject = currentOject.getInt("pressure_mb");
        pressureMb.icon = R.drawable.img_ap_suat_khong_khi;
        pressureMb.moTaIcon = "Áp suất";
        pressureMb.thongSoThoiTietDiaDiemHienTai = String.valueOf(pressureMbObject);
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(pressureMb);


        gridViewThongSoThoiTietDiaDiemHienTaiAdapter = new Phuoc_GridViewThongSoThoiTietDiaDiemHienTaiAdapter(this, R.layout.thong_so_thoi_tiet_dia_diem_hien_tai_item_layout, grvGridViewThongSoThoiTietDiaDiemHienTai);
        gvThongSoThoiTietDiaDiemHienTai.setAdapter(gridViewThongSoThoiTietDiaDiemHienTaiAdapter);

    }

    // BarChart
    // Phương thức lấy dữ liệu thông số thời tiết địa điểm hiện tại
    public void getAllDataForecastCurretnly(String url) {
        // Yêu cầu sử dụng thư viện volley
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Tạo dối tượng trong lớp StringRequest để lấy dữ liệu từ url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() { // Định nghĩa 1 trình nghe mới khi có phản hồi từ yêu cầu
                    @Override
                    public void onResponse(String response) { // Phương thức này được gọi khi có phản hồi từ yêu cầu
                        try {
                            parseJsonDataForecastCurretnly(response); // Phân tích dữ liệu JSON từ phản hồi
                            //  Log.d("ForecastData", "High Temperatures: " + lsNhietDoCaoNhat.toString());
                            // Log.d("ForecastData", "Low Temperatures: " + lsNhietDoThapNhat.toString());

                            setBarChart();
                        } catch (
                                JSONException e) { // Xử lý ngoại lệ trong quá trình phân tích dữ liệu
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // Khi có lỗi thì phương thức này sẽ được gọi để xử lý yêu cầu
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest); // Yêu cầu hàng đợi (trong trường hợp này là lấy yêu cầu từ file JSON)


    }

    // Phương thức phân tích dữ liệu JSON từ phản hồi
    public void parseJsonDataForecastCurretnly(String response) throws JSONException {
        // Tạo một đối tượng JSON chính từ chuỗi phản hồi
        JSONObject jsonRespond = new JSONObject(response);

        // Truy cập đối tượng forecast
        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");

        // Truy cập danh sách các ngày trong forecastday
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");

        // Lặp qua từng ngày trong danh sách
        for (int i = 0; i < forecastdayArray.length(); i++) {
            // Truy cập đối tượng day của từng ngày
            JSONObject dayObject = forecastdayArray.getJSONObject(i).getJSONObject("day");

            // Lấy thông số "Nhiệt độ cao nhất"
            double maxTempC = dayObject.getDouble("maxtemp_c");

            // Lấy thông số "Nhiệt độ thấp nhất"
            double minTempC = dayObject.getDouble("mintemp_c");

            // Thêm dữ liệu vào danh sách
            lsNhietDoCaoNhat.add(String.valueOf(maxTempC));
            lsNhietDoThapNhat.add(String.valueOf(minTempC));
        }

    }



    private void setBarChart(){
        // Khởi tạo BarChart
        barChart = findViewById(R.id.idBarChart);
        barChart.setBackgroundColor(Color.WHITE); // Thiết lập màu nền cho biểu đồ

        ArrayList<BarEntry> entries1 = new ArrayList<>();

        entries1.add(new BarEntry(1F, Float.parseFloat(lsNhietDoCaoNhat.get(0))));
        entries1.add(new BarEntry(2F, Float.parseFloat(lsNhietDoCaoNhat.get(1))));
        entries1.add(new BarEntry(3F, Float.parseFloat(lsNhietDoCaoNhat.get(2))));

/*        entries1.add(new BarEntry(1F,  20)); // Ngày 1
        entries1.add(new BarEntry(2, 15)); // Ngày 2
        entries1.add(new BarEntry(3, 20)); // Ngày 3*/


        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(1F, Float.parseFloat(lsNhietDoThapNhat.get(0))));
        entries2.add(new BarEntry(2F, Float.parseFloat(lsNhietDoThapNhat.get(1))));
        entries2.add(new BarEntry(3F, Float.parseFloat(lsNhietDoThapNhat.get(2))));

        /*entries2.add(new BarEntry(1, 15)); // Ngày 1
        entries2.add(new BarEntry(2, 10)); // Ngày 2
        entries2.add(new BarEntry(3, 10)); // Ngày 3
*/
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Chuyển giá trị float thành chuỗi theo ý bạn
                // Ví dụ: Nếu value là 1.0, bạn muốn hiển thị 'T2'
                // Các giá trị khác tương tự
                int intValue = (int) value;
                switch (intValue) {
                    case 1:
                        return "T2";
                    case 2:
                        return "T3";
                    case 3:
                        return "T4";
                    default:
                        return "";
                }
            }
        });




        BarDataSet bardataset1 = new BarDataSet(entries1, "Base");
        bardataset1.setColor(Color.parseColor("#FFA500"));
        // bardataset1.setValueTextColor(Color.WHITE);
        bardataset1.setValueTextSize(15);

        // Thêm ký hiệu độ trong độ C cho số liệu
        bardataset1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Chuyển về kiểu int nữa
                return String.format(value+ "°") ;
                //  return String.format("%d", (float)value) + "°";
            }
        });

        BarDataSet bardataset2 = new BarDataSet(entries2, "Top");
        bardataset2.setColor(Color.parseColor("#fffbfe"));
        // bardataset2.setValueTextColor(Color.WHITE);
        bardataset2.setValueTextSize(15);

        // Thêm ký hiệu độ trong độ C cho số liệu
        bardataset2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Chuyển về kiểu int nữa
                return String.format(value+ "°") ;
                // return String.format("%d", (float)value) + "°";
            }
        });

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bardataset1);
        dataSets.add(bardataset2);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.5f); // Thiết lập độ rộng cho các cột
        barChart.setData(data);


        // Đặt mô tả cho biểu đồ
        Description description = new Description();
        description.setText("Daily High and Low Temperatures");
        barChart.setDescription(description);

        // Tắt chức năng zoom
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);

        // Tắt các đường kẻ trên biểu đồ
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);

        // Thiết lập màu sắc cho số liệu trên các trục x và y
      /*  barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);
*/
        // Tạo hiệu ứng animating the y-axis của biểu đồ trong 5000 milliseconds (5 giây)
        // barChart.animateY(5000);
    }

    // Hàm kiểm tra xem url có thay đổi hay không, nếu có thì gọi lại làm lấy API

    //=============================PHƯỚC========================

}

