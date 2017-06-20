
package com.nalashaa.poc;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "XmlImportConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlImportConfig {

    @XmlElement(name = "indexfields")
    private ArrayList<IndexFields> listOfIndexFields;

    public ArrayList<IndexFields> getListOfIndexFields() {
        return listOfIndexFields;
    }

}