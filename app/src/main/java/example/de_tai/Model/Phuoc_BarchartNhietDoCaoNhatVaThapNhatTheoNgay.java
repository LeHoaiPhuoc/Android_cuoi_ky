package example.de_tai.Model;

public class Phuoc_BarchartNhietDoCaoNhatVaThapNhatTheoNgay {
    public String nhietDoCaoNhat; // "maxtemp_c" trong JSON
    public String nhietDoThapNhat; // "mintemp_c" trong JSON

    public Phuoc_BarchartNhietDoCaoNhatVaThapNhatTheoNgay(String nhietDoCaoNhat, String nhietDoThapNhat) {
        this.nhietDoCaoNhat = nhietDoCaoNhat;
        this.nhietDoThapNhat = nhietDoThapNhat;
    }

    public Phuoc_BarchartNhietDoCaoNhatVaThapNhatTheoNgay(){

    }

    public String getNhietDoCaoNhat() {
        return nhietDoCaoNhat;
    }

    public void setNhietDoCaoNhat(String nhietDoCaoNhat) {
        this.nhietDoCaoNhat = nhietDoCaoNhat;
    }

    public String getNhietDoThapNhat() {
        return nhietDoThapNhat;
    }

    public void setNhietDoThapNhat(String nhietDoThapNhat) {
        this.nhietDoThapNhat = nhietDoThapNhat;
    }
}
