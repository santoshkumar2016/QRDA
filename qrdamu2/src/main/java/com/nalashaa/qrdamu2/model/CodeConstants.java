
package com.nalashaa.qrdamu2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeConstants {

    private Map<String, List<String>> constantsMap = new HashMap<String, List<String>>();
    
    public Map<String, List<String>> getConstantsMap() {
        return constantsMap;
    }

    public void setConstantsMap(Map<String, List<String>> constantsMap) {
        this.constantsMap = constantsMap;
    }
}
