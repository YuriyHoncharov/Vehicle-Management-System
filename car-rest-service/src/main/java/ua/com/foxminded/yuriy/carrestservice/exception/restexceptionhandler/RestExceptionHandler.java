package ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = HTTPException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HTTPException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.valueOf(ex.getCode()), request);
	}

	private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus httpStatus, WebRequest webRequest) {
		ApiError apiError = new ApiError(httpStatus.value(), message,
				webRequest.getDescription(false).replace("uri=", ""));
		return ResponseEntity.status(httpStatus).body(apiError);
	}
}
