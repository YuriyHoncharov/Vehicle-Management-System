package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExceptionLogger {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionLogger.class);

	public static void logException(Exception e) {
		logger.error("Exception occured : {}", e.getMessage());
	}
}
