
package com.nalashaa.qrdamu2.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QrdaDto {

    private List<String> measureList;

    private List<String> providerList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public List<String> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<String> measureList) {
        this.measureList = measureList;
    }

    public List<String> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<String> providerList) {
        this.providerList = providerList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "QrdaDto [measureList=" + measureList + ", providerList=" + providerList + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
