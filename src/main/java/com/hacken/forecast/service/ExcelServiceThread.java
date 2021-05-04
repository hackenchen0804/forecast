package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.util.BeanContext;
import javafx.scene.control.Alert;

import java.io.File;


public class ExcelServiceThread implements Runnable{
    File file;
    String sheetName;

    public ExcelServiceThread(File file,String sheetName){
        this.file = file;
        this.sheetName=sheetName;
    }

    private ExcelService excelService;

    @Override
    public void run() {
        excelService= BeanContext.getBean(ExcelServiceImpl.class);
        try {
            excelService.load(this.file,this.sheetName);
        } catch (ForecastEx ex) {
            ex.printStackTrace();
        }
    }

}
