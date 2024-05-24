package ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto;

import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarPutDto {

	@NotNull
	private Long id;
	@NotNull
	private String objectId;
	@NotNull
	private Long brandId;
	@NotNull
	private int productionYear;
	@NotNull
	private Long modelId;
	@NotEmpty
	private Set<Long> categories;

}
