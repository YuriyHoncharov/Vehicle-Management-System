package ua.com.foxminded.yuriy.carrestservice.service;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;

public interface ModelService {

	Long delete(Long id);

	ModelDto save(ModelDto model);
	
	ModelDto update (ModelPutDto model);

	ModelDto getById(Long id);

	Model getByName(String name);

	Model save(String modelName, Brand brand);

}
