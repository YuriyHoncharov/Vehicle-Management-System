package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;

public interface ModelService {

	Long delete(Long id);

	Model save(Model model);

	Page<Model> getAll(Pageable pageable);

	Optional <Model> getById(Long id);

}
