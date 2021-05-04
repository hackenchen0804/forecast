package com.hacken.forecast.exception;

import java.util.ArrayList;
import java.util.List;

public final class ForecastEx extends Exception{
    public ForecastEx(Status status,String... args){
        this.status = status;
        this.args=args;
    }

    List<ForecastEx> subForecasts = new ArrayList<>();
    Status status;
    String[] args;

    public Status getStatus() {
        return status;
    }

    public String msgHead(){
        return this.status.msgHead();
    }

    public String msgBody(){
        String message = String.format(this.status.msgContent(),args)+"\n";
        if (subForecasts.size()>0){
            for(ForecastEx forecastEx:subForecasts){
                message+= "-" +forecastEx.msgBody()+"\n";
            }
        }
        return message;
    }

}
