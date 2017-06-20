package com.nalashaa.qrdamu2.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.nalashaa.qrdamu2.model.Emeasure;
import com.nalashaa.qrdamu2.model.Measure;

public class XMLPropertiesUtil {

	private Logger logger = LogManager.getLogger(XMLPropertiesUtil.class);

	private static Emeasure emeasure = null;

	static {
		File file;
		try {
			file = new ClassPathResource("measure.xml").getFile();

			JAXBContext jaxbContext = JAXBContext.newInstance(Emeasure.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			emeasure = (Emeasure) jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Measure getMeasureById(String measureId) throws Exception {
		logger.info("Entered : RestAPIUtil - getMeasureById");
		Measure measureModel = null;
		try {

			if (emeasure != null) {
				List<Measure> measureList = emeasure.getMeasureList();
				for (Measure measure : measureList) {
					// MU3 may change it match only the number and not the
					// version
					String actualMeasureId = measure.getId().substring(0, measure.getId().length() - 2);
					if (measureId.contains("v")) {
						measureId = measureId.substring(0, measureId.length() - 2);
					}
					if (!StringUtils.isEmpty(actualMeasureId) && !StringUtils.isEmpty(measureId)
							&& measureId.equalsIgnoreCase(actualMeasureId)) {
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
}
