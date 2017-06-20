package com.nalashaa.qrdamu3.cat3.model.custom;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "text")
public class CustomTextElement {
	
	@XmlElement( name = "table")
	private CustomTableElement table;
	
	@XmlElement( name = "list")
	private CustomListElement list;

	public CustomTableElement getTable() {
		return table;
	}

	public void setTable(CustomTableElement table) {
		this.table = table;
	}

	public CustomListElement getList() {
		return list;
	}

	public void setList(CustomListElement list) {
		this.list = list;
	}
	
	
	
}
