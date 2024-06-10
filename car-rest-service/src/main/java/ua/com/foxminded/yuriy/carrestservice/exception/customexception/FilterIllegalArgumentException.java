package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

import org.springframework.http.HttpStatus;

import ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler.HTTPException;

public class FilterIllegalArgumentException extends HTTPException{

	public FilterIllegalArgumentException(String message) {
		super(message, HttpStatus.BAD_REQUEST.value());
		ExceptionLogger.logException(this);
		
	}

}
