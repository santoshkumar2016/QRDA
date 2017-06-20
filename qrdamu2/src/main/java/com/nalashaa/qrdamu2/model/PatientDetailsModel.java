package com.nalashaa.qrdamu2.model;

public class PatientDetailsModel {

	private String provider;
	
	private String cqm;
	
	private String cid;
	
	private String lastName;
	
	private String firstName;
	
	private String gender;
	
	private String birthDate;

	public String getCqm() {
		return cqm;
	}

	public void setCqm(String cqm) {
		this.cqm = cqm;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}
