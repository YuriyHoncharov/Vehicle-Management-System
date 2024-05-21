package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;

@Component
public class BrandConverter {

	public BrandDto convertToDto(Brand brand) {
		BrandDto brandDto = new BrandDto();
		brandDto.setName(brand.getName());
		brandDto.setModels(brand.getModels().stream().map(Model::getName).collect(Collectors.toSet()));
		return brandDto;
	}

	public BrandDtoPage convertToPageDto(Page<Brand> brands) {
		BrandDtoPage brandPageDto = new BrandDtoPage();
		brandPageDto.setTotalElements(brands.getTotalElements());
		brandPageDto.setTotalPages(brands.getTotalPages());
		brandPageDto
				.setBrands(brands.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
		return brandPageDto;
	}
}
