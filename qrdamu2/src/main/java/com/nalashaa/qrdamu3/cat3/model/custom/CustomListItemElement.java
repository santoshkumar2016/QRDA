package com.nalashaa.qrdamu3.cat3.model.custom;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomListItemElement {

	@XmlElement(name = "content")
	private List<CustomListItemContentElement> content;

	@XmlMixed
	List<String> mixed;

	public List<String> getMixed() {
		return mixed;
	}

	public void setMixed(List<String> mixed) {
		this.mixed = mixed;
	}

	public List<CustomListItemContentElement> getContent() {
		return content;
	}

	public void setContent(List<CustomListItemContentElement> content) {
		this.content = content;
	}

}
