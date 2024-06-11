package ua.com.foxminded.yuriy.carrestservice.controller;

import java.util.Map;
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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@RestController
@RequestMapping("/api/v1/car")
@Slf4j
public class CarController {

	private CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

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
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		carService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CarDto car = carService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}

	@GetMapping
	public ResponseEntity<CarDtoPage> getAllCars(@RequestParam(required = false) Map<String, String> filters) {
		log.info("Calling getAllCars() method with JSON input : {}", filters);
		CarDtoPage carDtoPage = carService.getAll(filters);
		return ResponseEntity.status(HttpStatus.OK).body(carDtoPage);
	}
}
