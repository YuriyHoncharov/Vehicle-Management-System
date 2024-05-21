package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandDto {
	@NotNull
	private String name;	
	private Set<String> models;
}
