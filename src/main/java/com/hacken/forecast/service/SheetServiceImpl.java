package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SheetServiceImpl implements SheetService {
    Sheet sheet;
    Iterator<Row> rowIterator;
    Map<String, Integer> excelTitles = new HashMap<>();

    ExcelRowService excelRowService;

    @Autowired
    public void setExcelRowService(ExcelRowService excelRowService) {
        this.excelRowService = excelRowService;
    }

    @Override
    public void loadSheet(Sheet sheet) throws ForecastEx {
        if (sheet == null) {
            throw new ForecastEx(Status.SHEETCANNOTREAD);
        }

        this.sheet = sheet;
        this.rowIterator = this.sheet.rowIterator();
        getExcelTitle(rowIterator.next());
    }

    @Override
    public void getExcelTitle(Row titleRow) throws ForecastEx {
        if (titleRow == null) {
            throw new ForecastEx(Status.EXCELTITLE);
        }

        Iterator<Cell> cellIterator = titleRow.cellIterator();
        while(cellIterator.hasNext()){
            Cell titleCell = cellIterator.next();
            if (titleCell.getCellType()==CellType.STRING){
                String value = titleCell.getStringCellValue();
                this.excelTitles.put(value,titleCell.getColumnIndex());
            }
        }
        if(!this.excelTitles.keySet().containsAll(getStandardTitles())){
            throw new ForecastEx(Status.EXCELTITLE);
        }
    }


    private Set<String> getStandardTitles() {
        Set<String> fieldsInForecast = new HashSet<>();
        fieldsInForecast.add("order");
        fieldsInForecast.add("line");
        fieldsInForecast.add("qty");
        fieldsInForecast.add("planDate");
        fieldsInForecast.add("item");
        return fieldsInForecast;
    }

    @Override
    public List<Forecast> getAllForecastFromRow(CellStyle errorStyle) {
        List<Forecast> result = new ArrayList<>();
        while (this.rowIterator.hasNext()) {
            Row row = rowIterator.next();
            try {
                result.addAll(excelRowService.mapRowToForecast(row, this.excelTitles));
            } catch (ForecastEx ex) {
                setErrorRow(row, errorStyle, ex.msgBody());
            }
        }
        return result;
    }

    @Override
    public List<Forecast> process(Sheet sheet, CellStyle errorStyle) throws ForecastEx{
        loadSheet(sheet);
        return getAllForecastFromRow(errorStyle);
    }

    private void setErrorRow(Row row, CellStyle cellStyle, String msg) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            cellIterator.next().setCellStyle(cellStyle);
        }
        addErrorReason(row, msg);
    }

    private void addErrorReason(Row row, String msg) {
        Cell cellReason = row.createCell(row.getLastCellNum());
        cellReason.setCellValue(msg);
    }
}
