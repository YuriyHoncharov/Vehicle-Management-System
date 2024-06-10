package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

import org.springframework.http.HttpStatus;
import ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler.HTTPException;

public class EntityNotFoundException extends HTTPException {
	public EntityNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND.value());
		ExceptionLogger.logException(this);
	}
}
