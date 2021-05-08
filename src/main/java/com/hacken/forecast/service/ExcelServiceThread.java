package com.hacken.forecast.service;

import com.hacken.forecast.event.AlertEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.util.BeanContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.File;


public class ExcelServiceThread implements Runnable {
    String excelName;
    String sheetName;

    @Autowired
    ApplicationContext applicationContext;

    public ExcelServiceThread(String excelName, String sheetName) {
        this.excelName = excelName;
        this.sheetName = sheetName;
    }

    private ExcelService excelService;

    @Override
    public void run() {
        excelService = BeanContext.getBean(ExcelServiceImpl.class);
        try {
            excelService.load(this.excelName, this.sheetName);
        } catch (ForecastEx forecastEx) {
            applicationContext.publishEvent(new AlertEvent(forecastEx));
        }
    }

}
