package ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryBasicDto;

@Data
public class CarDto {
	@NotNull 
	@NotBlank
	private Long id;
	@NotNull
	@NotBlank
	private String brand;
	@NotNull
	@NotBlank
	private String objectId;
	@NotNull
	@NotBlank
	private String model;
	@NotNull
	@NotBlank
	private int productionYear;
	@NotNull
	private List<CategoryBasicDto> categories;
}
