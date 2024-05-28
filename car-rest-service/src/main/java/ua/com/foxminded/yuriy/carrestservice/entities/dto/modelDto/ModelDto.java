package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelDto {
	@NotNull (message = "Id cannot be Null, please provide it.")
	@NotBlank
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String brand;
}