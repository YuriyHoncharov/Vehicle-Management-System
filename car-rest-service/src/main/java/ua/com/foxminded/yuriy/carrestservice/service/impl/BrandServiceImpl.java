package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityAlreadyExistException;
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

	@Transactional
	@Override
	public BrandDto getDtoById(Long id) {
		Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with following ID not found : " + id.toString()));
		Hibernate.initialize(brand.getModels());
		BrandDto brandDto = brandConverter.convertToDto(brand);		
		
		return brandDto;
	}

	@Override
	public Brand getByName(String name) {
		return brandRepository.getByName(name)
				.orElseThrow(() -> new EntityNotFoundException("Entity with following name not found : " + name));
	}

	@Override
	@Transactional
	public BrandDto save(@Valid BrandPostDto brand) {
		if (checkIfBrandExists(brand.getName())) {
			throw new EntityAlreadyExistException("Brand with following name already exists : " + brand.getName());
		} else {
			Brand newBrand = new Brand();
			newBrand.setName(brand.getName());
			return brandConverter.convertToDto(brandRepository.save(newBrand));
		}
	}

	@Override
	@Transactional
	public BrandDto update(@Valid BrandPutDto brand) {
		if (checkIfBrandExists(brand.getName())) {
			throw new EntityAlreadyExistException("Brand with following name already exists : " + brand.getName());
		} else {
			Brand brandToUpdate = getById(brand.getId());
			Set<Model> models = brand.getModels().stream()
					.map(modelId -> modelRepository.findById(modelId).orElseThrow(
							() -> new EntityNotFoundException("Model with following ID was not found : " + modelId)))
					.collect(Collectors.toSet());
			brandToUpdate.setModels(models);
			return brandConverter.convertToDto(brandRepository.save(brandToUpdate));
		}
	}

	private boolean checkIfBrandExists(String name) {
		try {
			Brand existingBrand = getByName(name);
			return existingBrand != null;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	@Override
	@Transactional
	public Brand getById(Long id) {
		Brand brand = brandRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entity with following ID not found : " + id.toString()));
		Hibernate.initialize(brand.getModels());
		return brand;
	}

	@Override
	@Transactional
	public Set<Brand> saveAll(Set<Brand> brands) {
		return (brandRepository.saveAll(brands)).stream().collect(Collectors.toSet());
	}
}
