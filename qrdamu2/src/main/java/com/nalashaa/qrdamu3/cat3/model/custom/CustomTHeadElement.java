package com.nalashaa.qrdamu3.cat3.model.custom;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomTHeadElement {
	@XmlElement( name = "tr")
	private List<CustomTRElement> tr;

	public List<CustomTRElement> getTr() {
		return tr;
	}

	public void setTr(List<CustomTRElement> tr) {
		this.tr = tr;
	}
	
	
	
}
