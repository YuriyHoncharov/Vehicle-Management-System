package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ModelServiceImpl implements ModelService {

	private ModelRepository modelRepository;
	private ModelConverter modelConverter;
	private BrandService brandService;

	public ModelServiceImpl(ModelRepository modelRepository, ModelConverter modelConverter, BrandService brandService) {
		this.modelRepository = modelRepository;
		this.modelConverter = modelConverter;
		this.brandService = brandService;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		modelRepository.deleteById(id);
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
		log.info("Calling getAll() with following pagealbe param : page - {}, sort - {}, size - {}  ",
				pageable.getPageNumber(), pageable.getSort(), pageable.getPageSize());
		try {
			ModelDtoPage result = modelConverter.convertToModelDtoOPage(modelRepository.findAll(pageable));
			log.info("Successfully fetched and converted data.");
			return result;
		} catch (Exception e) {
			log.error("Error occurred while fetching data: ", e);
			throw e;
		}
	}

	private boolean checkIfModelExists(String name, Long brandId) {
		log.trace("Entering checkIfModelExists() method with parameters: name - {}, brandId - {}", name, brandId);
		boolean exists = modelRepository.getByNameAndBrandId(name, brandId).isPresent();
		if (exists) {
			log.debug("Model exists for name: {}, brandId: {}", name, brandId);
		} else {
			log.info("Model with name: {}, and brandId: {} does not exist.", name, brandId);
		}
		return exists;
	}
}
