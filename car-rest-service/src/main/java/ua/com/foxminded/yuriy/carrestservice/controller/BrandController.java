package ua.com.foxminded.yuriy.carrestservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
   public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
       Brand createdBrand = brandService.save(brand);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
   }
}
