package ua.com.foxminded.yuriy.carrestservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping
	public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryPostDto category) {
		log.info("Calling save() method with JSON input : {}", category);
		CategoryDto createdCategory = categoryService.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}

	@PutMapping
	public ResponseEntity<CategoryDto> update(@RequestBody @Valid CategoryPutDto category) {
		log.info("Calling update() method with JSON input : {}", category);
		CategoryDto updatedCategory = categoryService.update(category);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryDto> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CategoryDto category = categoryService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
}
