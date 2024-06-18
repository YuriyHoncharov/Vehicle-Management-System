package ua.com.foxminded.yuriy.carrestservice.controller;

import org.springframework.data.domain.Pageable;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@Slf4j
@Tag(name = "CATEGORY END-POINTS")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	@Operation(description = "Add new Category to Data Base",
				summary = "Add New Category",
				security = {
						@SecurityRequirement(name = "Car Service API", scopes = {"create:resource"})})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
			@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
			@ApiResponse(responseCode = "409", description = "Entity (Category) already Exist")
	})	
	@PostMapping
	public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryPostDto category) {
		log.info("Calling save() method with JSON input : {}", category);
		CategoryDto createdCategory = categoryService.save(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}
	
	@Operation(description = "Update Category Information",
			summary = "Edit Category",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"edit:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
		@ApiResponse(responseCode = "409", description = "Entity (Category) already Exist")
})	
	@PutMapping
	public ResponseEntity<CategoryDto> update(@RequestBody @Valid CategoryPutDto category) {
		log.info("Calling update() method with JSON input : {}", category);
		CategoryDto updatedCategory = categoryService.update(category);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
	}
	
	@Operation(description = "Delete Category from Data Base",
			summary = "Delete Category",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"delete:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successful operation"),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
})	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Get category by ID",
			summary = "Get category by ID")					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
		@ApiResponse(responseCode = "404", description = "Entity (Category) not Found")})	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CategoryDto category = categoryService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
	
	@Operation(description = "Get entire list of categories",
			summary = "Get All Categories",
			parameters = {					
					@Parameter(name = "page", description = "page of pagination", example = "10", required = false),
					@Parameter(name = "size", description = "Size of the page for pagination", example = "10", required = false),
					@Parameter(name = "sort", description = "Sorting criteria in the format: property, asc|desc. Default is ascending. Multiple sort criteria are supported.", example = "name,asc", required = false)
			})					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CategoryDtoPage.class))}),
		@ApiResponse(responseCode = "403", description = "Error while fetching data")})	
	@GetMapping
	public ResponseEntity<CategoryDtoPage> getAll(Pageable pageable) {
		CategoryDtoPage categetoryDtoPage = categoryService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(categetoryDtoPage);
	}
}
