package com.hacken.forecast.service;

import com.hacken.forecast.event.AlertEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    Workbook workbook = null;
    List<Forecast> forecastList = null;
    CellStyle errorStyle;
    SheetService sheetService;
    ApplicationContext applicationContext;
    PrnWriter prnWriter;
    WorkbookWriter workbookWriter;


    @Override
    public void setErrorStyle(CellStyle errorStyle) {
        this.errorStyle = errorStyle;
    }


    @Value("${forecast.prn.outpath}")
    String outPrnPath;

    @Autowired
    public void setSheetService(SheetService sheetService) {
        this.sheetService = sheetService;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setPrnWriter(PrnWriter prnWriter) {
        this.prnWriter = prnWriter;
    }

    @Autowired
    public void setWorkbookWriter(WorkbookWriter workbookWriter) {
        this.workbookWriter = workbookWriter;
    }


    @Override
    public void load(String excelName, String sheetName) throws ForecastEx {
        filterExcelData(excelName, sheetName);
        writeWorkbookToExcel(excelName);
    }

    private void writeWorkbookToExcel(String excelName) {
        try (FileOutputStream excelFile = new FileOutputStream(excelName)) {
            this.workbook.write(excelFile);
        } catch (FileNotFoundException e) {
            applicationContext.publishEvent(new AlertEvent(new ForecastEx(Status.FILENOTEXISTS)));
        } catch (IOException e) {
            applicationContext.publishEvent(new AlertEvent(new ForecastEx(Status.WRONGFILEFORMAT)));
        }
    }

    private void filterExcelData(String excelName, String sheetName) throws ForecastEx {
        try (FileInputStream excelFile = new FileInputStream(excelName)) {
            initialWorkbook(excelFile);
            CellStyle errorStyle = setDefaultCellStyle();
            setErrorStyle(errorStyle);
            initialForecastList(sheetName, errorStyle);
            writeToTargets();
        } catch (FileNotFoundException e) {
            applicationContext.publishEvent(new AlertEvent(new ForecastEx(Status.FILENOTEXISTS)));
        } catch (IOException e) {
            applicationContext.publishEvent(new AlertEvent(new ForecastEx(Status.WRONGFILEFORMAT)));
        }
    }

    private void initialForecastList(String sheetName, CellStyle errorStyle) throws ForecastEx {
        this.forecastList = sheetService.process(this.workbook.getSheet(sheetName), errorStyle);
    }

    private void writeToTargets() throws ForecastEx {
        prnWriter.write(this.forecastList, new File(this.outPrnPath, "forecast.prn"));
        workbookWriter.write(this.forecastList, this.workbook);
    }

    private void initialWorkbook(FileInputStream excelFile) throws IOException {
        ZipSecureFile.setMinInflateRatio(-1.0d);
        this.workbook = new XSSFWorkbook(excelFile);
    }

    private CellStyle setDefaultCellStyle() {
        CellStyle errorStyle = this.workbook.createCellStyle();
        errorStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
        errorStyle.setFillPattern(FillPatternType.LESS_DOTS);
        return errorStyle;
    }
}
