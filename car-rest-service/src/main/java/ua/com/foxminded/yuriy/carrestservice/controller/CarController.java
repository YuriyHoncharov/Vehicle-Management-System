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
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class CarController {

	private final CarService carService;

	@PostMapping
	public ResponseEntity<CarDto> save(@RequestBody @Valid CarPostDto car) {
		CarDto createdCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
	}

	@PutMapping
	public ResponseEntity<CarDto> update(@RequestBody @Valid CarPutDto car) {
		CarDto updatedCar = carService.update(car);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CarDto> delete(@PathVariable(value = "id") Long id) {
		carService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDto> get(@PathVariable(value = "id") Long id) {
		CarDto car = carService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}

	@GetMapping
	public CarDtoPage getAllCars(@RequestParam(required = false) Map<String, String> filters) {
		return carService.getAll(filters);

	}

}
