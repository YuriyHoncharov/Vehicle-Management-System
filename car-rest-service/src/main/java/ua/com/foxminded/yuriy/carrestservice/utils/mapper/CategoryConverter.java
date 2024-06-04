package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryBasicDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDtoPage;

@Component
public class CategoryConverter {

	public CategoryDto convertToDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category.getName());
		categoryDto.setId(category.getId());
		return categoryDto;
	}

	public CategoryDtoPage convertoToCategoryDtoPage(Page<Category> categoreis) {
		CategoryDtoPage categoryDtoPage = new CategoryDtoPage();
		categoryDtoPage.setTotalElements(categoreis.getTotalElements());
		categoryDtoPage.setTotalPages(categoreis.getTotalPages());
		categoryDtoPage
				.setCategories(categoreis.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
		return categoryDtoPage;
	}
	
	public CategoryBasicDto convertToBasic(Category category) {
		CategoryBasicDto categoryBasicDto = new CategoryBasicDto();
		categoryBasicDto.setId(category.getId());
		categoryBasicDto.setName(category.getName());
		return categoryBasicDto;
	}
}
