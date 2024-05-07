package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

	private final BrandRepository brandRepository;

	@Override
	public Long delete(Long id) {
		brandRepository.deleteById(id);
		return id;
	}

	@Override
	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public Page<Brand> getAll(Pageable pageable) {
		return brandRepository.findAll(pageable);
	}

	@Override
	public Optional<Brand> getById(Long id) {
		return Optional
				.of(brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity non found")));
	}

	@Override
	public Optional<Brand> getByName(String name) {
		return Optional
				.of(brandRepository.getByName(name).orElseThrow(() -> new EntityNotFoundException("Entity non found")));
	}

	@Override
	public Brand save(String brandName, Model model) {
		
		Optional<Brand> existingBrand = brandRepository.getByName(brandName);
		Brand brand = new Brand();
		
		if (!existingBrand.isPresent()) {
			brand = new Brand();
			brand.setName(brandName);
			brand.setModels(new HashSet<>());
		} else {
			brand = existingBrand.get();
		}

		brand.getModels().add(model);
		return brandRepository.save(brand);

	}
}
