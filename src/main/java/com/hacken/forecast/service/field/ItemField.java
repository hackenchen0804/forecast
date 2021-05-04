package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.util.Map;

public class ItemField extends FieldTemplate<String>{

    public ItemField(Row row, Map<String, Integer> titles) {
        super(row, "item", titles);
    }

    @Override
    public String exportFromString() throws ForecastEx {
        return this.cell.getStringCellValue();
    }

    @Override
    public String exportFromNum() throws ForecastEx {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(this.cell.getNumericCellValue());
    }
}
