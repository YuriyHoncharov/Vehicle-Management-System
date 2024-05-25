package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.List;
import java.util.Map;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;

public interface ModelService {

	Long delete(Long id);

	ModelDto save(ModelDto model);

	ModelDto update(ModelPutDto model);

	ModelDto getDtoById(Long id);

	Model getByName(String name);

	Model save(String modelName, Brand brand);

	Model getById(Long id);
	
	List<Model>saveAll(Map<String, String> modelBrandMap, Map<String, Brand> brandMap);

}
