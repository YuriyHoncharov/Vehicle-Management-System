package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;

public interface ModelService {

	void delete(Long id);

	ModelDto save(ModelPostDto model);

	ModelDto update(ModelPutDto model);

	ModelDto getDtoById(Long id);

	Model getById(Long id);
	
	Set<Model> saveAll(Set<Model>models);
	
	ModelDtoPage getAll(Pageable pageable);

}
