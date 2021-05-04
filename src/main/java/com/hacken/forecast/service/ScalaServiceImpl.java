package com.hacken.forecast.service;

import com.hacken.forecast.mapper.ScalaMapper;
import com.hacken.forecast.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScalaServiceImpl implements ScalaService {
    ScalaMapper scalaMapper;

    @Autowired
    public void setScalaMapper(ScalaMapper scalaMapper) {
        this.scalaMapper = scalaMapper;
    }

    @Override
    public boolean hasOrderLine(Forecast forecast){
        return scalaMapper.hasOrderLine(forecast.getOrder(),forecast.getLine());
    }

    @Override
    public boolean hasSameItemInOrderLine(Forecast forecast) {
        return scalaMapper.hasSameItemInOrderLine(forecast.getOrder(),forecast.getLine(),forecast.getItem());
    }

    @Override
    public List<Forecast> convertParentAndSubOrderLinesToForecasts(Forecast forecast) {
        return scalaMapper.getParentAndSubOrderLine(forecast.getOrder(),forecast.getLine());
    }
}
