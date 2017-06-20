
package com.nalashaa.qrdamu2.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nalashaa.qrdamu2.dto.QrdaDto;
import com.nalashaa.qrdamu2.exception.QrdaException;
import com.nalashaa.qrdamu2.model.Measure;
import com.nalashaa.qrdamu2.service.IProviderService;
import com.nalashaa.qrdamu2.service.IQrdaService;
import com.nalashaa.qrdamu2.service.IZipUtils;
import com.nalashaa.qrdamu2.util.RestAPIUtil;

@RestController
@RequestMapping("/qrda")
public class QrdaController {

    private static final Logger logger = LogManager.getLogger(QrdaController.class);

    @Autowired
    IQrdaService qrdaService;
    
    @Autowired
    IProviderService providerService;
    
    @Autowired
    RestAPIUtil restApiUtil;
    
    @Autowired
    IZipUtils zipUtils;

    @RequestMapping(value = "/createMU2files", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity<Map<String, Object>> createMU2files(@RequestBody QrdaDto qrdaDto, HttpServletResponse response) {
    	try {
            logger.info("Entered : QrdaController/createMU2files");
            if (logger.isDebugEnabled()) {
                logger.debug("Parameters Passed : " + qrdaDto);
                logger.debug("Calling qrdaService.createMU2files");
            }
            Map<String, Object> patientDetailsMap = qrdaService.createMU2files(qrdaDto);
            if (logger.isDebugEnabled()) {
                logger.debug("Came out of qrdaService.createMU2files");
            }
            logger.info("Exited : QrdaController/createMU2files");
            return new ResponseEntity<Map<String, Object>>(patientDetailsMap, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error in QrdaController/createMU2files", ex);
            throw new QrdaException(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/downloadMU2files", method = RequestMethod.GET, produces="application/zip")
    public ResponseEntity<byte[]> downloadMU2files(HttpServletResponse response) {
    	HashMap<String, Object> hashmap = new HashMap<>();
    	try {
            logger.info("Entered : QrdaController/downloadMU2files");
            zipUtils.zipQrdaFiles();
    		String fname= "QRDA.zip";
    		String contentType= "application/zip";
    		javax.servlet.ServletOutputStream servletoutputstream = null;
    		
    		if(fname.lastIndexOf("/")>0)
    			response.setContentType(contentType);
    			response.setHeader("Content-disposition","attachment; filename="+fname);
    			Path path = Paths.get("QRDA.zip");
        		byte[] blobAsBytes = Files.readAllBytes(path);
    			servletoutputstream = response.getOutputStream();
    		    servletoutputstream.write(blobAsBytes);
    		    servletoutputstream.close();
    		    servletoutputstream = null;
    		    hashmap.put("qrdaFiles", blobAsBytes);
    		    logger.info("Exited : QrdaController/downloadMU2files");
            return new ResponseEntity<byte[]>(blobAsBytes, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error in QrdaController/downloadMU2files", ex);
            throw new QrdaException(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/getProviders", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getProviders() {
        try {
            logger.info("Entered : QrdaController/getProviders");
            if (logger.isDebugEnabled()) {
                logger.debug("Calling qrdaService.getProviders");
            }
            List<String> providersList = providerService.getProviders();
            
            
//            
//            providersList.clear();
//            providersList.add("51020");
            if (logger.isDebugEnabled()) {
                logger.debug("Came out of qrdaService.getProviders");
            }
            logger.info("Exited : QrdaController/getProviders");
            return new ResponseEntity<List<String>>(providersList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error in QrdaController/getProviders", ex);
            throw new QrdaException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getMeasures", method = RequestMethod.GET)
    public ResponseEntity<List<Measure>> getMeasures() {
        try {
            logger.info("Entered : QrdaController/getMeasures");
            if (logger.isDebugEnabled()) {
                logger.debug("Calling qrdaService.getMeasures");
            }
            List<Measure> measuresList = restApiUtil.getMeasures();
            if (logger.isDebugEnabled()) {
                logger.debug("Came out of qrdaService.getMeasures");
            }
            logger.info("Exited : QrdaController/getMeasures");
            return new ResponseEntity<List<Measure>>(measuresList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error in QrdaController/getMeasures", ex);
            throw new QrdaException(ex.getMessage());
        }
    }
    @RequestMapping(value = "/uploadQrdaZip", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<Map<String, Boolean>> upload(@RequestParam("file") MultipartFile file) throws Exception {
    	logger.info("Entered : QrdaController/upload");
    	Map<String, Boolean> attachedOnSuccessMap = new HashMap<String, Boolean>();
    	if (file.isEmpty()) {
    		logger.error("QrdaController/upload : File is empty");
    		attachedOnSuccessMap.put("success", false);
    		return new ResponseEntity<Map<String, Boolean>>(attachedOnSuccessMap, HttpStatus.OK);
    	}else{
    		try {
    			qrdaService.importQrda(file);
    		} catch (Exception e) {
        		logger.error("Exception in QrdaController/upload", e);
        		throw new QrdaException(e.getMessage());
        	}
    	}
    	logger.info("Exited : QrdaController/upload");
    	return new ResponseEntity<Map<String, Boolean>>(attachedOnSuccessMap, HttpStatus.OK);
    }
    
    @ExceptionHandler(QrdaException.class)
    public ResponseEntity<String> CustomQrdaException(QrdaException qe) {
        return new ResponseEntity<String>(qe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(HttpServletRequest req, Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
