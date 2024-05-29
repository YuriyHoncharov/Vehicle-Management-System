package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.ModelConverter;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

	private final ModelRepository modelRepository;
	private final ModelConverter modelConverter;
	private final BrandService brandService;

	@Override
	public Long delete(Long id) {
		modelRepository.deleteById(id);
		return id;
	}
	
	@Transactional
	@Override
	public ModelDto save(@Valid ModelPostDto model) {
		Brand modelBrand = brandService.getById(model.getBrandId());
		if (checkIfModelExists(model.getName(), modelBrand.getName())) {
			throw new EntityAlreadyExistException("Entity with following Model & Brand name already exist : "
					+ model.getName() + " - " + modelBrand.getName());
		}
		Model newModel = new Model();
		newModel.setName(model.getName());
		newModel.setBrand(modelBrand);
		Model savedModel = modelRepository.save(newModel);
		return modelConverter.convetToModelDto(savedModel);
	}

	private boolean checkIfModelExists(String modelName, String brandName) {
		try {
			Model existingModel = getByName(modelName);
			return existingModel != null && existingModel.getBrand().getName().equals(brandName);
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
	
	@Transactional
	@Override
	public ModelDto update(@Valid ModelPutDto model) {
		Brand brand = brandService.getById(model.getBrandId());
		if (checkIfModelExists(model.getName(), brand.getName())) {
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
	public Model getByName(String name) {
		return modelRepository.getByName(name).orElseThrow(() -> new EntityNotFoundException("Model not found"));
	}

	@Override
	public Model getById(Long id) {
		return modelRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Model with following ID was not found : " + id.toString()));
	}
	
	@Transactional
	@Override
	public Set<Model> saveAll(Set<Model> models) {
		return (modelRepository.saveAll(models)).stream().collect(Collectors.toSet());

	}

}
