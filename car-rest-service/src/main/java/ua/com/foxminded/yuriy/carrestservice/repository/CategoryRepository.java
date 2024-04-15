package ua.com.foxminded.yuriy.carrestservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Page<Category> findAll(Pageable pageable);
}
