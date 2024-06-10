package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDtoPage;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryConverterTest {

	private final CategoryConverter categoryConverter = new CategoryConverter();

	@Test
	void convertToDto_shouldReturnCorrectFields() {
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryName");

		CategoryDto categoryDto = categoryConverter.convertToDto(category);

		assertEquals(category.getId(), categoryDto.getId());
		assertEquals(category.getName(), categoryDto.getName());
	}

	@Test
	void convertToCategoryDtoPage_shouldReturnCorrectPage() {
		List<Category> categoryList = new ArrayList<>();
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryName");

		for (int i = 0; i < 25; i++) {
			categoryList.add(category);
		}

		Page<Category> page = new PageImpl<>(categoryList, PageRequest.of(0, 10), 25);

		CategoryDtoPage categoryDtoPage = categoryConverter.convertoToCategoryDtoPage(page);

		assertEquals(page.getTotalElements(), categoryDtoPage.getTotalElements());
		assertEquals(page.getTotalPages(), categoryDtoPage.getTotalPages());
		assertEquals(page.getContent().size(), categoryDtoPage.getCategories().size());
	}

	@Test
	void convertToBasic_shouldReturnCorrectFields() {
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryName");

		BasicDataDto categoryBasicDto = BasicDataDtoConverter.convertToBasicDataDto(category);

		assertEquals(category.getId(), categoryBasicDto.getId());
		assertEquals(category.getName(), categoryBasicDto.getName());
	}
}
