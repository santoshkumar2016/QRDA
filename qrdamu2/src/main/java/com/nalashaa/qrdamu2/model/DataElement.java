
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataElement {

    @XmlAttribute(name = "name")
    String name;

    @XmlElement(name = "codes")
    Codes codes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Codes getCodes() {
		return codes;
	}

	public void setCodes(Codes codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "DataElement [name=" + name + ", codes=" + codes + ", getName()=" + getName() + ", getCodes()="
				+ getCodes() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
      
}