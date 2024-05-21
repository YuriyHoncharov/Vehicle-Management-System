package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.BrandConverter;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

	private final BrandRepository brandRepository;
	private final BrandConverter brandConverter;
	private final ModelRepository modelRepository;

	@Override
	public Long delete(Long id) {
		brandRepository.deleteById(id);
		return id;
	}
	
	@Override
	public BrandDto getById(Long id) {
		return brandConverter.convertToDto(
				brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity non found")));
	}

	@Override
	public Brand getByName(String name) {
		return brandRepository.getByName(name).orElseThrow(() -> new EntityNotFoundException("Entity non found"));
	}

	@Override
	public Brand save(String brandName) {

		Optional<Brand> existingBrand = brandRepository.getByName(brandName);
		Brand brand = new Brand();
		
		if (!existingBrand.isPresent()) {
			brand = new Brand();
			brand.setName(brandName);
			
		} else {
			
			brand = existingBrand.get();
		}
		return brandRepository.save(brand);

	}

	@Override
	public BrandDto save(@Valid BrandPostDto brand) {
		Brand newBrand = new Brand();
		brand.setName(brand.getName());
		return brandConverter.convertToDto(brandRepository.save(newBrand));
	}

	@Override
	public BrandDto update(@Valid BrandPutDto brand) {
		Brand brandToUpdate = brandRepository.findById(brand.getId())
				.orElseThrow(() -> new EntityNotFoundException("Brand with following ID was not found : " + brand.getId()));
		Set<Model> models = brand.getModels().stream()
				.map(modelString -> modelRepository.getByName(modelString).orElseThrow(
						() -> new EntityNotFoundException("Model with following name was not found : " + modelString)))
				.collect(Collectors.toSet());
		brandToUpdate.setModels(models);
		Brand updatedBrand = brandRepository.save(brandToUpdate);
		return brandConverter.convertToDto(updatedBrand);
	}
}
