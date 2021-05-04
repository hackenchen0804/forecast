package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

public interface SheetService {
    void loadSheet(Sheet sheet) throws ForecastEx;
    void getExcelTitle(Row titleRow) throws ForecastEx;
    List<Forecast> getAllForecastFromRow(CellStyle errorStyle);
    List<Forecast> process(Sheet sheet,CellStyle errorStyle) throws ForecastEx;
}
