package com.hacken.forecast.event;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationEvent;

public class ExcelRowEvent extends ApplicationEvent {
    Row row;
    public ExcelRowEvent(Row row) {
        super(row);
        this.row = row;
    }

    public String msg(){
        return "开始处理第"+this.row.getRowNum()+"行";
    }
}
