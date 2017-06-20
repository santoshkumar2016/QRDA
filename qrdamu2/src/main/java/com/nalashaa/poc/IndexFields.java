
package com.nalashaa.poc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class IndexFields {

    @XmlAttribute
    private String value;

    @XmlElementWrapper
    @XmlElement(name = "index")
    private List<Index> indexlist;

    public String getValue() {
        return value;
    }

}