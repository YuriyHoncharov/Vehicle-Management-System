package ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ApiError {
	private HttpStatus status;
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;	
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	ApiError(HttpStatus status){
		this();
		this.status = status;
	}
	
	ApiError(HttpStatus status, Throwable ex){
		this();
		this.status = status;
		this.message = "Unexpected Error";
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	 ApiError(HttpStatus status, String message, Throwable ex) {
       this();
       this.status = status;
       this.message = message;
       this.debugMessage = ex.getLocalizedMessage();
   }
}
