package com.hacken.forecast.service;

import com.hacken.forecast.event.FinishEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService{
    File excelFile = null;
    Workbook workbook = null;
    List<Forecast> forecastList=null;

    CellStyle errorStyle;

    @Override
    public void setErrorStyle(CellStyle errorStyle) {
        this.errorStyle = errorStyle;
    }

    SheetService sheetService;

    @Autowired
    public void setSheetService(SheetService sheetService) {
        this.sheetService = sheetService;
    }

    ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    PrnWriter prnWriter;

    @Autowired
    public void setPrnWriter(PrnWriter prnWriter) {
        this.prnWriter = prnWriter;
    }

    WorkbookWriter workbookWriter;

    @Autowired
    public void setWorkbookWriter(WorkbookWriter workbookWriter) {
        this.workbookWriter = workbookWriter;
    }

    @Override
    public void export() throws ForecastEx {
        prnWriter.write(this.forecastList,new File(this.excelFile.getParentFile(),"forecast.prn"));
        workbookWriter.write(this.forecastList,this.workbook);
    }

    @Override
    public void load(File excelFile, String sheetName) throws ForecastEx {
        this.excelFile = excelFile;

        ZipSecureFile.setMinInflateRatio(-1.0d);

        try {
            setWorkbook(excelFile);
            setDefaultCellStyle();
            setAllForecast(sheetName);
            export();
            saveAndClose();
            applicationContext.publishEvent(new FinishEvent(this));
        } catch (ForecastEx ex) {
            throw ex;
        } catch (IOException ioException) {
            throw new ForecastEx(Status.FILENOTEXISTS);
        } catch (InvalidFormatException e) {
            throw new ForecastEx(Status.WRONGFILEFORMAT);
        } catch (Exception e){
            throw new ForecastEx(Status.WRONGFILEFORMAT);
        }

    }

    private void setAllForecast(String sheetName) throws ForecastEx {
        this.forecastList = sheetService.process(this.workbook.getSheet(sheetName),errorStyle);
    }

    private void setWorkbook(File excelFile) throws IOException, InvalidFormatException {
        ZipSecureFile.setMinInflateRatio(-1.0d);
        this.workbook = new XSSFWorkbook(excelFile);
    }

    private void setDefaultCellStyle(){
        CellStyle errorStyle = this.workbook.createCellStyle();
        errorStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
        errorStyle.setFillPattern(FillPatternType.LESS_DOTS);
        setErrorStyle(errorStyle);
    }


    @Override
    public void saveAndClose() throws IOException {
        if (excelFile.exists()) {
            excelFile.delete();
        }
        try (OutputStream outputStream = new FileOutputStream(excelFile)) {
            this.workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (this.workbook != null) {
                this.workbook.close();
            }
        }
    }


}
