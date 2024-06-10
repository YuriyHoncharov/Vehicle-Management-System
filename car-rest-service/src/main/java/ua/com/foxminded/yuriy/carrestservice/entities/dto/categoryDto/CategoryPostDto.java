package ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryPostDto {
	@NotNull
	@NotBlank
	private String name;
}
