package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;

public interface BrandService {

	Long delete(Long id);

	Brand save(Brand brand);

	Page<Brand> getAll(Pageable pageable);

	Optional<Brand> getById(Long id);
}