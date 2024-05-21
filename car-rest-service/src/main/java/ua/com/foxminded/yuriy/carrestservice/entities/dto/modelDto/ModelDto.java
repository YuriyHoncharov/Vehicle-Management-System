package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelDto {
	@NotNull
	private String name;
	@NotNull
	private String brand;
}
