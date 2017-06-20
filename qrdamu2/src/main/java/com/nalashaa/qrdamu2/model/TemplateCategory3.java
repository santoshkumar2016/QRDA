
package com.nalashaa.qrdamu2.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "Template")
public class TemplateCategory3 {

	private String MEASURE_LIST;

    private String measure_inner_item;

    private String measure_list;

    private String measure_main_item;

    private String entryData;

    private String QRDA;

    private String MEASURETBODY;

    private String MEASUREDATA;

    private String entryRelationshipPaymentSource;

    private String entryRelationshipPatientRace;

    private String observationComponent;

    private String entryRelationshipPatientSex;

    private String entryRelationshipPatientEthnicity;
    
    private String componentIpp;
    
    private String componentDenominator;
    
    private String componentNumerator;
    
    private String componentDenominatorExclusions;
    
    private String componentDenominatorException;
    
    private String componentPerformanceRate;
    
    private String componentreportingRate;
    
    private String entryRelationshipStratum;

    public String getMEASURE_LIST ()
    {
        return MEASURE_LIST;
    }

    @XmlElement(name = "measure_list")
    public void setMEASURE_LIST (String MEASURE_LIST)
    {
        this.MEASURE_LIST = MEASURE_LIST;
    }

    public String getMeasure_inner_item ()
    {
        return measure_inner_item;
    }

    @XmlElement(name = "measure_inner_item")
    public void setMeasure_inner_item (String measure_inner_item)
    {
        this.measure_inner_item = measure_inner_item;
    }

    public String getMeasure_list ()
    {
        return measure_list;
    }

    @XmlElement(name = "measure_list")
    public void setMeasure_list (String measure_list)
    {
        this.measure_list = measure_list;
    }

    public String getMeasure_main_item ()
    {
        return measure_main_item;
    }

    @XmlElement(name = "measure_main_item")
    public void setMeasure_main_item (String measure_main_item)
    {
        this.measure_main_item = measure_main_item;
    }

    public String getEntryData ()
    {
        return entryData;
    }

    @XmlElement(name = "entryData")
    public void setEntryData (String entryData)
    {
        this.entryData = entryData;
    }

    public String getQRDA ()
    {
        return QRDA;
    }

    @XmlElement(name = "QRDA")
    public void setQRDA (String QRDA)
    {
        this.QRDA = QRDA;
    }

    public String getMEASURETBODY ()
    {
        return MEASURETBODY;
    }

    @XmlElement(name = "MEASURETBODY")
    public void setMEASURETBODY (String MEASURETBODY)
    {
        this.MEASURETBODY = MEASURETBODY;
    }

    public String getMEASUREDATA ()
    {
        return MEASUREDATA;
    }

    @XmlElement(name = "MEASUREDATA")
    public void setMEASUREDATA (String MEASUREDATA)
    {
        this.MEASUREDATA = MEASUREDATA;
    }

    public String getEntryRelationshipPaymentSource ()
    {
        return entryRelationshipPaymentSource;
    }

    @XmlElement(name = "entryRelationshipPaymentSource")
    public void setEntryRelationshipPaymentSource (String entryRelationshipPaymentSource)
    {
        this.entryRelationshipPaymentSource = entryRelationshipPaymentSource;
    }

    public String getEntryRelationshipPatientRace ()
    {
        return entryRelationshipPatientRace;
    }

    @XmlElement(name = "entryRelationshipPatientRace")
    public void setEntryRelationshipPatientRace (String entryRelationshipPatientRace)
    {
        this.entryRelationshipPatientRace = entryRelationshipPatientRace;
    }

    public String getObservationComponent ()
    {
        return observationComponent;
    }

    @XmlElement(name = "observationComponent")
    public void setObservationComponent (String observationComponent)
    {
        this.observationComponent = observationComponent;
    }

    public String getEntryRelationshipPatientSex ()
    {
        return entryRelationshipPatientSex;
    }

    @XmlElement(name = "entryRelationshipPatientSex")
    public void setEntryRelationshipPatientSex (String entryRelationshipPatientSex)
    {
        this.entryRelationshipPatientSex = entryRelationshipPatientSex;
    }

    public String getEntryRelationshipPatientEthnicity ()
    {
        return entryRelationshipPatientEthnicity;
    }

    @XmlElement(name = "entryRelationshipPatientEthnicity")
    public void setEntryRelationshipPatientEthnicity (String entryRelationshipPatientEthnicity)
    {
        this.entryRelationshipPatientEthnicity = entryRelationshipPatientEthnicity;
    }

	public String getComponentIpp() {
		return componentIpp;
	}

	@XmlElement(name = "componentIpp")
	public void setComponentIpp(String componentIpp) {
		this.componentIpp = componentIpp;
	}

	public String getComponentDenominator() {
		return componentDenominator;
	}

	@XmlElement(name = "componentDenominator")
	public void setComponentDenominator(String componentDenominator) {
		this.componentDenominator = componentDenominator;
	}

	public String getComponentNumerator() {
		return componentNumerator;
	}

	@XmlElement(name = "componentNumerator")
	public void setComponentNumerator(String componentNumerator) {
		this.componentNumerator = componentNumerator;
	}

	public String getComponentDenominatorExclusions() {
		return componentDenominatorExclusions;
	}

	@XmlElement(name = "componentDenominatorExclusions")
	public void setComponentDenominatorExclusions(String componentDenominatorExclusions) {
		this.componentDenominatorExclusions = componentDenominatorExclusions;
	}

	public String getComponentDenominatorException() {
		return componentDenominatorException;
	}

	@XmlElement(name = "componentDenominatorException")
	public void setComponentDenominatorException(String componentDenominatorException) {
		this.componentDenominatorException = componentDenominatorException;
	}

	public String getComponentPerformanceRate() {
		return componentPerformanceRate;
	}

	@XmlElement(name = "componentPerformanceRate")
	public void setComponentPerformanceRate(String componentPerformanceRate) {
		this.componentPerformanceRate = componentPerformanceRate;
	}

	public String getComponentreportingRate() {
		return componentreportingRate;
	}

	@XmlElement(name = "componentreportingRate")
	public void setComponentreportingRate(String componentreportingRate) {
		this.componentreportingRate = componentreportingRate;
	}

	public String getEntryRelationshipStratum() {
		return entryRelationshipStratum;
	}

	@XmlElement(name = "entryRelationshipStratum")
	public void setEntryRelationshipStratum(String entryRelationshipStratum) {
		this.entryRelationshipStratum = entryRelationshipStratum;
	}
	
}
