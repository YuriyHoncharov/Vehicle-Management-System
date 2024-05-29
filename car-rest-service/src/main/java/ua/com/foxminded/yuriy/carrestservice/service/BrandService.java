package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Set;
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
	
	Brand getById(Long id);		
	
	Set<Brand>saveAll(Set<Brand>brands);
	
}
