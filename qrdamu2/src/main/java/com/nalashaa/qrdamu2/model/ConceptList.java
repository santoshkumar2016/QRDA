
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptList {

    @XmlElement(name = "Concept")
    private List<Concept> concept;

    public List<Concept> getConcept() {
        return concept;
    }

    public void setConcept(List<Concept> concept) {
        this.concept = concept;
    }

    @Override
    public String toString() {
        return "ClassPojo [Concept = " + concept + "]";
    }
}
