package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String message) {
		super(message);
	}
}
