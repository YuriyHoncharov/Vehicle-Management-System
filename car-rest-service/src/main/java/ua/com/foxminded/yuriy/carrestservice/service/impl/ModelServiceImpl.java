package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
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
	private final BrandRepository brandRepository;

	@Override
	public Long delete(Long id) {
		modelRepository.deleteById(id);
		return id;
	}

	@Override
	public ModelDto save(@Valid ModelDto model) {
		Model newModel = new Model();
		newModel.setName(model.getName());
		newModel.setBrand(brandService.getByName(model.getBrand()));
		return modelConverter.convetToModelDto(modelRepository.save(newModel));
	}

	@Override
	public ModelDto update(@Valid ModelPutDto model) {
		Model modelToUpdate = modelRepository.findById(model.getId())
				.orElseThrow(() -> new EntityNotFoundException("Model with following ID was not found : " + model.getId()));
		modelToUpdate.setName(model.getName());
		modelToUpdate.setBrand(brandRepository.findById(model.getBrandId()).orElseThrow(
				() -> new EntityNotFoundException("Brand with following ID not exists : " + model.getBrandId())));
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
	public Model save(String modelName, Brand brand) {
		Optional<Model> existingModel = modelRepository.getByName(modelName);
		if (!existingModel.isPresent()) {
			Model model = new Model();
			model.setName(modelName);
			model.setBrand(brand);
			return modelRepository.save(model);
		} else {
			existingModel.get().setBrand(brand);
			return modelRepository.save(existingModel.get());
		}
	}

	@Override
	public Model getById(Long id) {
		return modelRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Model with following ID was not found : " + id.toString()));
	}

	@Override
	public void saveAll(List<Model> models) {
		modelRepository.saveAll(models);
	}

}
