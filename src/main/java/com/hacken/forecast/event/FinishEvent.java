package com.hacken.forecast.event;

import com.hacken.forecast.service.ExcelService;
import org.springframework.context.ApplicationEvent;

public class FinishEvent extends ApplicationEvent {
    public FinishEvent(ExcelService excelService) {
        super(excelService);
    }

    public String msg(){
        return "处理完成";
    }
}
