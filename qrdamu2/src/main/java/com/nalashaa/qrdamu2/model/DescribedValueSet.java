
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DescribedValueSet {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlAttribute(name = "displayName")
    private String displayName;

    @XmlAttribute(name = "version")
    private String version;

    @XmlElement(name = "ConceptList")
    private List<ConceptList> conceptList;

    @XmlElement(name = "Source")
    private String source;

    @XmlElement(name = "SourceURI")
    private String sourceURI;

    @XmlElement(name = "Definition")
    private String definition;

    @XmlElement(name = "Type")
    private String type;

    @XmlElement(name = "Binding")
    private String binding;

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "RevisionDate")
    private String revisionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ConceptList> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<ConceptList> conceptList) {
        this.conceptList = conceptList;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceURI() {
        return sourceURI;
    }

    public void setSourceURI(String sourceURI) {
        this.sourceURI = sourceURI;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
        this.revisionDate = revisionDate;
    }

    @Override
    public String toString() {
        return "ClassPojo [Source = " + source + ", Status = " + status + ", Type = " + type + ", ID = " + id + ", SourceURI = " + sourceURI + ", displayName = " + displayName + ", Binding = " + binding + ", RevisionDate = " + revisionDate + ", version = " + version + ", ConceptList = " + conceptList + ", Definition = " + definition + "]";
    }
}
