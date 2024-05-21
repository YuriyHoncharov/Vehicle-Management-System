package ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {
	@NotNull
	private String name;
}
