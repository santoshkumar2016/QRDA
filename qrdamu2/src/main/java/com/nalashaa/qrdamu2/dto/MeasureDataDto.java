
package com.nalashaa.qrdamu2.dto;

import java.util.HashMap;
import java.util.Map;

public class MeasureDataDto {

    private Map<String, String> constantsMap = new HashMap<String, String>();

    private String measureString;

    public Map<String, String> getConstantsMap() {
        return constantsMap;
    }

    public void setConstantsMap(Map<String, String> constantsMap) {
        this.constantsMap = constantsMap;
    }

    public String getMeasureString() {
        return measureString;
    }

    public void setMeasureString(String measureString) {
        this.measureString = measureString;
    }
}
