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
public class ModelController {

	private ModelService modelService;

	public ModelController(ModelService modelService) {
		this.modelService = modelService;
	}

	@PostMapping
	public ResponseEntity<ModelDto> save(@RequestBody @Valid ModelPostDto model) {
		log.info("Calling save() method with JSON input : {}", model);
		ModelDto createdModel = modelService.save(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
	}

	@PutMapping
	public ResponseEntity<ModelDto> update(@RequestBody @Valid ModelPutDto model) {
		log.info("Calling update() method with JSON input : {}", model);
		ModelDto updatedModel = modelService.update(model);
		return ResponseEntity.status(HttpStatus.OK).body(updatedModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		modelService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ModelDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		ModelDto model = modelService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(model);
	}

	@GetMapping
	public ResponseEntity<ModelDtoPage> getAll(Pageable pageable) {
		ModelDtoPage modelDtoPage = modelService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(modelDtoPage);
	}

}
