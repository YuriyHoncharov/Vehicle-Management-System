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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.restexceptionhandler.ApiError;
import ua.com.foxminded.yuriy.carrestservice.properties.SwaggerDescription;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

@RestController
@RequestMapping("/api/v1/brand")
@Slf4j
@Tag(name = "BRAND END-POINTS")
@SecurityRequirement(name = "bearerAuth")
public class BrandController {

	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@Operation(description = "Add new Brand",	summary = "Add New Brand" )					
		    @ApiResponses(value = {
				@ApiResponse(responseCode = "201", description = "Successful operation"),
				@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class))),
				@ApiResponse(responseCode = "409", description = "Entity (Brand) already Exist", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
})
	@PostMapping
	public ResponseEntity<BrandDto> save(@RequestBody @Valid BrandPostDto brand) {
		log.info("Calling save() method with JSON input : {}", brand);
		BrandDto createdBrand = brandService.save(brand);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
	}
	
	@Operation(description = "Update Brand Information",summary = "Edit Brand")
			@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Successful operation"),
				@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class))),
				@ApiResponse(responseCode = "409", description = "Entity (Brand) already Exist", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
})	
	@PutMapping
	public ResponseEntity<BrandDto> update(@RequestBody @Valid BrandPutDto brand) {
		log.info("Calling update() method with JSON input : {}", brand);
		BrandDto updatedBrand = brandService.update(brand);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBrand);
	}
	
	@Operation(description = "Delete Brand" ,summary = "Delete Brand")
			@ApiResponses(value = {
				@ApiResponse(responseCode = "204", description = "Successful operation"),
				@ApiResponse(responseCode = "401", description = "Unauthorized & Incorrect Token", content = @Content(schema = @Schema(implementation = Void.class))) 
})	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() method for ID : {}", id);
		brandService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Get Brand by ID", summary = "Get Brand by ID")					
			@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Successful operation"),
				@ApiResponse(responseCode = "404", description = "Entity (Brand) not Found", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
	@GetMapping("/{id}")
	public ResponseEntity<BrandDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() method for ID : {}", id);
		BrandDto brand = brandService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(brand);
	}
	
	@Operation(summary = "Get All Brands", description = SwaggerDescription.BRAND_GET_ALL_DESCRIPTION)						
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Successful operation"),
		        @ApiResponse(responseCode = "400", description = "Filter Illegal Argument - Incorrect page/size/etc.. parameters", content = {@Content (mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})	
	@GetMapping
	public ResponseEntity<BrandDtoPage> getAllBrands(Pageable pageable) {
		BrandDtoPage brandDtoPage = brandService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(brandDtoPage);
	}
}
