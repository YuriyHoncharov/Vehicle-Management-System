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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@RestController
@RequestMapping("/api/v1/models")
@AllArgsConstructor
public class ModelController {
	private final ModelService modelService;

	@PostMapping
	public ResponseEntity<ModelDto> save(@RequestBody @Valid ModelDto model) {
		ModelDto createdModel = modelService.save(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
	}

	@PutMapping
	public ResponseEntity<ModelDto> update(@RequestBody @Valid ModelPutDto model) {
		ModelDto updatedModel = modelService.update(model);
		return ResponseEntity.status(HttpStatus.OK).body(updatedModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ModelDto> delete(@PathVariable(value = "id") Long id) {
		modelService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ModelDto> get(@PathVariable(value = "id") Long id) {
		ModelDto model = modelService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(model);
	}

}
