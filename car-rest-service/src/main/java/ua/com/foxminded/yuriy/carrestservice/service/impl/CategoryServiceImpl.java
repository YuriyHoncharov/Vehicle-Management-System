package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.repository.CategoryRepository;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Long delete(Long id) {
		categoryRepository.deleteById(id);
		return id;
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Page<Category> getAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Optional<Category> getById(Long id) {
		return categoryRepository.findById(id);
	}

}
