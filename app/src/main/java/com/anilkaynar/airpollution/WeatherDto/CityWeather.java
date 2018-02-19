package com.anilkaynar.airpollution.WeatherDto;

import java.util.ArrayList;

/**
 * Created by anilkaynar on 18.02.2018.
 */


public class CityWeather {
   public coords coord;
   public ArrayList<weatherDTO> weather;
    private CityWeather(){
  //Work as expected
    }
    @Override
    public String toString() {
        return "CityWeather{" +
                "coords=" + coord.lat +
                ", weather=" + weather.get(0).main +
                '}';
    }
}
