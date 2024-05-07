package ua.com.foxminded.yuriy.carrestservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	Page<Brand> findAll(Pageable pageable);
	Optional<Brand>getByName (String name);
}
