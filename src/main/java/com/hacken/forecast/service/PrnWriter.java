package com.hacken.forecast.service;

import com.hacken.forecast.event.PrnWriterEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PrnWriter {
    ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void write(List<Forecast> source, File target) throws ForecastEx {

        try {
            FileWriter fileWriter = new FileWriter(target, true);
            for (Forecast forecast : source) {
                String item = forecast.getItem();
                SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
                String date = df.format(forecast.getPlanDate());
                Double qty = forecast.getQty();
                String out = String.format("%-35s%-8s%-20.2f\n", item, date, qty);
                fileWriter.append(out);
            }
            fileWriter.flush();
            fileWriter.close();
            applicationContext.publishEvent(new PrnWriterEvent(this));
        } catch (IOException ioException) {
            throw new ForecastEx(Status.NOTWRITETOPRN);
        }
    }

}
