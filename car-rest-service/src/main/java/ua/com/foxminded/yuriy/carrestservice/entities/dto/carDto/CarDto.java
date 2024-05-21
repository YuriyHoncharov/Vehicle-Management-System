package ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarDto {
	@NotNull
	private String brand;
	@NotNull
	private String model;
	@NotNull
	private int productionYear;
	@NotNull
	@Size(min = 1)
	private Set<String> categories;
}
