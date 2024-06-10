package ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ApiError {
	private int status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String request;	
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	ApiError(int status){
		this();
		this.status = status;
	}
	
	ApiError(int status, String request){
		this();
		this.status = status;
		this.message = "Unexpected Error";
		this.request = request;
	}
	
	 ApiError(int status, String message, String request) {
       this();
       this.status = status;
       this.message = message;
       this.request = request;
   }
}
