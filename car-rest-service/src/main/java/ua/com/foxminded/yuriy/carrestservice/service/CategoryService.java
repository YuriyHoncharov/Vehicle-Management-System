package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.List;

import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;

public interface CategoryService {

	Long delete(Long id);

	CategoryDto save(CategoryDto category);

	CategoryDto update(CategoryPutDto category);

	CategoryDto getDtoById(Long id);

	Category getByName(String name);

	Category save(String name);

	Category getById(Long id);
	
	void saveAll(List<Category>categories);

}
