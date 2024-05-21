package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.CategoryRepository;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.CategoryConverter;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryConverter categoryConverter;

	@Override
	public Long delete(Long id) {
		categoryRepository.deleteById(id);
		return id;
	}

	@Override
	public CategoryDto save(@Valid CategoryDto category) {
		Category newCategory = new Category();
		newCategory.setName(category.getName());
		return categoryConverter.convertToDto(categoryRepository.save(newCategory));
	}

	@Override
	public CategoryDto update(@Valid CategoryPutDto category) {
		Category categoryToUpdate = categoryRepository.findById(category.getId()).orElseThrow(
				() -> new EntityNotFoundException("Category want not found with following ID : " + category.getId()));
		categoryToUpdate.setName(category.getName());
		return categoryConverter.convertToDto(categoryRepository.save(categoryToUpdate));
	}

	@Override
	public CategoryDto getById(Long id) {
		return categoryConverter.convertToDto(
				categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found")));
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.getByName(name).orElseThrow(() -> new EntityNotFoundException("Entity non found"));
	}

	@Override
	public Category save(String category) {
		Optional<Category> existingCategory = categoryRepository.getByName(category);
		if (!existingCategory.isPresent()) {
			Category newCategory = new Category();
			newCategory.setName(category);
			return categoryRepository.save(newCategory);
		} else {
			return existingCategory.get();
		}
	}

}
