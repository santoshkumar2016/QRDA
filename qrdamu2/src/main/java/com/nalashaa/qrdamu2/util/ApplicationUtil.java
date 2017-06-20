
package com.nalashaa.qrdamu2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApplicationUtil {

	private Logger logger = LogManager.getLogger(ApplicationUtil.class);
	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private final SimpleDateFormat DATE_TIME_FULL_FORMAT = new SimpleDateFormat("MMM dd,yyyy hh:mm");
	private final SimpleDateFormat STRING_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat STRING_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	public String generateUUID() {
		logger.info("Entered : ApplicationUtil - generateUUID");
		try {
			final String uuid = UUID.randomUUID().toString();
			logger.info("Exited : ApplicationUtil - generateUUID");
			return uuid;
		} catch (Exception e) {
			logger.error("Exception : ApplicationUtil - generateUUID", e);
			throw e;
		}
	}

	public String formatDateTime(Date date) {
		logger.info("Entered : ApplicationUtil - formatDateTime");
		if (date == null) {
			logger.info("Exited : ApplicationUtil - formatDateTime, With date as null");
			return "";
		} else {
			logger.info("Exited : ApplicationUtil - formatDateTime");
			//return DATE_TIME_FORMAT.format(date);
			
			// as per r3
			return STRING_DATE_FORMAT.format(date);
		}
	}

	public String formatFullDateTime(Date date) {
		logger.info("Entered : ApplicationUtil - formatFullDateTime");
		if (date == null) {
			logger.info("Exited : ApplicationUtil - formatFullDateTime, With date as null");
			return "";
		} else {
			logger.info("Exited : ApplicationUtil - formatFullDateTime");
			// System.out.println(DATE_TIME_FULL_FORMAT.format(date));
			return DATE_TIME_FULL_FORMAT.format(date);
		}
	}

	public String formatStringDateTime(String stringDate) throws ParseException {
		logger.info("Entered : ApplicationUtil - formatDateTime");
		if (StringUtils.isEmpty(stringDate)) {
			logger.info("Exited : ApplicationUtil - formatDateTime, With date as null");
			return "";
		} else {
			logger.info("Exited : ApplicationUtil - formatDateTime");
			Date date = STRING_DATE_TIME_FORMAT.parse(stringDate);
			//return DATE_TIME_FORMAT.format(date);
			
			// as per r3
			return DATE_TIME_FORMAT.format(date);
		}
	}

	public String formatFullStringDateTime(String stringDate) throws ParseException {
		logger.info("Entered : ApplicationUtil - formatDateTime");
		if (StringUtils.isEmpty(stringDate)) {
			logger.info("Exited : ApplicationUtil - formatDateTime, With date as null");
			return "";
		} else {
			logger.info("Exited : ApplicationUtil - formatDateTime");
			Date date = STRING_DATE_TIME_FORMAT.parse(stringDate);
			return DATE_TIME_FULL_FORMAT.format(date);
		}
	}

	/**
	 * yyyyMMdd to yyyy-MM-dd
	 * 
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public String formatToDateString(String stringDate) throws ParseException {
		logger.info("Entered : ApplicationUtil - formatDateTime");
		if (StringUtils.isEmpty(stringDate)) {
			logger.info("Exited : ApplicationUtil - formatDateTime, With date as null");
			return "";
		} else {
			logger.info("Exited : ApplicationUtil - formatDateTime");
			Date date = STRING_DATE_FORMAT.parse(stringDate);
			//return STRING_DATE_TIME_FORMAT.format(date);
			// as per r3
			return STRING_DATE_FORMAT.format(date);
		}
	}
}
