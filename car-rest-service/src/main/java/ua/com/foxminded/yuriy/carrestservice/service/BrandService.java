package ua.com.foxminded.yuriy.carrestservice.service;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;

public interface BrandService {

	Long delete(Long id);

	BrandDto save(BrandPostDto brand);
	
	BrandDto update(BrandPutDto brand);

	BrandDto getById(Long id);
	
	Brand getByName(String name);
	
	Brand save(String brandName);
}
