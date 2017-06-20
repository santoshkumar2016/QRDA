
package com.nalashaa.qrdamu2.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Measure {

	@XmlAttribute(name = "id")
	private String id;

	@XmlElement(name = "title")
	// @XmlJavaTypeAdapter(AdapterCDATA.class)
	private String title;

	@XmlElement(name = "versionNeutralIdentifier")
	private String versionNeutralIdentifier;

	@XmlElement(name = "versionNumber")
	private String versionNumber;

	@XmlElement(name = "versionSpecificMeasureID")
	private String versionSpecificMeasureId;

	@XmlElement(name = "initialPatientPopulationID")
	private InitialPatientPopulationId initialPatientPopulationId;

	@XmlElement(name = "numeratorID")
	private NumeratorId numeratorId;

	@XmlElement(name = "denominatorPopulationID")
	private DenominatorPopulationId denominatorPopulationId;

	@XmlElement(name = "denominatorExclusionsID")
	private DenominatorExclusionsId denominatorExclusionsID;

	@XmlElement(name = "denominatorExceptionsID")
	private DenominatorExceptionsId denominatorExceptionsId;

	@XmlElement(name = "stratumID")
	private StratumId stratumID;

	@XmlElement(name = "category")
	private List<CategoryCatIII> category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersionNeutralIdentifier() {
		return versionNeutralIdentifier;
	}

	public void setVersionNeutralIdentifier(String versionNeutralIdentifier) {
		this.versionNeutralIdentifier = versionNeutralIdentifier;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionSpecificMeasureId() {
		return versionSpecificMeasureId;
	}

	public void setVersionSpecificMeasureId(String versionSpecificMeasureId) {
		this.versionSpecificMeasureId = versionSpecificMeasureId;
	}

	public NumeratorId getNumeratorId() {
		return numeratorId;
	}

	public void setNumeratorId(NumeratorId numeratorId) {
		this.numeratorId = numeratorId;
	}

	public InitialPatientPopulationId getInitialPatientPopulationId() {
		return initialPatientPopulationId;
	}

	public void setInitialPatientPopulationId(InitialPatientPopulationId initialPatientPopulationId) {
		this.initialPatientPopulationId = initialPatientPopulationId;
	}

	public DenominatorPopulationId getDenominatorPopulationId() {
		return denominatorPopulationId;
	}

	public void setDenominatorPopulationId(DenominatorPopulationId denominatorPopulationId) {
		this.denominatorPopulationId = denominatorPopulationId;
	}

	public DenominatorExclusionsId getDenominatorExclusionsID() {
		return denominatorExclusionsID;
	}

	public void setDenominatorExclusionsID(DenominatorExclusionsId denominatorExclusionsID) {
		this.denominatorExclusionsID = denominatorExclusionsID;
	}

	public DenominatorExceptionsId getDenominatorExceptionsId() {
		return denominatorExceptionsId;
	}

	public void setDenominatorExceptionsId(DenominatorExceptionsId denominatorExceptionsId) {
		this.denominatorExceptionsId = denominatorExceptionsId;
	}

	public StratumId getStratumID() {
		return stratumID;
	}

	public void setStratumID(StratumId stratumID) {
		this.stratumID = stratumID;
	}

	public List<CategoryCatIII> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryCatIII> category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Measure [id=" + id + ", title=" + title + ", versionNeutralIdentifier=" + versionNeutralIdentifier
				+ ", versionNumber=" + versionNumber + ", versionSpecificMeasureId=" + versionSpecificMeasureId
				+ ", initialPatientPopulationId=" + initialPatientPopulationId + ", numeratorId=" + numeratorId
				+ ", denominatorPopulationId=" + denominatorPopulationId + ", denominatorExclusionsID="
				+ denominatorExclusionsID + ", denominatorExceptionsId=" + denominatorExceptionsId + ", stratumID="
				+ stratumID + ", category=" + category + "]";
	}

	
}
