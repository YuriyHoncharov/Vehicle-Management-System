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
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@RestController
@RequestMapping("/api/v1/models")
@AllArgsConstructor
public class ModelController {
	private final ModelService modelService;

	@PostMapping("/save")
	public ResponseEntity<Model> saveModel(@RequestBody Model model) {
		Model createdModel = modelService.save(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
	}

	@PutMapping("/update")
	public ResponseEntity<Model> update(@RequestBody Model model) {
		Model updatedModel = modelService.save(model);
		return ResponseEntity.status(HttpStatus.OK).body(updatedModel);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Model> delete(@PathVariable(value = "id") Long id) {
		modelService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Model> get(@PathVariable(value = "id") Long id) {
		Model model = modelService.getById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(model);
	}

}
