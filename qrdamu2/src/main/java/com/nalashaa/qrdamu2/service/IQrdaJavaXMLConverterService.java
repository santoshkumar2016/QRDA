
package com.nalashaa.qrdamu2.service;

import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.nalashaa.qrdamu2.model.ResultSetModel;
import com.nalashaa.qrdamu2.model.RetrieveMultipleValueSetsResponse;

public interface IQrdaJavaXMLConverterService {

    List<ResultSetModel> processResultSet(CachedRowSet cachedRowSet) throws Exception;

    Map<String, Object> processTemplate1Xml(CachedRowSet cachedRowSet, RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse, String providerId, String startDate, String endDate) throws Exception;

	String processTemplate3Xml(String providerId, String startDate, String endDate, List<String> measureList) throws Exception;

	void setCat3Header();

}
