package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.model.Forecast;

import java.util.List;


public interface ForecastService {
    void validForecastInScala(Forecast forecast) throws ForecastEx;
    List<Forecast> getForecastsFromOrderLines(Forecast forecast) throws ForecastEx;
}
