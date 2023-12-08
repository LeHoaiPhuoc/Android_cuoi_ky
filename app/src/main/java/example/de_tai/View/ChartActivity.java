package example.de_tai.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import example.de_tai.Model.HangGioClass;
import example.de_tai.R;

public class ChartActivity extends AppCompatActivity {

    ArrayList<HangGioClass> lsHangGio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        String test = intent.getStringExtra("MY_STRING");
        String url = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q="+test+"&days=3&aqi=yes&alerts=no";
        getAllDataArtist(url);
    }

    public void getAllDataArtist(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(ChartActivity.this);
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

    public void parseJsonData(String response) throws JSONException {
        JSONObject jsonRespond = new JSONObject(response);
        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");

        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int hoursAdded = 0;

        List<BarEntry> entriesWind = new ArrayList<>();
        List<Entry> entriesRain = new ArrayList<>();
        List<Entry> entriesHumid = new ArrayList<>();
        List<Entry> entriesDewpoint = new ArrayList<>();
        List<Entry> entriesTempC = new ArrayList<>();
        List<BarEntry> entriesUvNum = new ArrayList<>();

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

                if (hoursAdded >= 24) {
                    break;
                }

                HangGioClass a = new HangGioClass(); // Tạo 1 đối tượng thông số thời tiết mới
                JSONObject conditionObject = hourObject.getJSONObject("condition");

                double tempC = hourObject.getDouble("temp_c");
                a.temp_c = String.valueOf(tempC) + " °C";
                entriesTempC.add(new Entry(hoursAdded, (float) tempC));

                int uvNum = hourObject.getInt("uv");
                a.uv = "Chỉ số UV: " + String.valueOf(uvNum);
                entriesUvNum.add(new BarEntry(hoursAdded, uvNum));

                int dewpoint = hourObject.getInt("dewpoint_c");
                a.dewpoint_c = "Điểm sương: " + String.valueOf(dewpoint);
                entriesDewpoint.add(new Entry(hoursAdded, dewpoint));

                int humid = hourObject.getInt("humidity");
                a.humidity = "Độ ẩm: " + String.valueOf(humid);
                entriesHumid.add(new Entry(hoursAdded, humid));

                double wind = hourObject.getDouble("wind_kph");
                a.wind_kph = String.valueOf(wind) + " km/h";
                entriesWind.add(new BarEntry(hoursAdded, (float) tempC));

                String textt = conditionObject.getString("text");
                a.text = textt;

                a.time = fulltime;

                String iconn = conditionObject.getString("icon");
                a.icon = "https:" + iconn;

                int rain = hourObject.getInt("chance_of_rain");
                a.chance_of_rain = String.valueOf(rain);
                entriesRain.add(new Entry(hoursAdded, rain));
                lsHangGio.add(a);
                hoursAdded++;
            }
        }
//        hangGioAdapter = new HangGioAdapter(getApplicationContext(), R.layout.layout_hanggio_final, lsHangGio);
//        lvHangGio.setAdapter(hangGioAdapter);

        BarChart chartWind = findViewById(R.id.barChart);
        BarDataSet dataSetWind = new BarDataSet(entriesWind, "Wind Speed");
        BarData barDataWind = new BarData(dataSetWind);
        chartWind.setData(barDataWind);
        chartWind.invalidate(); // refresh

        LineChart chartRain = findViewById(R.id.lineChart);
        LineDataSet dataSetRain = new LineDataSet(entriesRain, "Chance of Rain");
        LineData lineDataRain = new LineData(dataSetRain);
        chartRain.setData(lineDataRain);
        chartRain.invalidate(); // refresh

        LineChart chartHumid = findViewById(R.id.chart_humid);
        LineDataSet dataSetHumid = new LineDataSet(entriesHumid, "Humidity");
        LineData lineDataHumid = new LineData(dataSetHumid);
        chartHumid.setData(lineDataHumid);
        chartHumid.invalidate(); // refresh

        LineChart chartDewpoint = findViewById(R.id.chart_dewpoint);
        LineDataSet dataSetDewpoint = new LineDataSet(entriesDewpoint, "Dewpoint");
        LineData lineDataDewpoint = new LineData(dataSetDewpoint);
        chartDewpoint.setData(lineDataDewpoint);
        chartDewpoint.invalidate(); // refresh

        LineChart chartTempC = findViewById(R.id.chart_tempC);
        LineDataSet dataSetTempC = new LineDataSet(entriesTempC, "Temperature");
        LineData lineDataTempC = new LineData(dataSetTempC);
        chartTempC.setData(lineDataTempC);
        chartTempC.invalidate(); // refresh

        BarChart chartUvNum = findViewById(R.id.chart_uvNum);
        BarDataSet dataSetUvNum = new BarDataSet(entriesUvNum, "UV Number");
        BarData barDataUvNum = new BarData(dataSetUvNum);
        chartUvNum.setData(barDataUvNum);
        chartUvNum.invalidate(); // refresh
    }


}