
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DenominatorExceptionsId {

    @XmlAttribute(name = "multiple")
    boolean multiple;

    @XmlAttribute(name = "none")
    boolean none;

    @XmlElement(name = "item")
    private List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isNone() {
        return none;
    }

    public void setNone(boolean none) {
        this.none = none;
    }

    @Override
    public String toString() {
        return "InitialPatientPopulationId [multiple=" + multiple + ", none=" + none + ", item=" + item + "]";
    }
}
