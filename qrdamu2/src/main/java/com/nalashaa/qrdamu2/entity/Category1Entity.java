package com.nalashaa.qrdamu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name=Category1Entity.TABLE)
public class Category1Entity {

	public static final String TABLE = "CAT1_DETAILS";
	public static final String ID = "id";
	public static final String GIVEN = "Patient_Name_First";
	public static final String FAMILY = "Patient_Last_Name";
	public static final String PATIENT_ADDRESS_LINE_1 = "patient_address_line_1";
	public static final String PATIENT_ADDRESS_CITY = "patient_address_city";
	public static final String PATIENT_ADDRESS_STATE = "patient_address_state";
	public static final String PATIENT_ADDRESS_POSTALCODE = "patient_address_postalcode";
	public static final String PATIENT_ADDRESS_COUNTRY = "patient_address_country";
	
	@Id
	@Column(name = Category1Entity.ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = Category1Entity.GIVEN)
	private String patientNameFirst;
	
	@Column(name = Category1Entity.FAMILY)
	private String patientLastName;
	
	@Column(name = Category1Entity.PATIENT_ADDRESS_LINE_1)
	private String patientAddressStreet;
	
	@Column(name = Category1Entity.PATIENT_ADDRESS_CITY)
	private String patientAddressCity;
	
	@Column(name = Category1Entity.PATIENT_ADDRESS_STATE)
	private String patientAddressState;
	
	@Column(name = Category1Entity.PATIENT_ADDRESS_POSTALCODE)
	private String patientAddressPostalCode;
	
	@Column(name = Category1Entity.PATIENT_ADDRESS_COUNTRY)
	private String patientAddressCountry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPatientAddressStreet() {
		return patientAddressStreet;
	}

	public void setPatientAddressStreet(String patientAddressStreet) {
		this.patientAddressStreet = patientAddressStreet;
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

	public String getPatientAddressPostalCode() {
		return patientAddressPostalCode;
	}

	public void setPatientAddressPostalCode(String patientAddressPostalCode) {
		this.patientAddressPostalCode = patientAddressPostalCode;
	}

	public String getPatientAddressCountry() {
		return patientAddressCountry;
	}

	public void setPatientAddressCountry(String patientAddressCountry) {
		this.patientAddressCountry = patientAddressCountry;
	}
	
}
