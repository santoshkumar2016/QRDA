
package com.nalashaa.qrdamu2.model;

import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class QRDAModel {

    private String measureID; //NQFID

    private int clientId; // 

    private String patientNameFirst; // PatientFirstname

    private String patientLastName;

    private String patientBirthDate;

    private int patientAge;

    private String patientAddressLine1;

    private String patientAddressCity;

    private String patientAddressState;

    private String patientAddressCountry;

    private String patientAddressPostalCode;

    private String patientAddressType;

    private String patientTelephone;

    private String patientTelephoneType;

    private String patientGenderCode;

    private String patientGenderName;

    private String patientRaceCode;

    private String patientRaceName;

    private String patientEthnicityCode;

    private String patientEthnicityName;

    private String patientPayer;

    private String providerPeriodofCareBegin;

    private String providerPeriodofCareEnd;

    private String payerName;

    private Date startDate;

    private Date endDate;

    private String dataElement;

    private String codes;

    private String codeSystem;

    public String getMeasureID() {
        return measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getPatientNameFirst() {
        return patientNameFirst;
    }

    public void setPatientNameFirst(String patientNameFirst) {
        this.patientNameFirst = patientNameFirst;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientAddressLine1() {
        return patientAddressLine1;
    }

    public void setPatientAddressLine1(String patientAddressLine1) {
        this.patientAddressLine1 = patientAddressLine1;
    }

    public String getPatientAddressCity() {
        return patientAddressCity;
    }

    public void setPatientAddressCity(String patientAddressCity) {
        this.patientAddressCity = patientAddressCity;
    }

    public String getPatientAddressState() {
        return patientAddressState;
    }

    public void setPatientAddressState(String patientAddressState) {
        this.patientAddressState = patientAddressState;
    }

    public String getPatientAddressCountry() {
        return patientAddressCountry;
    }

    public void setPatientAddressCountry(String patientAddressCountry) {
        this.patientAddressCountry = patientAddressCountry;
    }

    public String getPatientAddressPostalCode() {
        return patientAddressPostalCode;
    }

    public void setPatientAddressPostalCode(String patientAddressPostalCode) {
        this.patientAddressPostalCode = patientAddressPostalCode;
    }

    public String getPatientAddressType() {
        return patientAddressType;
    }

    public void setPatientAddressType(String patientAddressType) {
        this.patientAddressType = patientAddressType;
    }

    public String getPatientTelephone() {
        return patientTelephone;
    }

    public void setPatientTelephone(String patientTelephone) {
        this.patientTelephone = patientTelephone;
    }

    public String getPatientTelephoneType() {
        return patientTelephoneType;
    }

    public void setPatientTelephoneType(String patientTelephoneType) {
        this.patientTelephoneType = patientTelephoneType;
    }

    public String getPatientGenderCode() {
        return patientGenderCode;
    }

    public void setPatientGenderCode(String patientGenderCode) {
        this.patientGenderCode = patientGenderCode;
    }

    public String getPatientGenderName() {
        return patientGenderName;
    }

    public void setPatientGenderName(String patientGenderName) {
        this.patientGenderName = patientGenderName;
    }

    public String getPatientRaceCode() {
        return patientRaceCode;
    }

    public void setPatientRaceCode(String patientRaceCode) {
        this.patientRaceCode = patientRaceCode;
    }

    public String getPatientRaceName() {
        return patientRaceName;
    }

    public void setPatientRaceName(String patientRaceName) {
        this.patientRaceName = patientRaceName;
    }

    public String getPatientEthnicityCode() {
        return patientEthnicityCode;
    }

    public void setPatientEthnicityCode(String patientEthnicityCode) {
        this.patientEthnicityCode = patientEthnicityCode;
    }

    public String getPatientEthnicityName() {
        return patientEthnicityName;
    }

    public void setPatientEthnicityName(String patientEthnicityName) {
        this.patientEthnicityName = patientEthnicityName;
    }

    public String getPatientPayer() {
        return patientPayer;
    }

    public void setPatientPayer(String patientPayer) {
        this.patientPayer = patientPayer;
    }

    public String getProviderPeriodofCareBegin() {
        return providerPeriodofCareBegin;
    }

    public void setProviderPeriodofCareBegin(String providerPeriodofCareBegin) {
        this.providerPeriodofCareBegin = providerPeriodofCareBegin;
    }

    public String getProviderPeriodofCareEnd() {
        return providerPeriodofCareEnd;
    }

    public void setProviderPeriodofCareEnd(String providerPeriodofCareEnd) {
        this.providerPeriodofCareEnd = providerPeriodofCareEnd;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDataElement() {
        return dataElement;
    }

    public void setDataElement(String dataElement) {
        this.dataElement = dataElement;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

}
