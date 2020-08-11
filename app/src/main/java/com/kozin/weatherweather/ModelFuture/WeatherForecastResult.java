package com.kozin.weatherweather.ModelFuture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kozin.weatherweather.ModelCurrent.*;

import java.util.List;

public class WeatherForecastResult {
//    @SerializedName("cod")
//    @Expose
    public String cod;
//    @SerializedName("message")
//    @Expose
    public int message;
//    @SerializedName("cnt")
//    @Expose
    public int cnt;
//    @SerializedName("list")
//    @Expose
    public List<MyList> list;
//    @SerializedName("city")
//    @Expose
    public City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<MyList> getList() {
        return list;
    }

    public void setList(List<MyList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


}
