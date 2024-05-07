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

import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CarPageDto;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;


@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class CarController {

	private final CarService carService;

	@PostMapping("/save")
	public ResponseEntity<Car> saveCar(@RequestBody Car car) {
		Car createdCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
	}

	@PutMapping("/update")
	public ResponseEntity<Car> update(@RequestBody Car car) {
		Car updatedCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Car> delete(@PathVariable(value = "id") Long id) {
		carService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> get(@PathVariable(value = "id") Long id) {
		Car car = carService.getById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}
	
	@GetMapping
	public CarPageDto getAllCars(@RequestParam(required = false)Map<String, String>filters) {
		return carService.getAll(filters);
	}

}
