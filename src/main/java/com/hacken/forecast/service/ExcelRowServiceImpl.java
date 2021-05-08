package com.hacken.forecast.service;

import com.hacken.forecast.event.ExcelRowEvent;
import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.model.Forecast;
import com.hacken.forecast.service.field.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExcelRowServiceImpl implements ExcelRowService {

    ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    ForecastService forecastService;

    @Autowired
    public void setForecastService(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @Override
    public List<Forecast> mapRowToForecast(Row row, Map<String, Integer> title) throws ForecastEx {
        applicationContext.publishEvent(new ExcelRowEvent(row));
        Forecast forecast = createForecastFromRow(row, title);
        return forecastService.getForecastsFromOrderLines(forecast);
    }

    @Override
    public Forecast createForecastFromRow(Row row, Map<String, Integer> title) throws ForecastEx {
        Forecast forecast = new Forecast();
        initialForecastInstance(row, title, forecast);
        return forecast;
    }

    private void initialForecastInstance(Row row, Map<String, Integer> title, Forecast forecast) throws ForecastEx {
        forecast.setItem(new ItemField(row, title).export());
        forecast.setOrder(new OrderField(row, title).export());
        forecast.setLine(new LineField(row, title).export());
        forecast.setQty(new QtyField(row, title).export());
        forecast.setPlanDate(new PlanDateField(row, title).export());
        forecast.setRowNumber(row.getRowNum()+1);
        forecast.setSubLine(0);
    }
}
