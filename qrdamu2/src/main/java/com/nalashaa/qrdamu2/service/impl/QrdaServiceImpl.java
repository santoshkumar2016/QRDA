
package com.nalashaa.qrdamu2.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sql.rowset.CachedRowSet;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.nalashaa.qrdamu2.dao.ICategory1DetailsRepository;
import com.nalashaa.qrdamu2.dao.PatientDao;
import com.nalashaa.qrdamu2.dto.QrdaDto;
import com.nalashaa.qrdamu2.entity.Category1Entity;
import com.nalashaa.qrdamu2.exception.QrdaException;
import com.nalashaa.qrdamu2.model.CategoryCatIII;
import com.nalashaa.qrdamu2.model.Measure;
import com.nalashaa.qrdamu2.model.PatientDetailsModel;
import com.nalashaa.qrdamu2.model.RetrieveMultipleValueSetsResponse;
import com.nalashaa.qrdamu2.service.IQrdaJavaXMLConverterService;
import com.nalashaa.qrdamu2.service.IQrdaService;
import com.nalashaa.qrdamu2.util.ApplicationConstants;
import com.nalashaa.qrdamu2.util.ImportXpathConstants;
import com.nalashaa.qrdamu2.util.JavaXMLConverterUtil;
import com.nalashaa.qrdamu2.util.RestAPIUtil;

@Component
@Transactional
@PropertySource("classpath:application.properties")
public class QrdaServiceImpl implements IQrdaService {

    private static final Logger logger = LogManager.getLogger(QrdaServiceImpl.class);

    @Value("${category.one.query}")
    private String cat1Query;

    @Value("${basepath}")
    private String basepath;

    @Autowired
    private RestAPIUtil restApi;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private IQrdaJavaXMLConverterService qrdaJavaXMLConverterService;
    
    @Autowired
    private ICategory1DetailsRepository category1DetailsRepository; 

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private JavaXMLConverterUtil javaXMLConverterUtil;
    
    @SuppressWarnings("unchecked")
	@Override
    public Map<String, Object> createMU2files(QrdaDto qrdaDto) {
    	Map<String, Object> patientDetailsMap = new HashMap<>();
    	patientDetailsMap.put("cat1PatientDetails", new ArrayList<>());
        try {
            logger.info("Entered : QrdaServiceImpl/createMU2files");
            if (logger.isDebugEnabled()) {
                logger.debug("Calling QrdaServiceImpl/validateQrdaDto");
                logger.debug("qrdaDto : " + qrdaDto);
            }
            validateQrdaDto(qrdaDto);
            if (logger.isDebugEnabled()) {
                logger.debug("Validation Complete. Came out of QrdaServiceImpl/validateQrdaDto");
            }
            String startDate = dateFormat.format(qrdaDto.getStartDate());
            String endDate = dateFormat.format(qrdaDto.getEndDate());
            String queryxml = getCat1Query();
            if (logger.isDebugEnabled()) {
                logger.debug("Start Date : " + startDate);
                logger.debug("End Date : " + endDate);
                logger.debug("Category-1 Query : " + queryxml);
            }
            for (String providerId : qrdaDto.getProviderList()) {
            	ArrayList<String> measureList = new ArrayList<>();
                for (String measureId : qrdaDto.getMeasureList()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Calling generatefiles");
                    }
                    Map<String, Object> cat1PatientDetailsMap = generateFiles(queryxml, measureId, providerId, startDate, endDate);
                    if(cat1PatientDetailsMap.get("cat1PatientDetails") != null){
                    	ArrayList<PatientDetailsModel> patientDetailsModelList = (ArrayList<PatientDetailsModel>)cat1PatientDetailsMap.get("cat1PatientDetails");
                    	ArrayList<PatientDetailsModel> cat1PatientDetailsModelList = (ArrayList<PatientDetailsModel>)patientDetailsMap.get("cat1PatientDetails");
                    	cat1PatientDetailsModelList.addAll(patientDetailsModelList);
                    	patientDetailsMap.put("cat1PatientDetails", cat1PatientDetailsModelList);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("Came out of generatefiles");
                    }
                    measureList.add(measureId);
                }
                generateFilesForCat3(providerId, startDate, endDate, measureList);
            }
            logger.info("Exiting : QrdaServiceImpl/createMU2files");
        } catch (QrdaException ex) {
            throw new QrdaException(ex.getMessage());
        } catch (Exception ex) {
            throw new QrdaException(ex.getMessage());
        }
        return patientDetailsMap;
    }

    private void validateQrdaDto(QrdaDto qrdaDto) {
        logger.info("Entered QrdaServiceImpl/validateQrdaDto ");
        List<String> providerList = qrdaDto.getProviderList();
        List<String> measureList = qrdaDto.getMeasureList();

        String startDate = dateFormat.format(qrdaDto.getStartDate());
        String endDate = dateFormat.format(qrdaDto.getEndDate());

        if (CollectionUtils.isEmpty(providerList)) {
            logger.error("Provider List Empty");
            throw new QrdaException("Provider List Empty");
        }

        if (CollectionUtils.isEmpty(measureList)) {
            logger.error("Measure List Empty");
            throw new QrdaException("Measure List Empty");
        }

        logger.info("Entered : QrdaController - create category 1 files for : " + providerList);

        if (startDate == null || endDate == null) {
            logger.info("Illegal arguements passed");
            throw new QrdaException("Illegal arguements passed");
        }
        logger.info("Exiting QrdaServiceImpl/validateQrdaDto ");
    }

    private String getCat1Query() {
        logger.info("Entered QrdaServiceImpl/getCat1Query");
        StringBuilder query = new StringBuilder();
        String line = null;
        Resource resource = new ClassPathResource(cat1Query);
        InputStream is;
        BufferedReader br;

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Entered Try Block");
            }
            is = resource.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                query.append(line);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Category-1 Query : " + query);
                logger.debug("Going out of Try Block");
            }
        } catch (IOException ex) {
            String msg = "IO Exception in QrdaServiceImpl/getCat1QueryXml";
            logger.error(msg);
            throw new QrdaException(msg);
        } finally {
            line = null;
            resource = null;
            is = null;
            br = null;
        }

        logger.info("Exiting QrdaServiceImpl/getCat1Query");
        return query.toString();
    }
    
    @Async
    private void generateFilesForCat3(String providerId, String startDate, String endDate, List<String> measureList) {
    	logger.info("Entered QrdaServiceImpl/generateFilesForCat3");
    	try{
    		Date now = new Date();
            String today = dateFormat.format(now);
            List<String> directoryList = new ArrayList<String>();
            String category3Directory = basepath + providerId + "\\" + today + "\\Category-3\\";
            directoryList.add(category3Directory);
            createDirectory(directoryList);
            String content = qrdaJavaXMLConverterService.processTemplate3Xml(providerId, startDate, endDate, measureList);
            content = javaXMLConverterUtil.beautifyXML(content);
            createCat3Files(content, category3Directory);
            
            //createCatIIIFiles
            getCategoryCodesCatIII(measureList);
    	} catch (Exception ex) {
    		logger.error("Exception QrdaServiceImpl/generateFilesForCat3", ex);
            throw new QrdaException("Exception in QrdaServiceImpl/generateFilesForCat3" + ex.getMessage());
        }
    	logger.info("Exited QrdaServiceImpl/generateFilesForCat3");
    }
    
    //MU3 : Cat III file generator 
    private void getCategoryCodesCatIII( List<String> measureList) {
    	logger.info("Entered QrdaServiceImpl/generateFilesForCat3");
    	try{
    		
    		 for (String measureId : measureList) {
    			 System.out.println("MeasureId : "+measureId);
    			 
    			 RetrieveMultipleValueSetsResponse valueSetResponse = restApi.getMultiValSetResObj(measureId);
    			 
    			 StringBuffer measureCodes = new StringBuffer();    			 
    			 
    			 Measure measure = restApi.getMeasureById(measureId);
    			 List<CategoryCatIII> categoryList = measure.getCategory();
    			 if(categoryList != null){
    			 categoryList.stream().filter(Objects::nonNull).forEach(category -> {
    			 System.out.println("Category: "+category.getCategoryName());
    			 StringBuffer categoryCodes = new StringBuffer();
    			 category.getDataElement().stream().filter(Objects::nonNull).forEach(dataElement -> {
    				 System.out.println("Date Element Name : "+dataElement.getName());
    				 if(valueSetResponse != null) {
    				 valueSetResponse.getDescribedValueSet().stream().filter(Objects::nonNull).forEach(describedValueSet -> {
    					 if(describedValueSet.getDisplayName().equals(dataElement.getName())){
    						 StringBuffer dataElementCodes = new StringBuffer();
    						 describedValueSet.getConceptList().stream().filter(Objects::nonNull).forEach(conceptList -> {
    							 conceptList.getConcept().stream().forEach(concept -> {
    								 dataElementCodes.append("'"+concept.getCode()+"',");
    								// measureCodes.append("'"+concept.getCode()+"',");
    							 });
    						 });
    						 System.out.println("dataElementCodes : " + dataElementCodes.toString());
    						 categoryCodes.append(dataElementCodes);
    					 }
    				 });
    				// System.out.println("Codes : "+deItem.getCodes().getValue());
    				 }
    				
    			 });
    			 System.out.println("categoryCodes : " + categoryCodes.toString());
    			 measureCodes.append(categoryCodes);
    			 });
    			 }
    			 if(measureCodes.length() > 1){
    				 //remove the last unwanted comma
    				 measureCodes.setLength( measureCodes.length() -1);
    			 }
    			 
    			 System.out.println("measureCodes : " + measureCodes.toString());
    		 }
    		
    		
    	} catch (Exception ex) {
    		logger.error("Exception QrdaServiceImpl/generateFilesForCat3", ex);
            throw new QrdaException("Exception in QrdaServiceImpl/generateFilesForCat3" + ex.getMessage());
        }
    	logger.info("Exited QrdaServiceImpl/generateFilesForCat3");
    }

    private void setCat3Header( List<String> measureList){
    	
    	qrdaJavaXMLConverterService.setCat3Header();
    }
    
    
    @Async
    private Map<String, Object> generateFiles(String queryxml, String measureId, String providerId, String startDate, String endDate) {
         logger.info("Entered QrdaServiceImpl/generateFiles");
        Map<String, Object> cat1DetailsMap = new HashMap<>();
        try {
            String valueSet = null;
            String codeValues = null;
            String finalQuery = null;
            Date now = new Date();
            String today = dateFormat.format(now);
            List<String> directoryList = new ArrayList<String>();
            String category1Directory = basepath + providerId + "\\" + today + "\\Category-1\\" + measureId + "\\";
            //String category3Directory = basepath + providerId + "\\" + today + "\\Category-3\\";
            directoryList.add(category1Directory);
            //directoryList.add(category3Directory);
            createDirectory(directoryList);
            if (logger.isDebugEnabled()) {
                logger.debug("List of Directories Created : " + directoryList);
                logger.debug("queryxml : " + queryxml);
                logger.debug("measureId : " + measureId);
                logger.debug("providerId : " + providerId);
                logger.debug("startDate : " + startDate);
                logger.debug("endDate : " + endDate);
            }
//            String query = restApi.getQuery(queryxml, measureId);
//            if (logger.isDebugEnabled()) {
//                logger.debug("Query : " + query);
//            }
//            valueSet = getValuesets(measureId);
//            if (logger.isDebugEnabled()) {
//                logger.debug("valueSet : " + valueSet);
//            }
//            codeValues = restApi.getCodes(valueSet);
//            //codeValues = restApi.getCodes(valueSet,measureId);
//            RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse = restApi.converToObject(valueSet);
            
            if(measureId.contains("v")){
            	measureId = measureId.substring(0, measureId.length() -2);
            }
            
            codeValues = restApi.getValueSetCodes(measureId);
            if(codeValues != null && !codeValues.isEmpty()) {
            
            RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse = restApi.getMultiValSetResObj(measureId);
            if (logger.isDebugEnabled()) {
                logger.debug("codeValues : " + codeValues);
                logger.debug("retrieveMultipleValueSetsResponse : " + retrieveMultipleValueSetsResponse);
            }
           // finalQuery = restApi.replaceValues(query, startDate, endDate, providerId, codeValues);
            if (logger.isDebugEnabled()) {
                logger.debug("finalQuery : " + finalQuery);
            }
            
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//            //Date parsed = format.parse(startDate).;
//            java.sql.Date sqlStartDat = new java.sql.Date(format.parse(startDate).getTime());
//            java.sql.Date sqlEndDat = new java.sql.Date(format.parse(endDate).getTime());
            
            // test using input table
				finalQuery = "Select * from mu_qrda_inputs where REPORTSTARTDATE >= Date('" + startDate
						+ "') and REPORTENDDATE <= Date('" + endDate + "') and NQFID = " + "'"
						+ measureId+ "'"; //+"and ProviderId = " +Integer.valueOf(providerId);

            CachedRowSet rowSet = patientDao.getPatientDetails(finalQuery);
            if (rowSet == null) {
                throw new QrdaException("RowSet is Empty ");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("rowSet : " + rowSet);
            }
            Map<String, Object> fileDetails = qrdaJavaXMLConverterService.processTemplate1Xml(rowSet, retrieveMultipleValueSetsResponse, providerId, startDate, endDate);
            cat1DetailsMap.put("cat1PatientDetails", fileDetails.get("cat1PatientDetails"));
            if (logger.isDebugEnabled()) {
                logger.debug("fileDetails : " + fileDetails);
                logger.debug("Calling createCat1Files");
            }
            createCat1Files(fileDetails, category1Directory);
            if (logger.isDebugEnabled()) {
                logger.debug("Coming out of createCat1Files");
            }
            
            } else {
            	logger.info("No code value found for measureId : " + measureId);
            }
        } catch (SQLException ex) {
            throw new QrdaException("SQLException in QrdaServiceImpl/generateFiles" + ex.getMessage());
        } catch (Exception ex) {
            throw new QrdaException("Exception in QrdaServiceImpl/generateFiles" + ex.getMessage());
        }
        logger.info("Exiting QrdaServiceImpl/generateFiles");
        return cat1DetailsMap;
    }

    private String getValuesets(String measureId) {
        logger.info("Entered QrdaServiceImpl/getValuesets");
        String response = null;
        try {
            String[] measures = measureId.split(",");
            List<String> measureList = Arrays.asList(measures);
            if (!(CollectionUtils.isEmpty(measureList))) {
                for (String measure : measureList) {
                    String[] measureIdAndVersion = measure.split("v");
                    if (StringUtils.isNoneBlank(measureIdAndVersion)) {
                        if (2 == measureIdAndVersion.length) {
                            response = restApi.getValueSet(measureIdAndVersion[0], measureIdAndVersion[1]);
                            logger.info(response);
                            System.out.println(response);
                        } else {
                            throw new QrdaException("Measure is not correct");
                        }
                    } else {
                        throw new QrdaException("Incorrect Measure. version Details Unavailable");
                    }
                }
            } else {
                throw new QrdaException("Measure List is Empty");
            }
        } catch (Exception ex) {
            throw new QrdaException("Exception in QrdaServiceImpl/getValuesets : " + ex.getMessage());
        }
        //logger.info("Response : " + response);
        logger.info("Exiting QrdaServiceImpl/getValuesets");
        return response;
    }

	private void createCat1Files(Map<String, Object> fileDetails, String cat1directory) {
		logger.info("Entered saveFiles");
		String fileName = null;
		String content = null;
		try {
			for (String name : fileDetails.keySet()) {
				fileName = cat1directory + name + ".xml";
				if (fileDetails.get(name) instanceof String) {
					content = (String) fileDetails.get(name);
					if (logger.isDebugEnabled()) {
						logger.debug("Complete File Name - " + fileName);
						logger.debug("File Name - " + name + ".xml");
						logger.debug("Content in " + fileName);
						logger.debug(content);
						logger.debug("Calling writeContentToXML");
					}
					writeContentToXML(fileName, content);
					if (logger.isDebugEnabled()) {
						logger.debug("Came back from writeContentToXML");
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in QrdaServiceImpl/saveFiles" + ex.getMessage());
			logger.error("Directory : " + cat1directory);
			logger.error("Complete Path : " + fileName);
			logger.error("Content : " + content);
			// throw new QrdaException("Exception in
			// QrdaServiceImpl-saveFiles");
		}
		logger.info("Exiting saveFiles");
	}
    
	private void createCat3Files(String file, String cat3directory) throws Exception {
		logger.info("Entered QrdaServiceImpl - createCat3Files");
		String fileName = null;
		String content = null;
		try {
			fileName = cat3directory + ApplicationConstants.QRDA_CAT3_FILE_NAME + ".xml";
			
			int index = file.indexOf("</ClinicalDocument>");
			file = file.substring(0, index+19);
			
			content = file;
			System.out.println(content);
			writeContentToXML(fileName, content);
			
			
			
			qrdaJavaXMLConverterService.setCat3Header();
			if (logger.isDebugEnabled()) {
				logger.debug("Came back from writeContentToXML");
			}

		} catch (Exception ex) {
			logger.error("Exception in QrdaServiceImpl - createCat3Files" + ex.getMessage());
			logger.error("Directory : " + cat3directory);
			logger.error("Complete Path : " + fileName);
			logger.error("Content : " + content);
			throw ex;
		}
		logger.info("Exiting QrdaServiceImpl - createCat3Files");
	}

    private void createDirectory(List<String> directoryList) {
        logger.info("Entered createDirectories");
        try {
            for (String directoryPath : directoryList) {
                logger.debug("Creating Directory : " + directoryPath);
                File file = new File(directoryPath);
                if (!file.exists()) {
                    logger.debug(directoryPath + " -  Directory does not exist. Creating it now");
                    file.mkdirs();
                    logger.debug(directoryPath + " -  Directory Created");
                }
            }

        } catch (Exception ex) {
            logger.error("Exception in QrdaServiceImpl/createDirectories");
            throw new QrdaException("Exception in QrdaServiceImpl/createDirectories");
        }
        logger.info("Exiting createDirectories");
    }

    private void writeContentToXML(String fileName, String content) throws IOException {
        logger.info("Entering writeContentToXML");
        try {
        	
        	String temp = content.substring(0, content.indexOf("</ClinicalDocument>")+19);
        	System.out.println(fileName+ ":" + temp);
            Files.write(Paths.get(fileName), temp.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception ex) {
            logger.error("Exception in QrdaServiceImpl/writeContentToXML");
            throw new QrdaException("Exception in QrdaServiceImpl/writeContentToXML");
        }
        logger.info("Exiting writeContentToXML");
    }
    
    @Override
    public void importQrda(MultipartFile file){
    	logger.info("Entering in QrdaServiceImpl/importQrda");
    	ZipFile zipFile = null;
    	FileOutputStream fos = null;
    	List<Category1Entity> category1EntityList = new ArrayList<>();
        try {
    		File convFile = new File(file.getOriginalFilename());
    	    convFile.createNewFile(); 
    	    fos = new FileOutputStream(convFile); 
    	    fos.write(file.getBytes());
    		
    		zipFile = new ZipFile(convFile);
    		Enumeration<? extends ZipEntry> entries = zipFile.entries();
    	    while(entries.hasMoreElements()){
    	        ZipEntry entry = entries.nextElement();
    	        InputStream stream = zipFile.getInputStream(entry);
    	        StringWriter writer = new StringWriter();
    	        IOUtils.copy(stream, writer, "UTF-8");
    	        String theString = writer.toString();
    	        Category1Entity category1Entity = prepareModelAndSaveFiles(theString);
    	        category1EntityList.add(category1Entity);
    	    }
    	    if(!CollectionUtils.isEmpty(category1EntityList))
    	    	category1DetailsRepository.save(category1EntityList);
    	} catch (Exception ex) {
            logger.error("Exception in QrdaServiceImpl/importQrda", ex);
            throw new QrdaException("Exception in QrdaServiceImpl/importQrda");
        }finally{
        	try{
        		if(zipFile != null)
        			zipFile.close();
        		if(fos != null)
        			fos.close();
        	}catch(IOException e){
        		logger.error("IOException in finally QrdaServiceImpl/importQrda", e);
                throw new QrdaException("IOException in finally QrdaServiceImpl/importQrda");
        	}
        }
        logger.info("Exiting in QrdaServiceImpl/importQrda");
    }

	private Category1Entity prepareModelAndSaveFiles(String cat1XmlString) {
		logger.info("Entering in QrdaServiceImpl/prepareModelAndSaveFiles");
		Category1Entity category1Entity = new Category1Entity();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(new InputSource(new StringReader(cat1XmlString)));
	        XPathFactory xPathfactory = XPathFactory.newInstance();
	        XPath xpath = xPathfactory.newXPath();
	        
	        XPathExpression given = xpath.compile(ImportXpathConstants.PATIENT_GIVEN_NAME);
	        String givenName = (String) given.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(givenName))
	        	category1Entity.setPatientNameFirst(givenName);
	        
	        XPathExpression family = xpath.compile(ImportXpathConstants.PATIENT_FAMILY_NAME);
	        String familyName = (String) family.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(familyName))
	        	category1Entity.setPatientLastName(familyName);
	        
	        XPathExpression patientAddrStreetExpr = xpath.compile(ImportXpathConstants.PATIENT_ADDR_STREET);
	        String patientAddrStreet = (String) patientAddrStreetExpr.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(patientAddrStreet))
	        	category1Entity.setPatientAddressStreet(patientAddrStreet);
	        
	        XPathExpression patientAddrCityExpr = xpath.compile(ImportXpathConstants.PATIENT_ADDR_CITY);
	        String patientAddrCity = (String) patientAddrCityExpr.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(patientAddrCity))
	        	category1Entity.setPatientAddressCity(patientAddrCity);
	        
	        XPathExpression patientAddrStateExpr = xpath.compile(ImportXpathConstants.PATIENT_ADDR_STATE);
	        String patientAddrState = (String) patientAddrStateExpr.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(patientAddrState))
	        	category1Entity.setPatientAddressState(patientAddrState);
	        
	        XPathExpression patientAddrPostaleExpr = xpath.compile(ImportXpathConstants.PATIENT_ADDR_POSTAL);
	        String patientAddrPostal = (String) patientAddrPostaleExpr.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(patientAddrPostal))
	        	category1Entity.setPatientAddressPostalCode(patientAddrPostal);
	        
	        XPathExpression patientAddrCountryExpr = xpath.compile(ImportXpathConstants.PATIENT_ADDR_COUNTRY);
	        String patientAddrCountry = (String) patientAddrCountryExpr.evaluate(document, XPathConstants.STRING);
	        if(!StringUtils.isEmpty(patientAddrCountry))
	        	category1Entity.setPatientAddressCountry(patientAddrCountry);
	        
		}catch(Exception e){
    		logger.error("Exception in QrdaServiceImpl/prepareModelAndSaveFiles", e);
            throw new QrdaException("Exception in QrdaServiceImpl/prepareModelAndSaveFiles");
    	}
		logger.info("Exiting in QrdaServiceImpl/prepareModelAndSaveFiles");
		return category1Entity;
	}
}