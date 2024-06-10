package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;

@Data
public class ModelDto {
	@NotNull
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	private BasicDataDto brand;
}
