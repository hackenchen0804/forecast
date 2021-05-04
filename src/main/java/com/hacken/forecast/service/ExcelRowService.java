package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;

public interface ExcelRowService {
    List<Forecast> mapRowToForecast(Row row, Map<String, Integer> title) throws ForecastEx;
    Forecast createForecastFromRow(Row row, Map<String, Integer> title) throws ForecastEx;
}
