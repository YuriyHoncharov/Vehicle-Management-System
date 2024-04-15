package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

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
		return brandRepository.findById(id);
	}

}
