package com.hacken.forecast.event;

import com.hacken.forecast.exception.ForecastEx;
import org.springframework.context.ApplicationEvent;

public class AlertEvent extends ApplicationEvent {
    ForecastEx forecastEx;

    public AlertEvent(ForecastEx forecastEx) {
        super(forecastEx);
        this.forecastEx = forecastEx;
    }

    public ForecastEx getForecastEx(){
        return this.forecastEx;
    }
}
