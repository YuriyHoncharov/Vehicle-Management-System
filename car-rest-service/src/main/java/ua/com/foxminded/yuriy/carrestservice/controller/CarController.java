package ua.com.foxminded.yuriy.carrestservice.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class CarController {

	private final CarService carService;
	private static final Logger log = LoggerFactory.getLogger(CarController.class);
	private final ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<CarDto> save(@RequestBody @Valid CarPostDto car) {
		log.info("Calling save() method with JSON input : {}", car);
		CarDto createdCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
	}

	@PutMapping
	public ResponseEntity<CarDto> update(@RequestBody @Valid CarPutDto car) {
		log.info("Calling update() method with JSON input : {}", car);
		CarDto updatedCar = carService.update(car);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CarDto> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		carService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CarDto car = carService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}

	@GetMapping
	public ResponseEntity<CarDtoPage> getAllCars(@RequestParam(required = false) Map<String, String> filters) {
		String logJson;
		try {
			logJson = objectMapper.writeValueAsString(filters);
			log.info("Calling getAllCars() method with JSON input : {}", logJson);
		} catch (JsonProcessingException e) {
			log.error("Failed to convert filters to JSON", e);
			e.printStackTrace();
		}
		CarDtoPage carDtoPage = carService.getAll(filters);
		log.info("Calling getAll() method");
		return ResponseEntity.status(HttpStatus.OK).body(carDtoPage);
	}
}
