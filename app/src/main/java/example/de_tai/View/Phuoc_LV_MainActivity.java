package example.de_tai.View;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import example.de_tai.Controller.Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter;
import example.de_tai.Model.Phuoc_RecyclerViewThongSoThoiTietTheoNgay;
import example.de_tai.R;

public class Phuoc_LV_MainActivity extends AppCompatActivity {
    RecyclerView rcvThongSoThoiTietTheoNgay;
    Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter recyclerViewThongSoThoiTietTheoNgayAdapter;
    ArrayList<Phuoc_RecyclerViewThongSoThoiTietTheoNgay> lsRecyclerViewThongSoThoiTietTheoNgay = new ArrayList<>();
    String url = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q=Hanoi&days=3&aqi=yes&alerts=no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phuoc_lv_activity_main);

        addControl();
        getDataDailyForecast(url);

    }

    private void addControl(){
        rcvThongSoThoiTietTheoNgay = findViewById(R.id.rcvThongSoThoiTietTheoNgay);
    }

    // Phương thức lấy dữ liệu thông số thời tiết
    public void getDataDailyForecast(String url) {
        // Yêu cầu sử dụng thư viện volley
        RequestQueue requestQueue = Volley.newRequestQueue(Phuoc_LV_MainActivity.this);

        // Tạo dối tượng trong lớp StringRequest để lấy dữ liệu từ url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() { // Định nghĩa 1 trình nghe mới khi có phản hồi từ yêu cầu
                    @Override
                    public void onResponse(String response) { // Phương thức này được gọi khi có phản hồi từ yêu cầu
                        try {
                            parseJsonData(response); // Phân tích dữ liệu JSON từ phản hồi
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
    public void parseJsonData (String response) throws JSONException {


        // Tạo 1 đối tượng JSON chính từ chuỗi phản hồi
        JSONObject jsonRespond = new JSONObject(response);

        // Truy cập đối tượng forecast
        JSONObject forecastObject = jsonRespond.getJSONObject("forecast");

        // Truy cập mảng forecastday
        JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");

        // Lặp qua từng đối tượng trong mảng forecastday
        for(int i = 0; i<forecastdayArray.length(); i++){
            Phuoc_RecyclerViewThongSoThoiTietTheoNgay recyclerViewThongSoThoiTietTheoNgay = new Phuoc_RecyclerViewThongSoThoiTietTheoNgay();

            // Lấy đối tượng trong mảng forecastday
            JSONObject forecastdayObject = forecastdayArray.getJSONObject(i);

            // Lấy đối tượng "date" để chuyển qua thứ
            String dateObject = forecastdayObject.getString("date");
            // Lấy xong rồi chuyển nó thành thứ trong tuần
            String dateString = dateObject;

            // Sử dụng hàm để lấy thứ trong tuần
            String dayOfWeek = getDayOfWeek(dateString);

            recyclerViewThongSoThoiTietTheoNgay.setThuTrongTuan(dayOfWeek);

            // Truy cập dối tượng "day" để lấy tốc độ gió lớn nhất
            JSONObject dayObject = forecastdayObject.getJSONObject("day");
            String maxWindKph = dayObject.getString("maxwind_mph");
            recyclerViewThongSoThoiTietTheoNgay.setTocDoGioTrungBinhTheoNgay(maxWindKph + "Km/h");

            // Truy cập dối tượng "daily_chance_of_rain" để lấy khả năng có mưa theo ngày
            String dailyChainceOfRain = dayObject.getString("daily_chance_of_rain");
            recyclerViewThongSoThoiTietTheoNgay.setKhaNangCoMuaTheoNgay(dailyChainceOfRain + "%");
            // Truy cập vào đối tượng condition trong đối tượng day
            JSONObject conditionObject = dayObject.getJSONObject("condition");
            // Truy cập đối tượng text trong đối tượng condition
            String textObject = conditionObject.getString("text");
            recyclerViewThongSoThoiTietTheoNgay.setNameWeather(textObject);
            recyclerViewThongSoThoiTietTheoNgay.setIconHuongGioTheoGio(R.drawable.baseline_wind_power_24);


            // Truy cập mảng hour bên trong đối tượng forecastday
           // JSONArray hourArray = forecastdayObject.getJSONArray("hour");

            // Lặp qua từng đối tượng trong mảng hour để lấy nhiệt độ hàng giờ
            /*for( int j = 0; j<hourArray.length(); j++){

                JSONObject hourObject = hourArray.getJSONObject(j);

                // Lấy thông tin nhiệt độ từ đối tượng hourOject
                //double tempeartureCTamp = hourObject.getDouble("temp_c");
                //a.nhietDoTheoGio = String.valueOf(tempeartureCTamp);

                //int chanceOfRainTamp = hourObject.getInt("chance_of_rain");
                //a.khaNangCoMuaTheoGio = String.valueOf(chanceOfRainTamp);

                // Truy cập vào đối tượng condition để lấy link icon
                JSONObject conditionObject_2 = hourObject.getJSONObject("condition");

                // Lấy giá trị của icon
              //  recyclerViewThongSoThoiTietTheoNgay.setIconLink(conditionObject.getString("icon"));
                // Lấy giá trị của icon
                // Lấy giá trị của icon
              //  String nameWeather = conditionObject.getString("text");

             //   recyclerViewThongSoThoiTietTheoNgay.setNameWeather(nameWeather);
                // Set tạm hình ảnh hướng gió lên trước
                recyclerViewThongSoThoiTietTheoNgay.setIconHuongGioTheoGio(R.drawable.baseline_wind_power_24);



                lsRecyclerViewThongSoThoiTietTheoNgay.add(recyclerViewThongSoThoiTietTheoNgay);
            }*/
            lsRecyclerViewThongSoThoiTietTheoNgay.add(recyclerViewThongSoThoiTietTheoNgay);
        }

        recyclerViewThongSoThoiTietTheoNgayAdapter = new Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter(lsRecyclerViewThongSoThoiTietTheoNgay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvThongSoThoiTietTheoNgay.setLayoutManager(layoutManager);
        rcvThongSoThoiTietTheoNgay.setAdapter(recyclerViewThongSoThoiTietTheoNgayAdapter);
        recyclerViewThongSoThoiTietTheoNgayAdapter.notifyDataSetChanged();
    }


    // Hàm chuyển chuỗi ngày thành thứ trong tuần
    private String getDayOfWeek(String dateString) {
        // Định dạng của chuỗi ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Chuyển đổi chuỗi thành đối tượng Date
            Date date = dateFormat.parse(dateString);

            // Định dạng mới để hiển thị thứ trong tuần
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

            // Lấy thứ trong tuần từ đối tượng Date
            return dayFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi chuyển đổi
        }
    }
}