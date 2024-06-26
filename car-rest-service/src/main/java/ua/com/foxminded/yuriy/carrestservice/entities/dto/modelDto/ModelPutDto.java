package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ModelPutDto {
	@NotNull
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	private Long brandId;

}
