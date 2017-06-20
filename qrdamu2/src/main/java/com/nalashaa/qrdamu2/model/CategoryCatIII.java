
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryCatIII {

	@XmlAttribute(name = "name")
	private String categoryName;

	@XmlElement(name = "dataelement")
	private List<DataElement> dataElement;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<DataElement> getDataElement() {
		return dataElement;
	}

	public void setDataElement(List<DataElement> dataElement) {
		this.dataElement = dataElement;
	}

	@Override
	public String toString() {
		return "CategoryCatIII [categoryName=" + categoryName + ", dataElement=" + dataElement + ", getCategoryName()="
				+ getCategoryName() + ", getDataElement()=" + getDataElement() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}