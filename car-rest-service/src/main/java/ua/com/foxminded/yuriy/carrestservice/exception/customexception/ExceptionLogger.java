package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ExceptionLogger {
	public static void logException(Exception e) {
		log.error("Exception occured : {}", e.getMessage());
	}
}
