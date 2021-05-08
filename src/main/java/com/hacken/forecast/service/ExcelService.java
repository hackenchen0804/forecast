package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;

public interface ExcelService {
    void load(String excelName, String sheetName) throws ForecastEx;
    void setErrorStyle(CellStyle errorStyle);
}
