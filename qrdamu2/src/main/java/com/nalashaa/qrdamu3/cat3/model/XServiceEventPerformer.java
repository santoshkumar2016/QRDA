//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.08 at 01:31:32 PM IST 
//


package com.nalashaa.qrdamu3.cat3.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_ServiceEventPerformer.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_ServiceEventPerformer">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="PRF"/>
 *     &lt;enumeration value="PPRF"/>
 *     &lt;enumeration value="SPRF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "x_ServiceEventPerformer")
@XmlEnum
public enum XServiceEventPerformer {

    PRF,
    PPRF,
    SPRF;

    public String value() {
        return name();
    }

    public static XServiceEventPerformer fromValue(String v) {
        return valueOf(v);
    }

}
