package com.hacken.forecast.event;

import com.hacken.forecast.service.WorkbookWriter;
import org.springframework.context.ApplicationEvent;

public class SheetWriterEvent extends ApplicationEvent {

    public SheetWriterEvent(WorkbookWriter workbookWriter) {
        super(workbookWriter);
    }

    public String msg(){
        return "写入Sheet程序完成";
    }
}
