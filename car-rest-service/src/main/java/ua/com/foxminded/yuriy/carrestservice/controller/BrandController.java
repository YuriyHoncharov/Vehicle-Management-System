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
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

@RestController
@RequestMapping("/api/v1/brands")
@AllArgsConstructor
public class BrandController {

	private final BrandService brandService;

	@PostMapping("/save")
	public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
		Brand createdBrand = brandService.save(brand);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
	}

	@PutMapping("/update")
	public ResponseEntity<Brand> update(@RequestBody Brand brand) {
		Brand updatedBrand = brandService.save(brand);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBrand);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Brand> delete(@PathVariable(value = "id") Long id) {
		brandService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Brand> get(@PathVariable(value = "id") Long id) {
		Brand brand = brandService.getById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(brand);
	}
}
