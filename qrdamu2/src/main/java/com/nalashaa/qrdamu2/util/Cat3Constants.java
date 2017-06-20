package com.nalashaa.qrdamu2.util;

public interface Cat3Constants {
	String COMPONENT_DENOMINATOR = "DENOM";
	String COMPONENT_NUMER = "NUMER";
	String COMPONENT_DENOMINATOR_EXCEPTION = "DENEXCEP";
	String COMPONENT_DENOMINATOR_EXCLUTION = "DENEX";
	
	String TYPE_ID_ROOT = "2.16.840.1.113883.1.3";
	String TYPE_ID_EXTENSION = "POCD_HD000040";
	
	String TEMPLATE_ID_ROOT = "2.16.840.1.113883.10.20.27.1.1";
	String TEMPLATE_ID_EXTENSION = "2016-09-01";
	
	String ID_ROOT = "";//GENERATED VALUE EX. 2dcaebf2-96ef-4987-9d13-1b3565e2c5d9
	// CODE, CODE SYSTEM, CODE_SYSTEM_NAME, DISPLAY_NAME
	//TITLE
	//EFFECTIVE TIME VAL - CURRENT DATETIME
	//LANGUAGE_CODE_CODE
	
	String CODE_CODE = "55184-6";
	String CODE_CODE_SYSTEM = "2.16.840.1.113883.6.1";
	String CODE_CODE_SYSTEM_NAME = "LOINC";
	String CODE_DISPLAY_NAME = "Quality Reporting Document Architecture Calculated Summary Report";
	
	String CONFIDETIALITY_CODE = "N";
	String CONFIDETIALITY_CODE_SYSTEM = "2.16.840.1.113883.5.25";
	
	String LANGUAGE_CODE = "US";
	String SIGNATURE_CODE = "S";
	
	String VERSION_NUMBER = "1";
	
	String assignedAuthorId_root = "2.16.840.1.113883.4.6";
	String assigningAuthorityName = "NPI";
	
	String representedCustodianOrganization_root = "2.16.840.1.113883.19.5";
	
	String serviceEvent_class_code = "PCPR";
	
	String reporting_param_section_template_id_root = "2.16.840.1.113883.10.20.17.2.1";
	String category3_Reporting_Parameters_templateId = "2.16.840.1.113883.10.20.27.2.2";
	
	String report_param_code = "55187-9";
	String report_param_code_system = "2.16.840.1.113883.6.1";
	
	String act_templateId = "2.16.840.1.113883.10.20.17.3.8";
	
	String act_code = "252116004";
	String act_code_system = "2.16.840.1.113883.6.96";
	String act_display_name = "Observation Parameters";
	
	String measureSection_templateId_1_root = "2.16.840.1.113883.10.20.24.2.2";
	String measureSection_templateId_2_root = "2.16.840.1.113883.10.20.27.2.1";
	String measureSection_templateId_2_extension = "2016-09-01";
	String measureSectionCode_code = "55186-1";
	String measureSectionCode_codeSystem = "2.16.840.1.113883.6.1";
	String measureSectionCode_displayName = "measure section";
	String measureOrganizerMoodCode = "EVN";
	String measureTemplateId_1_root = "2.16.840.1.113883.10.20.24.3.98";
	String measureTemplateId_2_root = "2.16.840.1.113883.10.20.27.3.1";
	String measureTemplateId_2_ext = "2016-09-01";
	
	String externalDoc_classCode = "DOC";
	
	
	//<!-- Sex Supplemental Data Element -->
	String gender_templateId_root = "2.16.840.1.113883.10.20.27.3.6";
	String gender_templateId_EXTENSION = "2016-09-01";
	String gender_code_code = "76689-9";
	String gender_code_displayName = "Sex assigned at birth";
	String gender_code_codeSystem = "2.16.840.1.113883.6.1";
	String gender_code_codeSystemName = "LOINC";
	String gender_value_code = "M";
	String gender_value_displayName = "Male";
	String gender_value_codeSystem = "2.16.840.1.113883.18.2";
	String gender_value_codeSystemName = "AdministrativeSex";
	//er-entryRelationship, obs-observation
	String gender_er_obs_er_obs_templateId_root = "2.16.840.1.113883.10.20.27.3.3";
	String gender_er_obs_er_obs_code_code = "MSRAGG";
	String gender_er_obs_er_obs_code_displayName = "rate aggregation";
	String gender_er_obs_er_obs_code_codeSystem = "2.16.840.1.113883.5.4";
	String gender_er_obs_er_obs_code_codeSystemName = "ActCode";
	String gender_er_obs_er_obs_methodCode_code = "COUNT";
	String gender_er_obs_er_obs_methodCode_codeSystem = "2.16.840.1.113883.5.84";
	String gender_er_obs_er_obs_methodCode_codeSystemName = "ObservationMethod";
	String gender_er_obs_er_obs_methodCode_displayName = "Count";
	
	
	String observation_templateId_root ="2.16.840.1.113883.10.20.27.3.3";
	String observation_code_code = "MSRAGG";
	String observation_code_codeSystem = "2.16.840.1.113883.5.4";
	String observation_code_codeSystemName = "ActCode";
	String observation_code_displayName = "rate aggregation";
	//String observation_templateId_extension ="";
	String observation_classCode = "OBS";
	String observation_moodCode = "EVN";
	String entryRelationship_typeCode = "SUBJ";
	Boolean entryRelationship_inversionInd = true;
	
	String statusCode = "completed";
	
	String payer_templateId_root = "2.16.840.1.113883.10.20.24.3.55";
	String payer_templateId_1_root = "2.16.840.1.113883.10.20.27.3.9";
	String payer_templateId_1_EXTENSION = "2016-02-01";
	String payer_code_code = "48768-6";
	String payer_code_displayName = "Payment source";
	String payer_code_codeSystemName = "LOINC";
	String payer_code_codeSystem = "2.16.840.1.113883.6.1";
	String payer_value_code = "2";
	String payer_value_codeSystem = "2.16.840.1.113883.3.221.5";
	String payer_value_codeSystemName = "Source of Payment Typology";
	
	String observation_methodCode_code = "COUNT";
	String observation_methodCode_displayName = "Count";
	String observation_methodCode_codeSystem = "2.16.840.1.113883.5.84";
	String observation_methodCode_codeSystemName = "ObservationMethod";

}
