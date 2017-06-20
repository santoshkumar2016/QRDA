
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RetrieveMultipleValueSetsResponse", namespace = "urn:ihe:iti:svs:2008")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetrieveMultipleValueSetsResponse {

    @XmlElement(name = "DescribedValueSet")
    private List<DescribedValueSet> describedValueSet;

    public List<DescribedValueSet> getDescribedValueSet() {
        return describedValueSet;
    }

    public void setDescribedValueSet(List<DescribedValueSet> describedValueSet) {
        this.describedValueSet = describedValueSet;
    }

}