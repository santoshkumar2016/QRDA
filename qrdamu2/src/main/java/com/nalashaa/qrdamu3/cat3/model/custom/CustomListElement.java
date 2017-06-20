package com.nalashaa.qrdamu3.cat3.model.custom;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomListElement {
	
	@XmlElement( name = "item")
	private List<CustomListItemElement> item;

	public List<CustomListItemElement> getItem() {
		return item;
	}

	public void setItem(List<CustomListItemElement> item) {
		this.item = item;
	}
	
	
}
