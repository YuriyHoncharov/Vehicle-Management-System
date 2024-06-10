package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandPostDto {
	@NotNull
	private String name;
	private List<Long> models;
}
