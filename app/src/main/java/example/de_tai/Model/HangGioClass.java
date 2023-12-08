package example.de_tai.Model;

public class HangGioClass {
    public String time, temp_c, uv, humidity, dewpoint_c, wind_kph, text, icon, chance_of_rain;

    public HangGioClass(String time, String temp_c, String uv, String humidity, String dewpoint_c, String wind_kph, String text, String icon, String chance_of_rain) {
        this.time = time;
        this.temp_c = temp_c;
        this.uv = uv;
        this.humidity = humidity;
        this.dewpoint_c = dewpoint_c;
        this.wind_kph = wind_kph;
        this.text = text;
        this.icon = icon;
        this.chance_of_rain = chance_of_rain;
    }

    public HangGioClass(){

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDewpoint_c() {
        return dewpoint_c;
    }

    public void setDewpoint_c(String dewpoint_c) {
        this.dewpoint_c = dewpoint_c;
    }

    public String getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(String wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChance_of_rain() {
        return chance_of_rain;
    }

    public void setChance_of_rain(String chance_of_rain) {
        this.chance_of_rain = chance_of_rain;
    }
}
