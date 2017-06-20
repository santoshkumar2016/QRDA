
package com.nalashaa.poc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Index {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String value;

}