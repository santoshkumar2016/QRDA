
package com.nalashaa.qrdamu2.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.nalashaa.qrdamu2.exception.QrdaException;
import com.nalashaa.qrdamu2.model.Emeasure;
import com.nalashaa.qrdamu2.model.Measure;
import com.nalashaa.qrdamu2.model.RetrieveMultipleValueSetsResponse;

@Component
public class RestAPIUtil {

    private static Logger logger = LogManager.getLogger(RestAPIUtil.class);

    @Value("${category.one.query}")
    private String cat1Query;

    @Value("${category.three.query}")
    private String cat3Query;

    @Value("${valueSet.endPoint}")
    private String valueSetEndPoint;

    @Value("${valueset.endPoint.userName}")
    private String userName;

    @Value("${valueSet.endPoint.password}")
    private String password;

    @Value("${query.node}")
    private String queryNode;

    @Value("${code.node}")
    private String codeNode;

    @Value("${qrda.measure}")
    private String measure;
    
    @Value("${qrda.valueset.files}")
    private String valueSetFiles;
    
    //just a bypass method, spring does not set value for a static variable
//    @Value("${qrda.valueset.files}")
//    public void setValueSetFiles(String value) {
//    	valueSetFiles = value;
//    }
    
    /*@Value("${describedvalueset.node}")
    private String describedValueSetNode;

    @Autowired
    private CodeConstants codeConstants;*/
    //@Cacheable("apiRestData")
    public String getValueSet(String measureId, String version) {
    	
    	readFileNames();
    	try {
			getValueSetCodes(measureId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
        logger.info("Entered: fetching valuesets");
        String restResource = valueSetEndPoint + "MeasureId=" + measureId + "&version=" + version;
        RestTemplate restTemplate = new RestTemplate();
        String plainCredentials = userName + ":" + password;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> rssResponse = restTemplate.exchange(restResource, HttpMethod.GET, requestEntity, String.class);
        if (rssResponse.getStatusCode() != HttpStatus.OK) {
            throw new QrdaException("Unable to retrieve valuesets");
        }
        //System.out.println(rssResponse.getBody());
        logger.info("Exited: fetching valuesets");
        return rssResponse.getBody();
    }

    public String replaceValues(String query, String startDate, String endDate, String providerId, String codeValues) {

        Map<String, String> map = new HashMap<>();
        map.put("START_DATE", "'" + startDate + "'");
        map.put("END_DATE", "'" + endDate + "'");
        map.put("PROVIDER_ID", "'" + providerId + "'");
        map.put("PROCEDURE_CODES_BIRTH_DATE", codeValues);
        map.put("PROCEDURE_CODES_PREVENTIVE", codeValues);
        map.put("PROCEDURE_CODES_REFERRAL", codeValues);
        map.put("PROCEDURE_CODES_CONSULTANT_REPORT", codeValues);
        //map.put("Code_System", codeValues);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            query = query.replace("'{%%" + entry.getKey() + "%%}'", entry.getValue());
        }

        if (StringUtils.isBlank(query)) {
            throw new QrdaException("Query Empty ");
        }

        return query;
    }

    public Document getDocument(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        return doc;
    }

    public void writeToXml(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("my-file.xml"));
        transformer.transform(source, result);
    }

    public RetrieveMultipleValueSetsResponse converToObject(String xmlValueSet) throws Exception {
        writeToXml(xmlValueSet);
        File file = new File("my-file.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveMultipleValueSetsResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse = (RetrieveMultipleValueSetsResponse) jaxbUnmarshaller.unmarshal(file);
        //boolean isFiledeleted = file.delete();
        if (retrieveMultipleValueSetsResponse == null) {
            throw new QrdaException("RetrieveMultipleValueSetsResponse is Empty ");
        }
        return retrieveMultipleValueSetsResponse;
    }

    @Cacheable("measureList")
    private Emeasure converToJava() throws Exception {
        File file = new ClassPathResource(measure).getFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(Emeasure.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Emeasure emeasure = (Emeasure) jaxbUnmarshaller.unmarshal(file);
        return emeasure;
    }

    public Measure getMeasureById(String measureId) throws Exception {
        logger.info("Entered : RestAPIUtil - getMeasureById");
        Measure measureModel = null;
        try {
            Emeasure emeasure = converToJava();
            if (emeasure != null) {
                List<Measure> measureList = emeasure.getMeasureList();
                for (Measure measure : measureList) {
                	//MU3 may change it match only the number and not the version
                    String actualMeasureId = measure.getId().substring(0, measure.getId().length() - 2);
                    if(measureId.contains("v")){
                    	measureId = measureId.substring(0, measureId.length()-2);
                    }
                    if (!StringUtils.isEmpty(actualMeasureId) && !StringUtils.isEmpty(measureId) && measureId.equalsIgnoreCase(actualMeasureId)) {
                        measureModel = measure;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception : RestAPIUtil - getMeasureById", e);
            throw e;
        }
        logger.info("Exited : RestAPIUtil - getMeasureById");
        return measureModel;
    }

    public String getQuery(String queryXml, String measureId) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(queryXml)));
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(queryNode + "'" + measureId + "']");
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nl.getLength(); i++) {
            Node currentItem = nl.item(i);
            String key = currentItem.getAttributes().getNamedItem("id").getNodeValue();
            if (measureId.equalsIgnoreCase(key)) {
                Element element = (Element) currentItem;
                NodeList queryList = element.getElementsByTagName("Query");
                for (int j = 0; j < queryList.getLength(); j++) {
                    Element currentNode = (Element) queryList.item(j);
                    String query = currentNode.getTextContent();
                    if (!(StringUtils.isEmpty(query))) {
                        return query.trim();
                    }/* else {
                        throw new QrdaException("Query Unavailable for the measure " + measureId);
                    }*/
                }
                break;
            }
        }
        return null;
    }

    //public String getCodes(String valueSet,String measureId) throws Exception {
    public String getCodes(String valueSet) throws Exception {
        logger.info("Entered :Fetching codes ");
        StringBuffer codeValues = new StringBuffer();
        String codes = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(valueSet)));
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(codeNode);
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < nl.getLength(); i++) {
            Node currentItem = nl.item(i);
            String codeValue = currentItem.getAttributes().getNamedItem("code").getNodeValue();
            if (i == nl.getLength() - 1) {
                codeValues.append("'" + codeValue + "'");
            } else {
                codeValues.append("'" + codeValue + "',");
            }
        }
        /*XPathExpression expr = xpath.compile(codeNode);
        List<String> describedValueSetList =  codeConstants.getConstantsMap().get(measureId);
        for(String displayName : describedValueSetList){
            XPathExpression expr = xpath.compile(describedValueSetNode + "'" + displayName + "']");
            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node currentItem = nl.item(i);
                String codeValue = currentItem.getAttributes().getNamedItem("code").getNodeValue();
                if (i == nl.getLength() - 1) {
                    codeValues.append("'" + codeValue + "'");
                } else {
                    codeValues.append("'" + codeValue + "',");
                }
            }
        }
       XPathExpression expr = xpath.compile(describedValueSetNode + "'" + measureId + "']");*/
        
        
        
        if (codeValues != null) codes = codeValues.toString();
        logger.info("Exited :Fetching codes ");

        if (StringUtils.isBlank(codes)) {
            throw new QrdaException("Codevalues Empty ");
        }
        return codes;
    }
    
    public List<Measure> getMeasures() throws Exception {
        logger.info("Entered : RestAPIUtil - getMeasures");
        List<Measure> measureList = new ArrayList<Measure>();
        try {
            Emeasure emeasure = converToJava();
            if (emeasure != null) {
                measureList = emeasure.getMeasureList();
            }
        } catch (Exception e) {
            logger.error("Exception : RestAPIUtil - getMeasures", e);
            throw e;
        }
        logger.info("Exited : RestAPIUtil - getMeasures");
        return measureList;
    }
    
    public void readFileNames(){
    	try {
    		Stream<Path> paths  = Files.find(Paths.get("."),
			           Integer.MAX_VALUE,
			           (path, attrs) -> attrs.isRegularFile()
                       && path.toString().startsWith("RetrieveMultipleValueSetsResponse_")
			);
    		
    		paths.filter(Objects::nonNull).forEach(fileItem->
			{
			System.out.println("filename" + fileItem.getFileName());
			  File file = fileItem.toFile();
		        JAXBContext jaxbContext;
				try {
					jaxbContext = JAXBContext.newInstance(RetrieveMultipleValueSetsResponse.class);
				
		        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		        RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse = (RetrieveMultipleValueSetsResponse) jaxbUnmarshaller.unmarshal(file);
		        //boolean isFiledeleted = file.delete();
		        if (retrieveMultipleValueSetsResponse == null) {
		            throw new QrdaException("RetrieveMultipleValueSetsResponse is Empty ");
		        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private static Map<String, RetrieveMultipleValueSetsResponse> valueSetMap = new HashMap<>();
    
    public void readValueSetDoc() throws Exception {
        
    	 if(valueSetMap.isEmpty()){
    	
    	String fileNames[] = valueSetFiles.split(",");
    	for(String fName : fileNames){
    			String measureId = fName.split("_")[1];
    			measureId = measureId.substring(0, measureId.length()- 4);
    			if(measureId.contains("v")){
    				measureId = measureId.substring(0, measureId.length()-2);
    			}
    		   File file = new ClassPathResource(fName).getFile();
    	        JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveMultipleValueSetsResponse.class);
    	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	        RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse = (RetrieveMultipleValueSetsResponse) jaxbUnmarshaller.unmarshal(file);
    	        //boolean isFiledeleted = file.delete();
    	        if (retrieveMultipleValueSetsResponse == null) {
    	            throw new QrdaException("RetrieveMultipleValueSetsResponse is Empty ");
    	        }
    	        
    	        valueSetMap.put(measureId, retrieveMultipleValueSetsResponse);
    	}
    	
    	 }
        //return retrieveMultipleValueSetsResponse;
    }
    
    
	public String getValueSetCodes(String measureId) throws Exception {
		logger.info("Entered :getValueSetCodes ");

		StringBuffer codeValues = new StringBuffer();

		readValueSetDoc();
		
		if(measureId.contains("v")){
			measureId = measureId.substring(0, measureId.length()-2);
		}

		if (valueSetMap != null && valueSetMap.get(measureId) != null) {
			valueSetMap.get(measureId).getDescribedValueSet().stream().filter(Objects::nonNull).forEach(
					DescValueSet -> DescValueSet.getConceptList().stream().filter(Objects::nonNull).forEach(item -> {
						item.getConcept().stream().filter(Objects::nonNull)
								.forEach(concept -> codeValues.append("'"+concept.getCode()+"',"));
					}));

		 codeValues.setLength(codeValues.length()-1);
		}
		 return codeValues.toString();

	}
	
    public RetrieveMultipleValueSetsResponse getMultiValSetResObj(String measureId) throws Exception {
    	// code 
    	if(measureId.contains("v")){
			measureId = measureId.substring(0, measureId.length()-2);
		}

       return valueSetMap.get(measureId);
    }

}
