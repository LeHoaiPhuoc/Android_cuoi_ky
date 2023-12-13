package example.de_tai.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.util.Calendar;

import example.de_tai.Controller.Tuong_HangGioAdapter;
import example.de_tai.Model.HangGioClass;
import example.de_tai.R;

public class HangGioActivity extends AppCompatActivity {

    ListView lvHangGio;

    ImageButton imgBack1;
    Tuong_HangGioAdapter hangGioAdapter;
    ArrayList<HangGioClass> lsHangGio = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_gio);
        lvHangGio = (ListView) findViewById(R.id.lvHangGio);
        imgBack1 = (ImageButton) findViewById(R.id.imgBack1);

        setBackground();
        //-----------------------------------

        Intent intent = getIntent();
        String test2 = intent.getStringExtra("MY_STRING_2");
        String url = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q="+test2+"&days=3&aqi=yes&alerts=no";
        getAllDataArtist(url);


        imgBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getAllDataArtist(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(HangGioActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJsonData(response);
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

    public void setBackground(){
        RelativeLayout relativeLayout = findViewById(R.id.hanggio_layout);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if(currentHour >= 5 && currentHour <= 11) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_morning);
            relativeLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if (currentHour >= 12 && currentHour <= 16) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_afternoon);
            relativeLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if(currentHour >= 17 && currentHour <= 19) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_evening);
            relativeLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        } else if (currentHour >= 20 && currentHour <= 4) {
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.gradient_list_night);
            relativeLayout.setBackground(animationDrawable);
            animationDrawable.setEnterFadeDuration(2500);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();
        }
    }

    public void parseJsonData(String response) throws JSONException {
        JSONObject jsonRespond = new JSONObject(response);
        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");

        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int hoursAdded = 0;

        for (int i = 0; i < forecastdayArray.length(); i++) {
            JSONObject forecastdayObject = forecastdayArray.getJSONObject(i);
            JSONArray hourArray = forecastdayObject.getJSONArray("hour");
            for (int j = 0; j < hourArray.length(); j++) {
                JSONObject hourObject = hourArray.getJSONObject(j);
                String fulltime = hourObject.getString("time");
                int hour = Integer.parseInt(fulltime.substring(11, 13));

                if (i == 0 && hour < currentHour) {
                    continue;
                }

                if (hoursAdded >= 48) {
                    break;
                }

                HangGioClass a = new HangGioClass(); // Tạo 1 đối tượng thông số thời tiết mới
                JSONObject conditionObject = hourObject.getJSONObject("condition");
                double tempC = hourObject.getDouble("temp_c");
                a.temp_c = String.valueOf(tempC) + " °C";

                int uvNum = hourObject.getInt("uv");
                a.uv = "Chỉ số UV: " + String.valueOf(uvNum);

                int dewpoint = hourObject.getInt("dewpoint_c");
                a.dewpoint_c = "Điểm sương: " + String.valueOf(dewpoint);

                int humid = hourObject.getInt("humidity");
                a.humidity = "Độ ẩm: " + String.valueOf(humid);

                double wind = hourObject.getDouble("wind_kph");
                a.wind_kph = String.valueOf(wind) + " km/h";

                String textt = conditionObject.getString("text");
                a.text = textt;

//                String timeOnly = fulltime.substring(11, 16);
                a.time = fulltime;

                String iconn = conditionObject.getString("icon");
                a.icon = "https:" + iconn;

                int rain = hourObject.getInt("chance_of_rain");
                a.chance_of_rain = String.valueOf(rain) + "%";

                lsHangGio.add(a);
                hoursAdded++;
            }
        }
        hangGioAdapter = new Tuong_HangGioAdapter(getApplicationContext(), R.layout.layout_hanggio_final, lsHangGio);
        lvHangGio.setAdapter(hangGioAdapter);
    }


}