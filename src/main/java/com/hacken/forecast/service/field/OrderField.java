package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.util.Map;

public class OrderField extends FieldTemplate<Long>{

    public OrderField(Row row, Map<String, Integer> titles) {
        super(row, "order", titles);
    }

    @Override
    public Long exportFromString() throws ForecastEx {
        try {
            return Long.parseLong(this.cell.getStringCellValue());
        }catch (Exception e){
            throw  new ForecastEx(Status.CELLFORMAT);
        }
    }

    @Override
    public Long exportFromNum() throws ForecastEx {
        Double result = this.cell.getNumericCellValue();
        return result.longValue();
    }
}
