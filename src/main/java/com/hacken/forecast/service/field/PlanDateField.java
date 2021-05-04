package com.hacken.forecast.service.field;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Map;

public class PlanDateField extends FieldTemplate<Date>{

    public PlanDateField(Row row, Map<String, Integer> titles) {
        super(row, "planDate", titles);
    }

    @Override
    public Date exportFromNum() throws ForecastEx {
        Date planDate = this.cell.getDateCellValue();
        if (planDate.before(new Date())){
            throw new ForecastEx(Status.DATEBEFORETODAY);
        }
        return planDate;
    }
}
