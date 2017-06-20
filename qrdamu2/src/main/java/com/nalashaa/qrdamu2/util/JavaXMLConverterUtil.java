
package com.nalashaa.qrdamu2.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * 
 * @author prathap
 *
 */
@Component
@PropertySource("classpath:application.properties")
public class JavaXMLConverterUtil {

    private static final Logger logger = LogManager.getLogger(JavaXMLConverterUtil.class);

    @Value("${qrda.template.cat1}")
    private String templateCat1;

    /**
     * 
     * @param content
     * @param keySet
     * @return
     * This function will return the replaced values of the string, Replaces all keys with format {%% %%} 
     */
    public String replaceWithValues(String content, Map<String, String> keySet) {
        logger.info("Entered : JavaXMLConverterUtil - replaceWithValues");
        try {
            for (Map.Entry<String, String> entry : keySet.entrySet()) {
            	if(entry.getKey().equals(ApplicationConstants.PATIENT_DATA)){
            		System.out.println("{%%" + entry.getKey() + "%%} ::"  +entry.getValue());
            	} else {
                content = content.replace("{%%" + entry.getKey() + "%%}", entry.getValue());
            	}
            }
        } catch (Exception e) {
            logger.error("Exception : JavaXMLConverterUtil - replaceWithValues", e);
            throw e;
        }
        logger.info("Exited : JavaXMLConverterUtil - replaceWithValues");
        return content;
    }
    
    /**
     * Beautify the XML Stirng 
     * @param xml
     * @return
     * @throws Exception
     */
    public String beautifyXML(String xml) throws Exception{
    	logger.info("Entered : JavaXMLConverterUtil - beautifyXML");
    	String beautifiedXML = "";
    	try {
    		if(!StringUtils.isEmpty(xml)){
    			// Turn xml string into a document
    			Document document = DocumentBuilderFactory.newInstance()
    					.newDocumentBuilder()
    					.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

    			Transformer transformer = TransformerFactory.newInstance().newTransformer();
    			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    			//initialize StreamResult with File object to save to file
    			StreamResult result = new StreamResult(new StringWriter());
    			DOMSource source = new DOMSource(document);
    			transformer.transform(source, result);
    			beautifiedXML = result.getWriter().toString();
    		}
    	} catch (Exception e) {
    		logger.error("Exception : JavaXMLConverterUtil - beautifyXML", e);
    		throw e;
    	}
    	logger.info("Exited : JavaXMLConverterUtil - beautifyXML");
    	return beautifiedXML;
    }

}
