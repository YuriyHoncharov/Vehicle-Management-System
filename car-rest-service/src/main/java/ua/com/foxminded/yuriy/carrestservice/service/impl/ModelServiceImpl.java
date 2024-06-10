package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.ModelConverter;

@Service
public class ModelServiceImpl implements ModelService {

	private ModelRepository modelRepository;
	private ModelConverter modelConverter;
	private BrandService brandService;
	private final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);

	@Autowired
	public ModelServiceImpl(ModelRepository modelRepository, ModelConverter modelConverter, BrandService brandService) {
		this.modelRepository = modelRepository;
		this.modelConverter = modelConverter;
		this.brandService = brandService;
	}

	@Override
	@Transactional
	public Long delete(Long id) {
		modelRepository.deleteById(id);
		return id;
	}

	@Transactional
	@Override
	public ModelDto save(@Valid ModelPostDto model) {
		if (checkIfModelExists(model.getName(), model.getBrandId())) {
			throw new EntityAlreadyExistException("Entity with following Model & Brand_ID name already exist : "
					+ model.getName() + " - " + model.getBrandId());
		}
		Model newModel = new Model();
		newModel.setName(model.getName());
		if (model.getBrandId() != null) {
			newModel.setBrand(brandService.getById(model.getBrandId()));
		}
		Model savedModel = modelRepository.save(newModel);
		return modelConverter.convetToModelDto(savedModel);
	}

	@Transactional
	@Override
	public ModelDto update(@Valid ModelPutDto model) {
		Brand brand = brandService.getById(model.getBrandId());
		if (checkIfModelExists(model.getName(), model.getBrandId())) {
			throw new EntityAlreadyExistException(
					"Entity with following Model & Brand name already exist : " + model.getName() + " - " + brand.getName());
		}
		Model modelToUpdate = modelRepository.findById(model.getId())
				.orElseThrow(() -> new EntityNotFoundException("Model with following ID was not found : " + model.getId()));
		modelToUpdate.setName(model.getName());
		modelToUpdate.setBrand(brandService.getById(model.getBrandId()));
		return modelConverter.convetToModelDto(modelRepository.save(modelToUpdate));

	}

	@Override
	public ModelDto getDtoById(Long id) {
		return modelConverter.convetToModelDto(
				modelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Model not found")));
	}

	@Override
	public Model getByNameAndBrandId(String name, Long brandId) {
		return modelRepository.getByNameAndBrandId(name, brandId)
				.orElseThrow(() -> new EntityNotFoundException("Model not found"));
	}

	@Override
	public Model getById(Long id) {
		return modelRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Model with following ID was not found : " + id.toString()));
	}

	@Transactional
	@Override
	public Set<Model> saveAll(Set<Model> models) {
		return new HashSet<>(modelRepository.saveAll(models));

	}

	@Override
	public ModelDtoPage getAll(Pageable pageable) {
		return modelConverter.convertToModelDtoOPage(modelRepository.findAll(pageable));
	}

	private boolean checkIfModelExists(String name, Long brandId) {
		try {
			Model existingModel = getByNameAndBrandId(name, brandId);
			return existingModel.getName().equals(name) && existingModel.getBrand().getId().equals(brandId);
		} catch (EntityNotFoundException e) {
			logger.info("Model with following name : {}, and following brand id : {} not exists.", name, brandId);
			return false;
		}
	}
}
