
package com.nalashaa.qrdamu2.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "Template")
public class TemplateCategory1 {

    private String Medication_Dispensed;

    private String Procedure_Performed;

    private String Communication_From_Provider_to_Provider;

    private String QRDA;

    private String MEASUREDATA;

    private String Physical_Exam;

    private String Encounter_Performed;

    private String Intervention_Performed;

    private String Medication_Active;

    private String Medication_Order;

    private String Diagnosis_Active;

    public String getMedication_Dispensed() {
        return Medication_Dispensed;
    }

    @XmlElement(name = "Medication_Dispensed")
    public void setMedication_Dispensed(String medication_Dispensed) {
        Medication_Dispensed = medication_Dispensed;
    }

    public String getProcedure_Performed() {
        return Procedure_Performed;
    }

    @XmlElement(name = "Procedure_Performed")
    public void setProcedure_Performed(String procedure_Performed) {
        Procedure_Performed = procedure_Performed;
    }

    public String getCommunication_From_Provider_to_Provider() {
        return Communication_From_Provider_to_Provider;
    }

    @XmlElement(name = "Communication_From_Provider_to_Provider")
    public void setCommunication_From_Provider_to_Provider(String communication_From_Provider_to_Provider) {
        Communication_From_Provider_to_Provider = communication_From_Provider_to_Provider;
    }

    public String getQRDA() {
        return QRDA;
    }

    @XmlElement(name = "QRDA")
    public void setQRDA(String qRDA) {
        QRDA = qRDA;
    }

    public String getMEASUREDATA() {
        return MEASUREDATA;
    }

    @XmlElement(name = "MEASUREDATA")
    public void setMEASUREDATA(String mEASUREDATA) {
        MEASUREDATA = mEASUREDATA;
    }

    public String getPhysical_Exam() {
        return Physical_Exam;
    }

    @XmlElement(name = "Physical_Exam")
    public void setPhysical_Exam(String physical_Exam) {
        Physical_Exam = physical_Exam;
    }

    public String getEncounter_Performed() {
        return Encounter_Performed;
    }

    @XmlElement(name = "Encounter_Performed")
    public void setEncounter_Performed(String encounter_Performed) {
        Encounter_Performed = encounter_Performed;
    }

    public String getIntervention_Performed() {
        return Intervention_Performed;
    }

    @XmlElement(name = "Intervention_Performed")
    public void setIntervention_Performed(String intervention_Performed) {
        Intervention_Performed = intervention_Performed;
    }

    public String getMedication_Active() {
        return Medication_Active;
    }

    @XmlElement(name = "Medication_Active")
    public void setMedication_Active(String medication_Active) {
        Medication_Active = medication_Active;
    }

    public String getMedication_Order() {
        return Medication_Order;
    }

    @XmlElement(name = "Medication_Order")
    public void setMedication_Order(String medication_Order) {
        Medication_Order = medication_Order;
    }

    public String getDiagnosis_Active() {
        return Diagnosis_Active;
    }

    @XmlElement(name = "Diagnosis_Active")
    public void setDiagnosis_Active(String diagnosis_Active) {
        Diagnosis_Active = diagnosis_Active;
    }

}
