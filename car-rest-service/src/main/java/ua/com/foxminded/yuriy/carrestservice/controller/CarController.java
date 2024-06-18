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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@RestController
@RequestMapping("/api/v1/car")
@Slf4j
@Tag(name = "CAR END-POINTS")
public class CarController {

	private CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}
	
	@Operation(description = "Add new Car to Data Base",
			summary = "Add New Car",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"create:resource"})})
@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "OK : Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CarDto.class))}),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
		@ApiResponse(responseCode = "400", description = "Bad Request : Model and Brand don't match each other"),
		@ApiResponse(responseCode = "409", description = "Conflict : Car already exist with given ObjectID")
})
	@PostMapping	
	public ResponseEntity<CarDto> save(@RequestBody @Valid CarPostDto car) {
		log.info("Calling save() method with JSON input : {}", car);
		CarDto createdCar = carService.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
	}
	
	@Operation(description = "Update Car Information",
			summary = "Edit Car",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"edit:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CarDto.class))}),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
		@ApiResponse(responseCode = "400", description = "Bad Request : Model and Brand don't match each other"),
		@ApiResponse(responseCode = "409", description = "Conflict : Car already exist with given ObjectID")
})	
	@PutMapping	
	public ResponseEntity<CarDto> update(@RequestBody @Valid CarPutDto car) {
		log.info("Calling update() method with JSON input : {}", car);
		CarDto updatedCar = carService.update(car);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCar);
	}
	
	@Operation(description = "Delete Car from Data Base",
			summary = "Delete Car",
			security = {
					@SecurityRequirement(name = "Car Service API", scopes = {"delete:resource"})})
		@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Successful operation"),
		@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token"),
})	
	@DeleteMapping("/{id}")	
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() for ID : {}", id);
		carService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Get Car by ID",
			summary = "Get Car by ID")					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CarDto.class))}),
		@ApiResponse(responseCode = "404", description = "Entity (Car) not Found")})
	@GetMapping("/{id}")
	public ResponseEntity<CarDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() for ID : {}", id);
		CarDto car = carService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
	}
	
	@Operation(description = "Get entire list of Cars",
			summary = "Get All Cars",
			parameters = {					
					@Parameter(name = "page", description = "page of pagination", example = "10", required = false),
					@Parameter(name = "size", description = "Size of the page for pagination", example = "10", required = false),
					@Parameter(name = "sortOrder", description = "Sorting criteria in the format: asc|desc. Default is ascending. Multiple sort criteria are supported.", example = "name,asc", required = false),
					@Parameter(name = "sortBy", description = "Sorting criteria in the format: property. Default is Model. Multiple sort criteria are supported.", example = "name,asc", required = false),
					@Parameter(name = "brand", description = "Sort cars by Brand name. Multiple sort criteria are supported.", example = "Audi, Chevrolet, Acura", required = false),
					@Parameter(name = "category", description = "Sort cars by Category name. Multiple sort criteria are supported.", example = "SUV, Sedan, Pickup", required = false),
					@Parameter(name = "model", description = "Sort cars by Model name. Multiple sort criteria are supported.", example = "Q3, RLX, Encore", required = false),
					@Parameter(name = "year", description = "Sort cars by Year of Production. Possible to pass 2 parameters : [y0, y1] where (y0) is *MIN* and (y0) *MAX*."
							+ "If only one parameter is passed - final criteria will be Greater Then Or Equal To the passed parameter"
							+ "If first parameter (y0) is equal to *zero* - final criteria will be Less or Equal to the second parameter", example = "[2020] | [0,2020] | [2015,2015]", required = false)
			})					
		@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = CarDtoPage.class))}),
		@ApiResponse(responseCode = "400", description = "Filter Illegal Argument - Incorrect page/size/etc.. parameters"),	
		@ApiResponse(responseCode = "403", description = "Error while fetching data")})	
	
	@GetMapping
	public ResponseEntity<CarDtoPage> getAllCars(@RequestParam(required = false) Map<String, String> filters) {
		log.info("Calling getAllCars() method with JSON input : {}", filters);
		CarDtoPage carDtoPage = carService.getAll(filters);
		return ResponseEntity.status(HttpStatus.OK).body(carDtoPage);
	}
}
