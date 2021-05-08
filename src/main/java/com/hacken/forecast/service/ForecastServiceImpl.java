package com.hacken.forecast.service;

import com.hacken.forecast.exception.ForecastEx;
import com.hacken.forecast.exception.Status;
import com.hacken.forecast.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService {
    private ScalaService scalaService;

    @Autowired
    public void setScalaService(ScalaService scalaService) {
        this.scalaService = scalaService;
    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void validForecastInScala(Forecast forecast) throws ForecastEx {
        if (!scalaService.hasOrderLine(forecast)){
            throw  new ForecastEx(Status.ORDERLINENOTINSCALA);
        }
        if(!scalaService.hasSameItemInOrderLine(forecast)){
            throw new ForecastEx(Status.ITEMNOTEQUALINSCALA);
        }
    }

    @Override
    public List<Forecast> getForecastsFromOrderLines(Forecast baseForecast) throws ForecastEx {
        validForecastInScala(baseForecast);

        List<Forecast> result = new ArrayList<>();
        List<Forecast> forecastListInScala = scalaService.convertParentAndSubOrderLinesToForecasts(baseForecast);

        if(hasSubOrderLines(forecastListInScala)){
            addMultiForecast(baseForecast, result, forecastListInScala);
        }else {
            addOneForecast(baseForecast, result);
        }
        return result;
    }

    private void addOneForecast(Forecast baseForecast, List<Forecast> result) {
        result.add(baseForecast);
    }

    private void addMultiForecast(Forecast baseForecast, List<Forecast> result, List<Forecast> forecastListInScala) {
        Double qtyInForecast = baseForecast.getQty();
        Double ratio = 0.0;
        for(Forecast forecastInScala: forecastListInScala){
            if(isParent(forecastInScala)){
                ratio = setRatio(qtyInForecast, forecastInScala);
                continue;
            }
            updateFromBaseForecast(baseForecast, ratio, forecastInScala);
            addOneForecast(forecastInScala, result);
        }
    }

    private Double setRatio(Double qtyInForecast, Forecast forecastInScala) {
        Double exchangeRatio;
        exchangeRatio = qtyInForecast / forecastInScala.getQty();
        return exchangeRatio;
    }

    private boolean isParent(Forecast forecastInScala) {
        return forecastInScala.getSubLine() == 0;
    }

    private void updateFromBaseForecast(Forecast forecast, Double exchangeRatio, Forecast forecastInScala) {
        forecastInScala.setRowNumber(forecast.getRowNumber());
        forecastInScala.setPlanDate(forecast.getPlanDate());
        forecastInScala.setQty(exchangeRatio * forecastInScala.getQty());
    }

    private boolean hasSubOrderLines(List<Forecast> forecastListInScala) {
        return forecastListInScala.size() > 1;
    }
}
