package ua.com.foxminded.yuriy.carrestservice.exception.customexception;

public class EntityAlreadyExistException extends RuntimeException {
	public EntityAlreadyExistException(String message) {
		super(message);
	}
}
