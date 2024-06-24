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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler.ApiError;
import ua.com.foxminded.yuriy.carrestservice.properties.SwaggerDescription;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@RestController
@RequestMapping("/api/v1/car")
@Slf4j
@Tag(name = "CAR END-POINTS")
@SecurityRequirement(name = "bearerAuth")
public class CarController {

	private final CarService carService;
	public CarController(CarService carService) {
		this.carService = carService;
	}
	
	@Operation(description = "Add new Car", summary = "Add New Car")
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "OK : Successful operation"), 
		    @ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class))),
		    @ApiResponse(responseCode = "400", description = "Bad Request : Model and Brand don't match each other", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
		    @ApiResponse(responseCode = "409", description = "Conflict : Car already exist with given ObjectID", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
	@PostMapping	
	public ResponseEntity<CarDto> save(@RequestBody @Valid CarPostDto car) {
		log.info("Calling save() method with JSON input : {}", car);
		CarDto createdCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
	}
	
	@Operation(description = "Update Car Information",	summary = "Edit Car")
		@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request : Model and Brand don't match each other", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
			@ApiResponse(responseCode = "409", description = "Conflict : Car already exist with given ObjectID", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})	
	@PutMapping	
	public ResponseEntity<CarDto> update(@RequestBody @Valid CarPutDto car) {
		log.info("Calling update() method with JSON input : {}", car);
		CarDto updatedCar = carService.update(car);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
	}
	
	@Operation(description = "Delete Car", summary = "Delete Car")
		@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation"),
			@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class)))})	
	@DeleteMapping("/{id}")	
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		carService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Get Car by ID", summary = "Get Car by ID")					
		@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "Entity (Car) not Found", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
	@GetMapping("/{id}")
	public ResponseEntity<CarDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CarDto car = carService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}
	
	@Operation(description = SwaggerDescription.CAR_GET_ALL_DESCRIPTION, summary = "Get All Cars")
		@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Filter Illegal Argument - Incorrect page/size/etc.. parameters", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})	
	@GetMapping
	public ResponseEntity<CarDtoPage> getAllCars(@RequestParam(required = true) Map<String, String> filters) {
		log.info("Calling getAllCars() method with JSON input : {}", filters);
		CarDtoPage carDtoPage = carService.getAll(filters);
		return ResponseEntity.status(HttpStatus.OK).body(carDtoPage);
	}
}
