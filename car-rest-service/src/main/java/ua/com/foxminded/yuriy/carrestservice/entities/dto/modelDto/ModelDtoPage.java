package ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto;

import java.util.List;
import lombok.Data;

@Data
public class ModelDtoPage {
	private List<ModelDto> models;
	private long totalElements;
	private long totalPages;
}
