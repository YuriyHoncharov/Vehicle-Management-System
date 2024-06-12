package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.CategoryRepository;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.CategoryConverter;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private CategoryConverter categoryConverter;

	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
		this.categoryRepository = categoryRepository;
		this.categoryConverter = categoryConverter;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		categoryRepository.deleteById(id);
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

	@Override
	public CategoryDtoPage getAll(Pageable pageable) {		
		log.info("Calling getAll() with following pagealbe param : page - {}, sort - {}, size - {}  ",
				pageable.getPageNumber(), pageable.getSort(), pageable.getPageSize());
		try {
			CategoryDtoPage result = categoryConverter.convertoToCategoryDtoPage(categoryRepository.findAll(pageable));
			log.info("Successfully fetched and converted data.");
			return result;
		} catch (Exception e) {
			log.error("Error occurred while fetching data: ", e);
			throw e;
		}
	}
}
