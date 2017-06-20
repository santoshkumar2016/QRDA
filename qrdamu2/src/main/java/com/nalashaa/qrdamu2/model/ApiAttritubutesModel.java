
package com.nalashaa.qrdamu2.model;

public class ApiAttritubutesModel {

    private String displayName;

    private String conceptDisplayName;

    private String describedId;
    
    private String codeSystem;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getConceptDisplayName() {
        return conceptDisplayName;
    }

    public void setConceptDisplayName(String conceptDisplayName) {
        this.conceptDisplayName = conceptDisplayName;
    }

    public String getDescribedId() {
        return describedId;
    }

    public void setDescribedId(String describedId) {
        this.describedId = describedId;
    }

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

}
