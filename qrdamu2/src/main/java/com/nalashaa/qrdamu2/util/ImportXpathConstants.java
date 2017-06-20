package com.nalashaa.qrdamu2.util;

/**
 * 
 * @author prathap
 * 
 * This interface is specifically for maintaining XML PATH constants to import the data 
 */
public interface ImportXpathConstants {

	String PATIENT_GIVEN_NAME = "//ClinicalDocument/recordTarget/patientRole/patient/name/given/text()";
	String PATIENT_FAMILY_NAME = "//ClinicalDocument/recordTarget/patientRole/patient/name/family/text()";
	String PATIENT_ADDR_STREET = "//ClinicalDocument/recordTarget/patientRole/addr/streetAddressLine/text()";
	String PATIENT_ADDR_CITY = "//ClinicalDocument/recordTarget/patientRole/addr/city/text()";
	String PATIENT_ADDR_STATE = "//ClinicalDocument/recordTarget/patientRole/addr/state/text()";
	String PATIENT_ADDR_POSTAL = "//ClinicalDocument/recordTarget/patientRole/addr/postalCode/text()";
	String PATIENT_ADDR_COUNTRY = "//ClinicalDocument/recordTarget/patientRole/addr/country/text()";
}
