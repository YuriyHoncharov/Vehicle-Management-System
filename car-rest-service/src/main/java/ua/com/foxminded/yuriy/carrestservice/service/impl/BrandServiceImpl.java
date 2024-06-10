package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.BrandConverter;

@Service
public class BrandServiceImpl implements BrandService {

	private BrandRepository brandRepository;
	private BrandConverter brandConverter;
	private ModelRepository modelRepository;
	private final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Autowired
	public BrandServiceImpl(BrandRepository brandRepository, BrandConverter brandConverter,
			ModelRepository modelRepository) {
		this.brandRepository = brandRepository;
		this.brandConverter = brandConverter;
		this.modelRepository = modelRepository;
	}

	@Override
	@Transactional
	public Long delete(Long id) {
		brandRepository.deleteById(id);
		return id;
	}

	@Override
	public BrandDto getDtoById(Long id) {
		return brandConverter.convertToDto(brandRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entity with following ID not found : " + id.toString())));
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
			Set<Model> models = brand.getModels().stream()
					.map(modelId -> modelRepository.findById(modelId).orElseThrow(
							() -> new EntityNotFoundException("Model with following ID was not found : " + modelId)))
					.filter((model -> model.getBrand() == null || model.getBrand().getName().equals(brand.getName())))
					.collect(Collectors.toSet());
			final Brand finalNewBrand = newBrand;
			models.forEach(model -> model.setBrand(finalNewBrand));
			newBrand.setModels(models);
			return brandConverter.convertToDto(brandRepository.save(newBrand));
		}
	}

	@Override
	@Transactional
	public BrandDto update(@Valid BrandPutDto brand) {

		Brand brandToUpdate = getById(brand.getId());
		Optional<Brand> existingBrandWithName = brandRepository.findByName(brand.getName());
		if (existingBrandWithName.isPresent() && !existingBrandWithName.get().getId().equals(brand.getId())) {
			throw new EntityAlreadyExistException(
					"Brand with the name '" + brand.getName() + "' already exists with a different ID.");
		}
		if (!brandToUpdate.getName().equals(brand.getName())) {
			brandToUpdate.setName(brand.getName());
			Set<Model> models = brand.getModels().stream()
					.map(modelId -> modelRepository.findById(modelId).orElseThrow(
							() -> new EntityNotFoundException("Model with following ID was not found : " + modelId)))
					.filter((model -> model.getBrand() == null || model.getBrand().getName().equals(brand.getName())))
					.collect(Collectors.toSet());
			brandToUpdate.setModels(models);
		}
		return brandConverter.convertToDto(brandRepository.save(brandToUpdate));
	}

	private boolean checkIfBrandExists(String name) {
		try {
			Brand existingBrand = getByName(name);
			return existingBrand != null;
		} catch (EntityNotFoundException e) {
			logger.info("Brand with following name was not found : {}", name);
			return false;
		}
	}

	@Override
	public Brand getById(Long id) {
		return brandRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Brand with following ID not found : " + id.toString()));
	}

	@Override
	@Transactional
	public Set<Brand> saveAll(Set<Brand> brands) {
		return new HashSet<>(brandRepository.saveAll(brands));
	}

	@Override
	public BrandDtoPage getAll(Pageable pageable) {
		return brandConverter.convertToPageDto(brandRepository.findAll(pageable));
	}
}
