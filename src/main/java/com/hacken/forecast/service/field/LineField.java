package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.util.Map;

public class LineField extends FieldTemplate<Integer>{

    public LineField(Row row, Map<String, Integer> titles) {
        super(row, "line", titles);
    }

    @Override
    public Integer exportFromString() throws ForecastEx {
        try {
            return Integer.parseInt(this.cell.getStringCellValue());
        }catch (Exception e){
            throw  new ForecastEx(Status.CELLFORMAT);
        }
    }

    @Override
    public Integer exportFromNum() throws ForecastEx {
        Double result = this.cell.getNumericCellValue();
        return result.intValue();
    }
}
