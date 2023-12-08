package example.de_tai.View;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.de_tai.Controller.GridViewThongSoThoiTietDiaDiemHienTaiAdapter;
import example.de_tai.Model.GridViewThongSoThoiTietDiaDiemHienTai;
import example.de_tai.R;

public class GridViewActivity extends AppCompatActivity {
    GridView gvThongSoThoiTietDiaDiemHienTai; // lv hiển thị dữ liệu
    GridViewThongSoThoiTietDiaDiemHienTaiAdapter gridViewThongSoThoiTietDiaDiemHienTaiAdapter;
    ArrayList<GridViewThongSoThoiTietDiaDiemHienTai> grvGridViewThongSoThoiTietDiaDiemHienTai = new ArrayList<>();
    String url = "https://api.weatherapi.com/v1/forecast.json?key=0f4ce91ee1a24deebce53135232211&q=Hanoi&days=1&aqi=yes&alerts=no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        getAllDataArtist(url);
    }

    private void addControls() {
        gvThongSoThoiTietDiaDiemHienTai = findViewById(R.id.gvThongSoThoiTietDiaDiemHienTai);
    }

    // Phương thức lấy dữ liệu thông số thời tiết địa điểm hiện tại
    public void getAllDataArtist(String url) {
        // Yêu cầu sử dụng thư viện volley
        RequestQueue requestQueue = Volley.newRequestQueue(GridViewActivity.this);

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
    public void parseJsonData(String response) throws JSONException {


        // Tạo 1 đối tượng JSON chính từ chuỗi phản hồi
        JSONObject jsonRespond = new JSONObject(response);
        // Truy cập đối tượng current
        JSONObject currentOject = jsonRespond.getJSONObject("current");

        // Lấy thông số "Cảm giác như"
        // Tạo đối tượng lấy thông số
        GridViewThongSoThoiTietDiaDiemHienTai camGiacNhu = new GridViewThongSoThoiTietDiaDiemHienTai();
        int feelslikecObject = currentOject.getInt("feelslike_c");
        camGiacNhu.icon = R.drawable.img_cam_giac_nhu;
        camGiacNhu.moTaIcon = "Cảm giác như";
        camGiacNhu.thongSoThoiTietDiaDiemHienTai = feelslikecObject + "°C";
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(camGiacNhu);

        // Lấy thông số "Độ ẩm"
        GridViewThongSoThoiTietDiaDiemHienTai doAm = new GridViewThongSoThoiTietDiaDiemHienTai();
        int humidityObject = currentOject.getInt("humidity");
        doAm.icon = R.drawable.img_do_am;
        doAm.moTaIcon = "Độ ẩm";
        doAm.thongSoThoiTietDiaDiemHienTai = humidityObject + "%";
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(doAm);

        // Lấy thông số "Chỉ số uv"
        GridViewThongSoThoiTietDiaDiemHienTai uv = new GridViewThongSoThoiTietDiaDiemHienTai();
        int uvObject = currentOject.getInt("uv");
        uv.icon = R.drawable.img_uv;
        uv.moTaIcon = "Chỉ số uv";
        uv.thongSoThoiTietDiaDiemHienTai = String.valueOf(uvObject);
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(uv);

        // Lấy thông số "Tầm nhìn"
        GridViewThongSoThoiTietDiaDiemHienTai vis = new GridViewThongSoThoiTietDiaDiemHienTai();
        int visObject = currentOject.getInt("vis_km");
        vis.icon = R.drawable.img_tam_nhin;
        vis.moTaIcon = "Tầm nhìn";
        vis.thongSoThoiTietDiaDiemHienTai = String.valueOf(visObject + " Km");
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(vis);

        // Lấy thông số "Tốc độ gió"
        GridViewThongSoThoiTietDiaDiemHienTai windKph = new GridViewThongSoThoiTietDiaDiemHienTai();
        int windKphObject = currentOject.getInt("wind_kph");
        windKph.icon = R.drawable.img_toc_do_gio;
        windKph.moTaIcon = "Tốc độ gió";
        windKph.thongSoThoiTietDiaDiemHienTai = String.valueOf(windKphObject + " Km/h");
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(windKph);

        // Lấy thông số "Áp suất không khí"
        GridViewThongSoThoiTietDiaDiemHienTai pressureMb = new GridViewThongSoThoiTietDiaDiemHienTai();
        int pressureMbObject = currentOject.getInt("pressure_mb");
        pressureMb.icon = R.drawable.img_ap_suat_khong_khi;
        pressureMb.moTaIcon = "Áp suất";
        pressureMb.thongSoThoiTietDiaDiemHienTai = String.valueOf(pressureMbObject);
        grvGridViewThongSoThoiTietDiaDiemHienTai.add(pressureMb);


        gridViewThongSoThoiTietDiaDiemHienTaiAdapter = new GridViewThongSoThoiTietDiaDiemHienTaiAdapter(this, R.layout.thong_so_thoi_tiet_dia_diem_hien_tai_item_layout, grvGridViewThongSoThoiTietDiaDiemHienTai);
        gvThongSoThoiTietDiaDiemHienTai.setAdapter(gridViewThongSoThoiTietDiaDiemHienTaiAdapter);

    }
}