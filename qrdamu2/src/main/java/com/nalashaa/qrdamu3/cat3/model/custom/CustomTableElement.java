package com.nalashaa.qrdamu3.cat3.model.custom;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomTableElement {
	
	@XmlAttribute( name = "border")
	private String border;
	
	@XmlAttribute( name = "width")
	private String width;
	
	
	@XmlElement( name = "thead")
	private CustomTHeadElement thead;
	
	@XmlElement( name = "tbody")
	private CustomTBodyElement tbody;

	public CustomTHeadElement getThead() {
		return thead;
	}

	public void setThead(CustomTHeadElement thead) {
		this.thead = thead;
	}

	public CustomTBodyElement getTbody() {
		return tbody;
	}

	public void setTbody(CustomTBodyElement tbody) {
		this.tbody = tbody;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	

	

	
	
	

}
