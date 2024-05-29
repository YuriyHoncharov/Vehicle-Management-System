package ua.com.foxminded.yuriy.carrestservice.exception;

public class EntityAlreadyExistException extends RuntimeException {
	public EntityAlreadyExistException(String message) {
		super(message);
	}
}
