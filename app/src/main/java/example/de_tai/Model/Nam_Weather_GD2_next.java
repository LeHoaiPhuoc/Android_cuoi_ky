package example.de_tai.Model;

public class Nam_Weather_GD2_next {
    public Nam_Weather_GD2_next() {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moonphase = moonphase;
        this.wind_kph = wind_kph;
        this.win_dir = win_dir;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoonphase() {
        return moonphase;
    }

    public void setMoonphase(String moonphase) {
        this.moonphase = moonphase;
    }

    public String getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(String wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getWin_dir() {
        return win_dir;
    }

    public void setWin_dir(String win_dir) {
        this.win_dir = win_dir;
    }

    public String sunrise, sunset, moonrise, moonset, moonphase;
    public String wind_kph, win_dir;


}
