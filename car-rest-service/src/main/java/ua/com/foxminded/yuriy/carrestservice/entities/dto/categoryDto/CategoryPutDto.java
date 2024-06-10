package ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryPutDto {
	@NotNull
	private Long id;
	@NotNull
	@NotBlank
	private String name;
}
