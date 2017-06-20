package com.nalashaa.qrdamu3.cat3.model.custom;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomTRElement {
	@XmlElement( name = "th")
	private List<String> th;
	
	@XmlElement( name = "td")
	private List<String> td;

	public List<String> getTh() {
		return th;
	}

	public void setTh(List<String> th) {
		this.th = th;
	}

	public List<String> getTd() {
		return td;
	}

	public void setTd(List<String> td) {
		this.td = td;
	}

	
	
}
