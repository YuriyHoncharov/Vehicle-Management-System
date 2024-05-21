package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandPutDto {
	@NotNull
	private Long id;
	@NotEmpty
	private Set<String> models;

}
