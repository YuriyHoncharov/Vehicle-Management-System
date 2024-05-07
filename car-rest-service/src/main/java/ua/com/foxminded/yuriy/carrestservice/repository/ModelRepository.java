package ua.com.foxminded.yuriy.carrestservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;

public interface ModelRepository extends JpaRepository<Model, Long>{
	Page<Model> findAll(Pageable pageable);
	Optional <Model> getByName(String name);
}
