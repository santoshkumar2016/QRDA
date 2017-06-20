
package com.nalashaa.qrdamu2.util;

public interface ApplicationConstants {

	/*Template file name*/
	String QRDA_CAT3_FILE_NAME = "QRDA_CAT3";
	
	
    /* Template1 keys */
    String EFFECTIVE_TIME = "EFFECTIVE_TIME";
    String ID_EXTENSION = "idExtension";
    String ADDR_USE = "addrUse";
    String STREET_ADDRESS_LINE = "streetAddressLine";
    String CITY = "city";
    String STATE = "state";
    String POSTAL_CODE = "postalCode";
    String COUNTRY = "country";
    String TELECOM_USE = "telecomUse";
    String TELECOM_VALUE = "telecomValue";
    String NAME_GIVEN = "nameGiven";
    String NAME_FAMILY = "nameFamily";
    String ADMINISTRATIVE_GENDER_CODE_CODE = "administrativeGenderCodeCode";
    String BIRTH_TIME = "birthTime";
    String RACE_CODE_CODE = "raceCodeCode";
    String RACE_CODE_DISPLAY_NAME = "raceCodeDisplayName";
    String ETHNIC_GROUP_CODE_CODE = "ethnicGroupCodeCode";
    String ETHNIC_GROUP_CODE_DISPLAY_NAME = "ethnicGroupCodeDisplayName";
    String LANGUAGE_CODE = "languageCode";
    String AUTHOR_TIME = "authorTime";
    String AUTHOR_ID_EXTENSION = "authorIdExtension";
    String AUTHOR_STREET_ADDRESS_LINE = "authorStreetAddressLine";
    String AUTHOR_CITY = "authorCity";
    String AUTHOR_STATE = "authorState";
    String AUTHOR_POSTAL_CODE = "authorPostalCode";
    String AUTHOR_COUNTRY = "authorCountry";
    String AUTHOR_TELECOM_USE = "authorTelecomUse";
    String AUTHOR_TELECOM_VALUE = "authorTelecomValue";
    String MANUFACTURE_MODEL_NAME = "manufactureModelName";
    String SOFTWARE_NAME = "softwareName";
    String CUSTODIAN_NAME = "custodianName";
    String CUSTODIAN_TELECOM_USE = "custodiantelecomUse";
    String CUSTODIAN_TELECOM_VALUE = "custodiantelecomValue";
    String CUSTODIAN_STREET_ADDRESS_LINE = "custodianStreetAddressLine";
    String CUSTODIAN_CITY = "custodianCity";
    String CUSTODIAN_STATE = "custodianState";
    String CUSTODIAN_POSTAL_CODE = "custodianPostalCode";
    String CUSTODIAN_COUNTRY = "custodianCountry";
    String LEGAL_AUTHENTICATOR_TIME = "legalAuthenticatorTime";
    String LEGAL_AUTHENTICATOR_STREET_ADDRESS_LINE = "legalAuthenticatorStreetAddressLine";
    String LEGAL_AUTHENTICATOR_CITY = "legalAuthenticatorCity";
    String LEGAL_AUTHENTICATOR_STATE = "legalAuthenticatorState";
    String LEGAL_AUTHENTICATOR_POSTAL_CODE = "legalAuthenticatorPostalCode";
    String LEGAL_AUTHENTICATOR_COUNTRY = "legalAuthenticatorCountry";
    String LEGAL_AUTHENTICATOR_TELECOM_USE = "legalAuthenticatorTelecomUse";
    String LEGAL_AUTHENTICATOR_TELECOM_VALUE = "legalAuthenticatorTelecomValue";
    String LEGAL_AUTHENTICATOR_GIVEN = "legalAuthenticatorGiven";
    String LEGAL_AUTHENTICATOR_FAMILY = "legalAuthenticatorFamily";
    String LEGAL_AUTHENTICATOR_NAME = "legalAuthenticatorName";
    String DOCUMENTATION_OF_EFFECTIVE_TIME_LOW = "documentationOfEffectiveTimeLow";
    String DOCUMENTATION_OF_EFFECTIVE_TIME_HIGH = "documentationOfEffectiveTimeHigh";
    String DOCUMENTATION_OF_PERFORMER_TIME_LOW = "documentationOfPerformerTimeLow";
    String DOCUMENTATION_OF_PERFORMER_TIME_HIGH = "documentationOfPerformerTimeHigh";
    String DOCUMENTATION_OF_NPI = "documentationOfNPI";
    String DOCUMENTATION_OF_TIN = "documentationOfTIN";
    String DOCUMENTATION_OF_CCN = "documentationOfCCN";

    /* Measure keys */
    String MEASURE_TITLE = "measureTitle";
    String MEASURE_VERSION_NEUTRAL_IDENTIFIER = "measureVersionNeutralIdentifier";
    String MEASURE_VERSION_NUMBER = "measureVersionNumber";
    String MEASURE_VERSION_SPECIFIC_IDENTIFIER = "measureVersionSpecificIdentifier";
    String MEASURE_RANDOM_NUMBER = "measureRandomNumber";

    /* Entry keys */
    String RANDOM_NUMBER = "randomNumber";
    String CODE = "code";
    String CODE_SYSTEM = "codeSystem";
    String VALUE_SET = "valueSet";
    String TEXT = "text";
    String START_DATE = "startDate";
    String END_DATE = "endDate";
    String START_DATE_CAT3 = "START_DATE";
    String END_DATE_CAT3 = "END_DATE";
    String FORMATTED_START_DATE = "formattedStartDate";
    String FORMATTED_END_DATE = "formattedEndDate";
    String ENTRY_DATA = "ENTRY_DATA";
    String OBSERVATION_COMPONENT = "observationComponent";
    String ENTRY_RELATIONSHIP = "entryRelationship";
    String IPP_GUID = "ippGUID";
    String IPP_COUNT = "ippCount";
    String DENOMINATOR_COUNT = "denominatorCount";
    String NUMERATOR_COUNT = "numeratorCount";
    String DENOMINATOR_EXCLUSIONS_COUNT = "denominatorExclusionsCount";
    String DENOMINATOR_EXCEPTION_COUNT = "denominatorExceptionCount";
    String DENOMINATOR_GUID = "denominatorGUID";
    String DENOMINATOR_EXCLUSIONS_GUID = "denominatorExclusionsGUID";
    String NUMERATOR_GUID = "numeratorGUID";
    String DENOMINATOR_EXCEPTION_GUID = "denominatorExceptionGUID";
    String PERFORMANCE_RATE_COMPONENT = "componentPerformanceRate";
    String REPORTING_RATE_COMPONENT = "componentreportingRate";
    String STRATUM_GUID = "stratumGUID";
    String STRATUM_COUNT = "stratumCount";
    
    /*Entry relationships*/
    String ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_CODE = "patientEthnicityCode";
    String ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_DISPLAYNAME = "patientEthnicityDisplayName";
    String ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_VALUE = "patientEthnicityValue";
    String ENTRY_RELATIONSHIP_PATIENT_RACE_CODE = "patientRaceCode";
    String ENTRY_RELATIONSHIP_PATIENT_RACE_DISPLAYNAME = "patientRaceDisplayName";
    String ENTRY_RELATIONSHIP_PATIENT_RACE_VALUE = "patientRaceValue";
    String ENTRY_RELATIONSHIP_PAYER_CODE = "payerCode";
    String ENTRY_RELATIONSHIP_PAYER_DISPLAYNAME = "payerDisplayName";
    String ENTRY_RELATIONSHIP_PAYER_VALUE = "payerValue";
    String ENTRY_RELATIONSHIP_PAYER_LOW = "payerLow";
    String ENTRY_RELATIONSHIP_PAYER_HIGH = "payerHigh";
    String ENTRY_RELATIONSHIP_PATIENT_SEX_CODE = "patientSexCode";
    String ENTRY_RELATIONSHIP_PATIENT_SEX_DISPLAYNAME = "patientSexDisplayName";
    String ENTRY_RELATIONSHIP_PATIENT_SEX_VALUE = "patientSexValue";
    

    /*Template Entry names*/
    String ENCOUNTER_PERFORMED = "Encounter_Performed";
    String INTERVENTION_PERFORMED = "Intervention_Performed";
    String DIAGNOSIS_ACTIVE = "Diagnosis_Active";
    String MEDICATION_DISPENSED = "Medication_Dispensed";
    String MEDICATION_ACTIVE = "Medication_Active";
    String PROCEDURE_PERFORMED = "Procedure_Performed";
    String MEDICATION_ORDER = "Medication_Order";
    String PHYSICAL_EXAM = "Physical_Exam";
    String COMMUNICATION_FROM_PROVIDER_TO_PROVIDER = "Communication_From_Provider_to_Provider";

    /*Query Alias column name*/
    String MEASUREID = "MeasureID";
    String CLIENTID = "ClientId";
    String PATIENT_NAME_FIRST = "Patient_Name_First";
    String PATIENT_LAST_NAME = "Patient_Last_Name";
    String PATIENT_BIRTHDATE = "Patient_BirthDate";
    String PATIENT_AGE = "Patient_Age";
    String PATIENT_ADDRESS_LINE_1 = "Patient_Address_Line_1";
    String PATIENT_ADDRESS_CITY = "Patient_Address_City";
    String PATIENT_ADDRESS_STATE = "Patient_Address_State";
    String PATIENT_ADDRESS_COUNTRY = "Patient_Address_Country";
    String PATIENT_ADDRESS_POSTALCODE = "Patient_Address_PostalCode";
    String PATIENT_ADDRESS_TYPE = "Patient_Address_Type";
    String PATIENT_TELEPHONE = "Patient_Telephone";
    String PATIENT_TELEPHONE_TYPE = "Patient_Telephone_Type";
    String PATIENT_GENDER_CODE = "Patient_Gender_Code";
    String PATIENT_GENDER_NAME = "Patient_Gender_Name";
    String PATIENT_RACE_CODE = "Patient_Race_Code";
    String PATIENT_RACE_NAME = "Patient_Race_Name";
    String PATIENT_ETHNICITY_CODE = "Patient_Ethnicity_Code";
    String PATIENT_ETHNICITY_NAME = "Patient_Ethnicity_Name";
    String PATIENT_PAYER = "Patient_Payer";
    String PROVIDERPERIODOFCARE_BEGIN = "ProviderPeriodofCare_Begin";
    String PROVIDERPERIODOFCARE_END = "ProviderPeriodofCare_End";
    String PAYERNAME = "PayerName";
    String STARTDATE = "StartDate";
    String ENDDATE = "EndDate";
    String DATA_ELEMENT = "DATA_ELEMENT";
    String CODES = "CODES";
    String CODESYSTEM = "CodeSystem";

    /*Query Alias column name for Agency*/
    String AGENCYID = "AgencyID";
    String AGENCYNAME = "AgencyName";
    String AGENCYTELEPHONE = "AgencyTelephone";
    String AGENCYADDRESS1 = "AgencyAddress1";
    String AGENCYCITY = "AgencyCity";
    String AGENCYSTATE = "AgencyState";
    String AGENCYCOUNTRY = "AgencyCountry";
    String AGENCYZIP = "AgencyZip";
    String PROVIDER_NPI = "Provider_NPI";
    
    /*Country*/
    String UNITED_STATES = "United States";
    
    
    
    
    // Query name for new input table approach
    String TransactionID = "TransactionID";
    String NQFID = "NQFID";
    String CASE_ID = "CASEID";
    String PATIENT_F_NAME = "PatientFirstname";
    String PATIENT_L_NAME = "PatientLastname";
    String BIRTH_DATE = "Birthdate";
    String AGE = "Age";
    String ADDR_LINE_1 = "AddressLine1";
    String Q_CITY = "City";
    String Q_STATE = "State";
    String Q_COUNTRY = "Country";
    String Q_POSTAL_CODE = "PostalCode";
    String ADDR_TYPE = "AddressType";
    String TELEPHONE = "Telephone";
    String TELEPHONE_TYPE = "TelephoneType";
    String GENDER_CODE = "GenderCode";
    String GENDER_NAME = "GenderName";
    String RACE_CODE = "RaceCode";
    String RACE_NAME = "RaceName";
    String ETHNICITY_CODE = "EthnicityCode";
    String ETHNICITY_NAME = "EthnicityName";
    String PAYER_CODE =  "PayerCode";
    String PROVIDER_ID = "ProviderID";
    String PROVIDER_F_NAME = "ProviderFirstname";
    String PROVIDER_L_NAME = "ProviderLastname";
    String PROVIDER_ADDR_LINE = "ProviderAddressLine";
    String PROVIDER_CITY = "ProviderCity";
    String PROVIDER_STATE = "ProviderState";
    String PROVIDER_COUNTRY = "ProviderCountry";
    String PROVIDER_POSTAL_CODE = "ProviderPostalcode";
    String PROVIDER_TELEPHONE = "ProviderTelephone";
    String Q_PROVIDER_NPI = "ProviderNPI";
    String ORG_TIN = "OrganizationTIN";
    String ORG_CCN = "OrganizationCCN";
    String REPORT_START_DATE = "ReportStartDate";
    String REPORT_END_DATE = "ReportEndDate";
    String DATA_ELEM_CODE = "DataElementCode";
    String DATA_ELEM_NAME = "DataElementName";
    String Q_START_DATE = "StartDate";
    String Q_END_DATE = "EndDate";
    String DATA_VALUE = "DataValue";
    String CREATED_BY = "CreatedBy";
    String CREATED_ON = "CreatedOn";
    
    //for MU3
    String GUID = "GUID";
    String DISPLAY_NAME = "DisplayName";
    String PATIENT_DATA = "PatientData";

}