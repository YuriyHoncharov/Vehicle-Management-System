package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

import org.springframework.http.HttpStatus;

import ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler.HTTPException;

public class EntityAlreadyExistException extends HTTPException {
	public EntityAlreadyExistException(String message) {
		super(message, HttpStatus.CONFLICT.value());
		ExceptionLogger.logException(this);
	}
}
