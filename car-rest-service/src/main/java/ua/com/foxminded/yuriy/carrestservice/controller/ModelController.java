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
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@RestController
@RequestMapping("/api/v1/model")
@Slf4j
@Tag(name = "MODEL END-POINTS")
public class ModelController {

	private ModelService modelService;

	public ModelController(ModelService modelService) {
		this.modelService = modelService;
	}
	
	@Operation(description = "Add new Model to Data Base",
			summary = "Add New Model",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"create:resource"})})
@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ModelDto.class))}),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
		@ApiResponse(responseCode = "409", description = "Entity with given Model name & Brand_ID already exist")
})
	@PostMapping
	public ResponseEntity<ModelDto> save(@RequestBody @Valid ModelPostDto model) {
		log.info("Calling save() method with JSON input : {}", model);
		ModelDto createdModel = modelService.save(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
	}
	
	@Operation(description = "Update Model Information",
			summary = "Edit Model",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"edit:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ModelDto.class))}),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
		@ApiResponse(responseCode = "409", description = "Entity with following Model & Brand name already exist"),
		@ApiResponse(responseCode = "404", description = "Model with given ID was not found")
})	
	@PutMapping
	public ResponseEntity<ModelDto> update(@RequestBody @Valid ModelPutDto model) {
		log.info("Calling update() method with JSON input : {}", model);
		ModelDto updatedModel = modelService.update(model);
		return ResponseEntity.status(HttpStatus.OK).body(updatedModel);
	}	
	
	@Operation(description = "Delete Model from Data Base",
			summary = "Delete Model",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"delete:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successful operation"),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
})	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		modelService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Get Model by ID",
			summary = "Get Model by ID")					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ModelDto.class))}),
		@ApiResponse(responseCode = "404", description = "Entity (Model) not Found")})
	@GetMapping("/{id}")
	public ResponseEntity<ModelDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		ModelDto model = modelService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(model);
	}
	
	@Operation(description = "Get entire list of Models",
			summary = "Get All Models",
			parameters = {					
					@Parameter(name = "page", description = "page of pagination", example = "10", required = false),
					@Parameter(name = "size", description = "Size of the page for pagination", example = "10", required = false),
					@Parameter(name = "sort", description = "Sorting criteria in the format: property, asc|desc. Default is ascending. Multiple sort criteria are supported.", example = "name,asc", required = false)
			})					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ModelDtoPage.class))}),
		@ApiResponse(responseCode = "403", description = "Error while fetching data")})	
	@GetMapping
	public ResponseEntity<ModelDtoPage> getAll(Pageable pageable) {
		ModelDtoPage modelDtoPage = modelService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(modelDtoPage);
	}

}
