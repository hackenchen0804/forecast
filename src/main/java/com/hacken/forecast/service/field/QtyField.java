package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

public class QtyField extends FieldTemplate<Double> {

    public QtyField(Row row, Map<String, Integer> titles) {
        super(row, "qty", titles);
    }

    @Override
    public Double exportFromString() throws ForecastEx {
        try {
            return Double.parseDouble(this.cell.getStringCellValue());
        } catch (Exception e) {
            throw new ForecastEx(Status.CELLFORMAT);
        }
    }

    @Override
    public Double exportFromNum() throws ForecastEx {
        return this.cell.getNumericCellValue();
    }
}
