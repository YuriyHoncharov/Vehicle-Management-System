package ua.com.foxminded.yuriy.carrestservice.entities.dto;

import java.util.List;
import lombok.Data;

@Data
public class CarPageDto {
	private List<CarDto> cars;
	private long totalPages;
	private long totalElements;
}
