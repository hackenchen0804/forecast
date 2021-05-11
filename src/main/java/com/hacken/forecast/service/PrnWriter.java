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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrnWriter {
    ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void write(List<Forecast> source, File target) throws ForecastEx {
        List<Forecast> summaryForecast= summaryForecast(source);

        try {
            FileWriter fileWriter = new FileWriter(target, true);
            for (Forecast forecast : summaryForecast) {
                String item = forecast.getItem();
                SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
                String date = df.format(forecast.getPlanDate());
                Double qty = forecast.getQty();
                String out = String.format("%-35s%-6s%-20.2f\n", item, date, qty);
                fileWriter.append(out);
            }
            fileWriter.flush();
            fileWriter.close();
            applicationContext.publishEvent(new PrnWriterEvent(this));
        } catch (IOException ioException) {
            throw new ForecastEx(Status.NOTWRITETOPRN);
        }
    }

    private List<Forecast> summaryForecast(List<Forecast> source) {
        Map<String,Forecast> result = new HashMap<>();
        for (Forecast forecast:source){
            String item = forecast.getItem();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
            String dateString = simpleDateFormat.format(forecast.getPlanDate());
            String key = item+dateString;
            if(result.containsKey(key)){
                Forecast tempForecast = result.get(key);
                tempForecast.setQty(tempForecast.getQty()+forecast.getQty());
            }else{
                result.put(key,forecast);
            }
        }
        return new ArrayList<>(result.values());
    }

}
