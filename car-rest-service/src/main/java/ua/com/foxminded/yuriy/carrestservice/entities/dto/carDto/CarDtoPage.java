package ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto;

import java.util.List;
import lombok.Data;

@Data
public class CarDtoPage {
	private List<CarDto> cars;
	private long totalPages;
	private long totalElements;
}
