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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

@RestController
@RequestMapping("/api/v1/brands")
@AllArgsConstructor
public class BrandController {

	private final BrandService brandService;

	@PostMapping
	public ResponseEntity<BrandDto> save(@RequestBody @Valid BrandPostDto brand) {
		BrandDto createdBrand = brandService.save(brand);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
	}

	@PutMapping
	public ResponseEntity<BrandDto> update(@RequestBody @Valid BrandPutDto brand) {
		BrandDto updatedBrand = brandService.update(brand);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBrand);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BrandDto> delete(@PathVariable(value = "id") Long id) {
		brandService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BrandDto> get(@PathVariable(value = "id") Long id) {
		BrandDto brand = brandService.getDtoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(brand);
	}
}
