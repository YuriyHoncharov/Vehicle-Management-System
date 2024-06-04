package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
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
	
	@Transactional
	@Override
	public CategoryDto save(@Valid CategoryPostDto category) {
		if (checkIfCategoryExists(category.getName())) {
			throw new EntityAlreadyExistException("Category with following name already exists : " + category.getName());
		}
		Category newCategory = new Category();
		newCategory.setName(category.getName());
		return categoryConverter.convertToDto(categoryRepository.save(newCategory));

	}
	
	@Transactional
	@Override
	public CategoryDto update(@Valid CategoryPutDto category) {
		if (checkIfCategoryExists(category.getName())) {
			throw new EntityAlreadyExistException("Category with following name already exists : " + category.getName());
		}
		Category categoryToUpdate = getById(category.getId());
		categoryToUpdate.setName(category.getName());
		return categoryConverter.convertToDto(categoryRepository.save(categoryToUpdate));
	}

	private boolean checkIfCategoryExists(String name) {
		try {
			Category existingCategory = getByName(name);
			return existingCategory != null;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	@Override
	public CategoryDto getDtoById(Long id) {
		return categoryConverter.convertToDto(categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with following ID not found : " + id.toString())));
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.getByName(name)
				.orElseThrow(() -> new EntityNotFoundException("Entity with following Name not found : " + name));
	}

	@Override
	public Category getById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with following ID not found : " + id.toString()));
	}
	
	@Transactional
	@Override
	public Set<Category> saveAll(Set<Category> categories) {
		return new HashSet<>(categoryRepository.saveAll(categories));
	}
}
