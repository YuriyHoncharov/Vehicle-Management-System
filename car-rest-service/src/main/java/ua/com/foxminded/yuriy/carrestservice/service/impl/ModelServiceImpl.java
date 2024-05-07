package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

	private final ModelRepository modelRepository;

	@Override
	public Long delete(Long id) {
		modelRepository.deleteById(id);
		return id;
	}

	@Override
	public Model save(Model model) {
		return modelRepository.save(model);
	}

	@Override
	public Page<Model> getAll(Pageable pageable) {
		return modelRepository.findAll(pageable);
	}

	@Override
	public Optional<Model> getById(Long id) {
		return modelRepository.findById(id);
	}

	@Override
	public Optional<Model> getByName(String name) {
		return modelRepository.getByName(name);
	}

	@Override
	public Model save(String modelName) {
		Optional<Model>existingModel = modelRepository.getByName(modelName);
		if (!existingModel.isPresent()) {
			Model model = new Model();
			model.setName(modelName);
			return modelRepository.save(model);
		} else {
			return existingModel.get();
		}
	}
}
