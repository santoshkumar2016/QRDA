
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "eMeasure")
@XmlAccessorType(XmlAccessType.FIELD)
public class Emeasure {

    @XmlElement(name = "Measure")
    private List<Measure> measureList;

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }

    @Override
    public String toString() {
        return "Emeasure [measureList=" + measureList + "]";
    }
}
