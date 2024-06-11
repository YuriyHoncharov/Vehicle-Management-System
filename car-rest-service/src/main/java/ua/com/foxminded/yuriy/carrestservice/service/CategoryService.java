package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Set;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;

public interface CategoryService {

	void delete(Long id);

	CategoryDto save(CategoryPostDto category);

	CategoryDto update(CategoryPutDto category);

	CategoryDto getDtoById(Long id);

	Category getByName(String name);

	Category getById(Long id);

	Set<Category> saveAll(Set<Category>categories);
	
	CategoryDtoPage getAll(Pageable pageable);

}
