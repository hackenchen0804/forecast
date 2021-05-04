package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

public class FieldTemplate<T> {
    Cell cell;
    String fieldName;

    public FieldTemplate(Row row, String fieldName, Map<String, Integer> titles) {
        int columnIndex = titles.getOrDefault(fieldName, -1);
        if(columnIndex!=-1) {
            this.cell = row.getCell(columnIndex);
            this.fieldName = fieldName;
        }
    }

    public T export() throws ForecastEx {
        if (this.cell != null) {
            CellType cellType = this.cell.getCellType();

            switch (cellType) {
                case STRING:
                    return exportFromString();
                case NUMERIC:
                    return exportFromNum();
                case BLANK:
                    return exportFromBlank();
                case _NONE:
                    return exportFromNone();
                case ERROR:
                    return exportFromError();
                case BOOLEAN:
                    return exportFromBoolean();
                case FORMULA:
                    return exportFromFormula();
                default:
                    throw new ForecastEx(Status.CELLFORMAT);
            }
        } else {
            throw new ForecastEx(Status.CELLFORMAT);
        }


    }

    public T exportFromString() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromNum() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromBlank() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromNone() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromError() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromBoolean() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

    public T exportFromFormula() throws ForecastEx {
        throw new ForecastEx(Status.CELLFORMAT);
    }

}
