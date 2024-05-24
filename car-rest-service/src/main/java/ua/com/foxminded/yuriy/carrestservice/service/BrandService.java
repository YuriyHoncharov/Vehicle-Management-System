package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.List;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;

public interface BrandService {

	Long delete(Long id);

	BrandDto save(BrandPostDto brand);
	
	BrandDto update(BrandPutDto brand);

	BrandDto getDtoById(Long id);
	
	Brand getByName(String name);
	
	Brand save(String brandName);
	
	Brand getById(Long id);	
	
	void saveAll(List<Brand>brands);
	
}
