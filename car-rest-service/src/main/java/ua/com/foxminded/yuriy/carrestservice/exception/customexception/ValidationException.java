package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

public class ValidationException extends RuntimeException {
	public ValidationException(String message) {
		super(message);
	}
}
