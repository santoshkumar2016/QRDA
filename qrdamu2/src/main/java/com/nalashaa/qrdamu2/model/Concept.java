
package com.nalashaa.qrdamu2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Concept {

    @XmlAttribute(name = "code")
    private String code;

    @XmlAttribute(name = "codeSystem")
    private String codeSystem;

    @XmlAttribute(name = "codeSystemName")
    private String codeSystemName;

    @XmlAttribute(name = "codeSystemVersion")
    private String codeSystemVersion;

    @XmlAttribute(name = "displayName")
    private String displayName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public String getCodeSystemVersion() {
        return codeSystemVersion;
    }

    public void setCodeSystemVersion(String codeSystemVersion) {
        this.codeSystemVersion = codeSystemVersion;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "ClassPojo [codeSystemVersion = " + codeSystemVersion + ", codeSystemName = " + codeSystemName + ", codeSystem = " + codeSystem + ", code = " + code + ", displayName = " + displayName + "]";
    }
}