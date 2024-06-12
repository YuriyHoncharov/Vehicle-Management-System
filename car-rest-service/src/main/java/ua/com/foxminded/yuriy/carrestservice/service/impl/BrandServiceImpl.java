package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BrandServiceImpl implements BrandService {

	private BrandRepository brandRepository;
	private BrandConverter brandConverter;
	private ModelRepository modelRepository;

	public BrandServiceImpl(BrandRepository brandRepository, BrandConverter brandConverter,
			ModelRepository modelRepository) {
		this.brandRepository = brandRepository;
		this.brandConverter = brandConverter;
		this.modelRepository = modelRepository;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		brandRepository.deleteById(id);
	}

	@Override
	public BrandDto getDtoById(Long id) {
		return brandConverter.convertToDto(brandRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entity with following ID not found : " + id.toString())));
	}

	@Override
	@Transactional
	public BrandDto save(@Valid BrandPostDto brand) {
		if (checkIfBrandExists(brand.getName())) {
			throw new EntityAlreadyExistException("Brand with following name already exists : " + brand.getName());
		} else {
			Set<Long> notAddedModelIds = new HashSet<>();
			Brand newBrand = new Brand();
			newBrand.setName(brand.getName());
			if (brand.getModels() != null) {
				Set<Model> models = brand.getModels().stream().map(modelId -> {
					Optional<Model> optionalModel = modelRepository.findById(modelId);
					if (optionalModel.isPresent()) {
						Model model = optionalModel.get();
						if (model.getBrand() == null || model.getBrand().getName().equals(brand.getName())) {
							return model;
						} else {
							notAddedModelIds.add(modelId);
							return null;
						}
					} else {
						notAddedModelIds.add(modelId);
						return null;
					}
				}).filter(Objects::nonNull).collect(Collectors.toSet());
				final Brand finalNewBrand = newBrand;
				models.forEach(model -> model.setBrand(finalNewBrand));
				newBrand.setModels(models);
			}
			BrandDto savedBrand = brandConverter.convertToDto(brandRepository.save(newBrand));
			if (!notAddedModelIds.isEmpty()) {
				log.info("Models with the following IDs were not added: {}", notAddedModelIds);
			}
			return savedBrand;
		}
	}

	@Override
	@Transactional
	public BrandDto update(@Valid BrandPutDto brand) {

		Brand brandToUpdate = getById(brand.getId());
		Optional<Brand> existingBrandWithName = brandRepository.findByName(brand.getName());
		Set<Long> notAddedModelIds = new HashSet<>();
		if (existingBrandWithName.isPresent() && !existingBrandWithName.get().getId().equals(brand.getId())) {
			throw new EntityAlreadyExistException(
					"Brand with the name '" + brand.getName() + "' already exists with a different ID.");
		}
		if (!brandToUpdate.getName().equals(brand.getName())) {
			brandToUpdate.setName(brand.getName());
		}
		if (brand.getModels() != null) {
			Set<Model> models = brand.getModels().stream().map(modelId -> {
				Optional<Model> optionalModel = modelRepository.findById(modelId);
				if (optionalModel.isPresent()) {
					Model model = optionalModel.get();
					if (model.getBrand() == null || model.getBrand().getName().equals(brand.getName())) {
						return model;
					} else {
						notAddedModelIds.add(modelId);
						return null;
					}
				} else {
					notAddedModelIds.add(modelId);
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toSet());
			final Brand finalNewBrand = brandToUpdate;
			models.forEach(model -> model.setBrand(finalNewBrand));
			brandToUpdate.setModels(models);
		}
		BrandDto brandDto = brandConverter.convertToDto(brandRepository.save(brandToUpdate));
		log.info("Models with the following IDs were not added: {}", notAddedModelIds);
		return brandDto;
	}

	private boolean checkIfBrandExists(String name) {
		log.info("Entering checkIfBrandExists with following name param : {}", name);
		boolean exist = brandRepository.findByName(name).isPresent();
		if (exist) {
			log.debug("Brand with following name : {}, exists in db.", name);
			return exist;
		} else {
			log.info("Brand with following name : {}, does not exists in db.", name);
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
		log.info("Calling getAll() with following pagealbe param : page - {}, sort - {}, size - {}  ",
				pageable.getPageNumber(), pageable.getSort(), pageable.getPageSize());
		try {
			BrandDtoPage result = brandConverter.convertToPageDto(brandRepository.findAll(pageable));
			log.info("Successfully fetched and converted data.");
			return result;
		} catch (Exception e) {
			log.error("Error occurred while fetching data: ", e);
			throw e;
		}
	}
}
