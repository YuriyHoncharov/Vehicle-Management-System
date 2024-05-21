package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ModelPutDto {
	@NotNull
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String brand;

}
