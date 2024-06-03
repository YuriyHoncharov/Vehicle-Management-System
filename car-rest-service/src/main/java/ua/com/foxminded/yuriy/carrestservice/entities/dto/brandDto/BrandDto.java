package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelBasicDto;

@Data

public class BrandDto {
	@NotNull (message = "Id cannot be Null, please provide it.")
	@NotBlank
	private Long id;
	@NotBlank
	@NotNull (message = "Name of Brand cannot be null.")
	private String name;
	@NotNull
	private List<ModelBasicDto> models;
}
