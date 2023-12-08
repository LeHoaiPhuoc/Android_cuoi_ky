package example.de_tai.Model;

public class Phuoc_GridViewThongSoThoiTietDiaDiemHienTai {
    public int icon;
    public String moTaIcon;
    public String thongSoThoiTietDiaDiemHienTai;

    public Phuoc_GridViewThongSoThoiTietDiaDiemHienTai(int imgIcon, String moTaIcon, String thongSoThoiTietDiaDiemHienTai) {
        this.icon = imgIcon;
        this.moTaIcon = moTaIcon;
        this.thongSoThoiTietDiaDiemHienTai = thongSoThoiTietDiaDiemHienTai;
    }
    public Phuoc_GridViewThongSoThoiTietDiaDiemHienTai(){

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMoTaIcon() {
        return moTaIcon;
    }

    public void setMoTaIcon(String moTaIcon) {
        this.moTaIcon = moTaIcon;
    }

    public String getThongSoThoiTietDiaDiemHienTai() {
        return thongSoThoiTietDiaDiemHienTai;
    }

    public void setThongSoThoiTietDiaDiemHienTai(String thongSoThoiTietDiaDiemHienTai) {
        this.thongSoThoiTietDiaDiemHienTai = thongSoThoiTietDiaDiemHienTai;
    }
}
