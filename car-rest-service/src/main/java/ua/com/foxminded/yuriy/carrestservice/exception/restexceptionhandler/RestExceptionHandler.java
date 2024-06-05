package ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.ValidationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String error = "Malformed JSON request";
		log.info("HttpMessageNotReadableException: {}", error);
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
		apiError.setMessage(ex.getMessage());
		log.info("EntityNotFoundException: {}", ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ FilterIllegalArgumentException.class })
	protected ResponseEntity<Object> handleFilterIllegalArgumentException(FilterIllegalArgumentException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex);
		log.info("FilterIllegalArgumentException: {}", ex.getMessage());
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ EntityAlreadyExistException.class })
	protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
		apiError.setMessage(ex.getMessage());
		log.info("EntityAlreadyExistException: {}", ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ ValidationException.class })
	protected ResponseEntity<Object> handleValidationExceptions(ValidationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex);
		apiError.setMessage(ex.getMessage());
		log.info("ValidationException: {}", ex.getMessage());
		return buildResponseEntity(apiError);
	}

}
