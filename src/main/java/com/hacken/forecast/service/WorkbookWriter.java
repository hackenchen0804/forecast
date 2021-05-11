package com.hacken.forecast.service;

import com.hacken.forecast.event.PrnWriterEvent;
import com.hacken.forecast.event.SheetWriterEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class WorkbookWriter {
    ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void write(List<Forecast> source, Workbook target,String sheetName) throws ForecastEx {
        if (target.getSheet(sheetName) != null) {
            target.removeSheetAt(target.getSheetIndex(sheetName));
        }
        Sheet sheet = target.createSheet(sheetName);
        int rowNumber = 0;
        for (Forecast forecast : source) {
            Row row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(forecast.getRowNumber());
            row.createCell(1).setCellValue(forecast.getItem());
            row.createCell(2).setCellValue(forecast.getPlanDate());
            row.createCell(3).setCellValue(forecast.getOrder());
            row.createCell(4).setCellValue(forecast.getLine());
            row.createCell(5).setCellValue(forecast.getSubLine());
            row.createCell(6).setCellValue(forecast.getQty());
            rowNumber++;
        }

        applicationContext.publishEvent(new SheetWriterEvent(this));
    }
}
