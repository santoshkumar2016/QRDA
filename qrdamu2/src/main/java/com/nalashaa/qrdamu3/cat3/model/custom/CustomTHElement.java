package com.nalashaa.qrdamu3.cat3.model.custom;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomTHElement {

	@XmlElement( name = "th")
	private List<CustomTHElement> th;

	public List<CustomTHElement> getTh() {
		return th;
	}

	public void setTh(List<CustomTHElement> th) {
		this.th = th;
	}
	
	


}
