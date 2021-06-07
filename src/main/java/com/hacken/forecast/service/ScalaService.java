package com.hacken.forecast.service;

import com.hacken.forecast.model.Forecast;

import java.util.List;


public interface ScalaService {
    boolean hasOrderLine(Forecast forecast);
    boolean hasSameItemInOrderLine(Forecast forecast);
    List<Forecast> convertParentAndSubOrderLinesToForecasts(Forecast forecast);
    List<Forecast> getWorkOrderMaterials();
    String getWarehouse(Forecast forecast);
}
