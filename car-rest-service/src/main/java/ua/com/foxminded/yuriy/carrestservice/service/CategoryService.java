package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;

public interface CategoryService {
	
	Long delete(Long id);
	Category save(Category category);
	Page<Category> getAll(Pageable pageable);
	Optional <Category> getById(Long id);
	Optional <Category> getByName(String name);
	Category save(String category);

}
