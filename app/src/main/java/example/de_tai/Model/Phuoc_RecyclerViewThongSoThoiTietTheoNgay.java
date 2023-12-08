package example.de_tai.Model;

public class Phuoc_RecyclerViewThongSoThoiTietTheoNgay {
    public String khaNangCoMuaTheoNgay; // daily_chance_of_rain trong JSON
    public String nameWeather; // "text" trong JSON
    public String tocDoGioTrungBinhTheoNgay; // Lấy wind_dir trong từng giờ rồi chia trung bình ra
    public int iconHuongGioTheoGio; // Trong file drawble
    public String thuTrongTuan; // Lấy "date" trong JSON rồi dùng thư viện tính ra

    public Phuoc_RecyclerViewThongSoThoiTietTheoNgay(String khaNangCoMuaTheoNgay, String nameWeather, String tocDoGioTrungBinhTheoNgay, int iconHuongGioTheoGio, String thuTrongTuan) {
        this.khaNangCoMuaTheoNgay = khaNangCoMuaTheoNgay;
        this.nameWeather = nameWeather;
        this.tocDoGioTrungBinhTheoNgay = tocDoGioTrungBinhTheoNgay;
        this.iconHuongGioTheoGio = iconHuongGioTheoGio;
        this.thuTrongTuan = thuTrongTuan;
    }

    public Phuoc_RecyclerViewThongSoThoiTietTheoNgay(){

    }
    public String getKhaNangCoMuaTheoNgay() {
        return khaNangCoMuaTheoNgay;
    }

    public void setKhaNangCoMuaTheoNgay(String khaNangCoMuaTheoNgay) {
        this.khaNangCoMuaTheoNgay = khaNangCoMuaTheoNgay;
    }

    public String getNameWeather() {
        return nameWeather;
    }

    public void setNameWeather(String nameWeather) {
        this.nameWeather = nameWeather;
    }

    public String getTocDoGioTrungBinhTheoNgay() {
        return tocDoGioTrungBinhTheoNgay;
    }

    public void setTocDoGioTrungBinhTheoNgay(String tocDoGioTrungBinhTheoNgay) {
        this.tocDoGioTrungBinhTheoNgay = tocDoGioTrungBinhTheoNgay;
    }

    public int getIconHuongGioTheoGio() {
        return iconHuongGioTheoGio;
    }

    public void setIconHuongGioTheoGio(int iconHuongGioTheoGio) {
        this.iconHuongGioTheoGio = iconHuongGioTheoGio;
    }

    public String getThuTrongTuan() {
        return thuTrongTuan;
    }

    public void setThuTrongTuan(String thuTrongTuan) {
        this.thuTrongTuan = thuTrongTuan;
    }
}
