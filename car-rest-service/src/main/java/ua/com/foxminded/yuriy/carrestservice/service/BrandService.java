package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Set;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;

public interface BrandService {

	void delete(Long id);

	BrandDto save(BrandPostDto brand);

	BrandDto update(BrandPutDto brand);

	BrandDto getDtoById(Long id);

	Brand getById(Long id);

	Set<Brand> saveAll(Set<Brand> brands);

	BrandDtoPage getAll(Pageable pageable);

}
