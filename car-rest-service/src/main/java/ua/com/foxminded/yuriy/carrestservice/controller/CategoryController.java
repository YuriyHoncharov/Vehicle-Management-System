package ua.com.foxminded.yuriy.carrestservice.controller;

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
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping("/save")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		Category createdCategory = categoryService.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}

	@PutMapping("/update")
	public ResponseEntity<Category> update(@RequestBody Category category) {
		Category updatedCategory = categoryService.save(category);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Category> delete(@PathVariable(value = "id") Long id) {
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> get(@PathVariable(value = "id") Long id) {
		Category category = categoryService.getById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
}
