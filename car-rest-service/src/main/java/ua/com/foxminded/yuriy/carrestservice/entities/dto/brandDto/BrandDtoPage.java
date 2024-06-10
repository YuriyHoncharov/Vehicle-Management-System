package ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto;

import java.util.List;
import lombok.Data;

@Data
public class BrandDtoPage {
	private List<BrandDto> brands;
	private long totalPages;
	private long totalElements;

}
