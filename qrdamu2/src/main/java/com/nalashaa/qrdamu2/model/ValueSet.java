
package com.nalashaa.qrdamu2.model;

import java.util.HashMap;
import java.util.Map;

public class ValueSet {

    private Map<String, String> constantsMap = new HashMap<String, String>();

    public Map<String, String> getConstantsMap() {
        return constantsMap;
    }

    public void setConstantsMap(Map<String, String> constantsMap) {
        this.constantsMap = constantsMap;
    }
}
