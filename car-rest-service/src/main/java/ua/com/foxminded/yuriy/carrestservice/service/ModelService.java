package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Set;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;

public interface ModelService {

	Long delete(Long id);

	ModelDto save(ModelPostDto model);

	ModelDto update(ModelPutDto model);

	ModelDto getDtoById(Long id);

	Model getByNameAndBrandId(String name, Long brandId);

	Model getById(Long id);
	
	Set<Model> saveAll(Set<Model>models);

}
