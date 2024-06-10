package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandPutDto {
	@NotNull
	private Long id;
	@NotNull
	@NotEmpty
	private String name;
	private List<Long> models;

}
