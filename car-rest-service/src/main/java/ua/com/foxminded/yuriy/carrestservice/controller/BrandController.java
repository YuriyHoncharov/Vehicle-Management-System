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
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

@RestController
@RequestMapping("/api/v1/brand")
@Slf4j
public class BrandController {

	private BrandService brandService;

	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}

	@PostMapping
	public ResponseEntity<BrandDto> save(@RequestBody @Valid BrandPostDto brand) {
		log.info("Calling save() method with JSON input : {}", brand);
		BrandDto createdBrand = brandService.save(brand);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
	}

	@PutMapping
	public ResponseEntity<BrandDto> update(@RequestBody @Valid BrandPutDto brand) {
		log.info("Calling update() method with JSON input : {}", brand);
		BrandDto updatedBrand = brandService.update(brand);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBrand);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		log.info("Calling delete() method for ID : {}", id);
		brandService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BrandDto> get(@PathVariable(value = "id") Long id) {
		log.info("Calling get() method for ID : {}", id);
		BrandDto brand = brandService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(brand);
	}

	@GetMapping
	public ResponseEntity<BrandDtoPage> getAllBrands(Pageable pageable) {
		BrandDtoPage brandDtoPage = brandService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(brandDtoPage);
	}
}
