package com.hacken.forecast.event;

import com.hacken.forecast.service.PrnWriter;
import org.springframework.context.ApplicationEvent;

public class PrnWriterEvent extends ApplicationEvent {

    public PrnWriterEvent(PrnWriter prnWriter) {
        super(prnWriter);
    }

    public String msg(){
        return "写入Prn程序完成";
    }
}
