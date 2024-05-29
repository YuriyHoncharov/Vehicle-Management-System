package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.utils.FilterUtils;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.CarConverter;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

	private final CarRepository carRepository;
	private final ModelService modelService;
	private final BrandService brandService;
	private final CategoryService categoryService;
	private final FilterUtils filterUtils;
	private final SpecificationManager<Car> specificationManager;
	private final CarConverter carConverter;
	private static final String SPLIT_TO_ARRAY = ",";

	@Override
	public Long delete(Long id) {
		carRepository.deleteById(id);
		return id;
	}

	@Override
	public CarDto save(@Valid CarPostDto car) {
		Car newCar = new Car();
		newCar.setObjectId(car.getObjectId());
		newCar.setBrand(brandService.getById(car.getBrandId()));
		newCar.setProductionYear(car.getProductionYear());
		newCar.setModel(modelService.getById(car.getModelId()));
		newCar.setCategory(car.getCategories().stream().map(categoryService::getById).collect(Collectors.toSet()));
		return carConverter.convertToDto(carRepository.save(newCar));
	}

	@Override
	public CarDto getById(Long id) {
		return carConverter.convertToDto(
				carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car entity not found")));
	}

	@Override
	public CarDtoPage getAll(Map<String, String> filters) {
		Pageable pageReqeust = filterUtils.getPageFromFilters(filters);
		Specification<Car> specification = null;
		for (Map.Entry<String, String> entry : filters.entrySet()) {
			Specification<Car> sc = specificationManager.get(entry.getKey(), entry.getValue().split(SPLIT_TO_ARRAY));
			specification = specification == null ? Specification.where(sc) : specification.and(sc);
		}
		return carConverter.convertToPage(carRepository.findAll(specification, pageReqeust));
	}

	@Override
	@Transactional
	public CarDto update(@Valid CarPutDto car) {
		Car newCar = carRepository.findById(car.getId())
				.orElseThrow(() -> new EntityNotFoundException("Car with following ID was not found : " + car.getId()));
		newCar.setObjectId(car.getObjectId());
		newCar.setProductionYear(car.getProductionYear());
		newCar.setBrand(brandService.getById(car.getBrandId()));
		newCar.setModel(modelService.getById(car.getModelId()));
		newCar.setCategory(car.getCategories().stream().map(categoryService::getById).collect(Collectors.toSet()));
		return carConverter.convertToDto(carRepository.save(newCar));
	}

	@Override
	public void saveAll(Set<Car> cars) {
		carRepository.saveAll(cars);

	}

	@Override
	public Car save(Car car) {
		return carRepository.save(car);
	}
}
