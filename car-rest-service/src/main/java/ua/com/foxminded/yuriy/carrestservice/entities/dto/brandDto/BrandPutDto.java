package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandPutDto {
	@NotNull
	@NotBlank
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	private Set<Long> models;

}
