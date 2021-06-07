package com.hacken.forecast.model;

import java.util.Date;

public class Forecast {
    Integer rowNumber;
    String item;
    Long order;
    Integer line;
    Double qty;
    Date planDate;
    Integer subLine;
    String warehouse;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Integer getSubLine() {
        return subLine;
    }

    public void setSubLine(Integer subLine) {
        this.subLine = subLine;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "rowNumber=" + rowNumber +
                ", item='" + item + '\'' +
                ", order=" + order +
                ", line=" + line +
                ", qty=" + qty +
                ", planDate=" + planDate +
                ", subLine=" + subLine +
                '}';
    }
}
