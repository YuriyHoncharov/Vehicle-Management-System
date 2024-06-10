package ua.com.foxminded.yuriy.carrestservice.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BasicDataDto {
	@NotNull
	private long id;
	@NotNull
	@NotBlank
	private String name;
}
