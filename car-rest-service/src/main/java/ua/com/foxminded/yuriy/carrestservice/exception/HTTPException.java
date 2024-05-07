package ua.com.foxminded.yuriy.carrestservice.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HTTPException extends RuntimeException {

	private final int code;

	public HTTPException(String message, int code) {
		super(message);
		this.code = code;
	}

	public HTTPException(String message) {
		super(message);
		this.code = HttpStatus.FORBIDDEN.value();
	}
}
