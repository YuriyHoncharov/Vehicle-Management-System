package ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto;

import java.util.List;
import lombok.Data;

@Data
public class CategoryDtoPage {
	private List<CategoryDto> categories;
	private long totalElements;
	private long totalPages;
}
