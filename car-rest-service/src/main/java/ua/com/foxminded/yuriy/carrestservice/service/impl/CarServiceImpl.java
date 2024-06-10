package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.ValidationException;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.utils.FilterUtils;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.CarConverter;

@Service
public class CarServiceImpl implements CarService {

	private CarRepository carRepository;
	private ModelService modelService;
	private BrandService brandService;
	private CategoryService categoryService;
	private FilterUtils filterUtils;
	private SpecificationManager<Car> specificationManager;
	private CarConverter carConverter;
	private static final String SPLIT_TO_ARRAY = ",";

	@Autowired
	public CarServiceImpl(CarRepository carRepository, ModelService modelService, BrandService brandService,
			CategoryService categoryService, FilterUtils filterUtils, SpecificationManager<Car> specificationManager,
			CarConverter carConverter) {
		this.carRepository = carRepository;
		this.modelService = modelService;
		this.brandService = brandService;
		this.categoryService = categoryService;
		this.filterUtils = filterUtils;
		this.specificationManager = specificationManager;
		this.carConverter = carConverter;
	}

	@Override
	@Transactional
	public Long delete(Long id) {
		carRepository.deleteById(id);
		return id;
	}

	@Override
	@Transactional
	public CarDto save(@Valid CarPostDto car) {

		Brand brand = brandService.getById(car.getBrandId());
		Model model = modelService.getById(car.getModelId());
		if (!checkModelAndBrandCompatibility(brand, model)) {
			throw new ValidationException("Model's actual brand don't match with selected Brand");
		}
		Car newCar = new Car();
		newCar.setObjectId(car.getObjectId());
		newCar.setBrand(brand);
		newCar.setProductionYear(car.getProductionYear());
		newCar.setModel(model);
		newCar.setCategory(car.getCategories().stream().map(categoryService::getById).collect(Collectors.toSet()));
		try {
			return carConverter.convertToDto(carRepository.save(newCar));
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyExistException("Car with following objectId already exists : " + car.getObjectId());
		}

	}

	@Override
	public CarDto getById(Long id) {
		return carConverter.convertToDto(
				carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car entity not found")));
	}

	@Override
	@Transactional
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

		Brand brand = brandService.getById(car.getBrandId());
		Model model = modelService.getById(car.getModelId());
		if (!checkModelAndBrandCompatibility(brand, model)) {
			throw new ValidationException("Model's actual brand don't match with selected Brand");
		}
		Car newCar = carRepository.findById(car.getId())
				.orElseThrow(() -> new EntityNotFoundException("Car with following ID was not found : " + car.getId()));
		newCar.setObjectId(car.getObjectId());
		newCar.setProductionYear(car.getProductionYear());
		newCar.setBrand(brand);
		newCar.setModel(model);
		newCar.setCategory(car.getCategories().stream().map(categoryService::getById).collect(Collectors.toSet()));
		try {
			return carConverter.convertToDto(carRepository.save(newCar));
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyExistException("Car with following objectId already exists : " + car.getObjectId());
		}
	}

	private boolean checkModelAndBrandCompatibility(Brand brand, Model model) {
		return model.getBrand().getName().equals(brand.getName());
	}

	@Override
	@Transactional
	public void saveAll(Set<Car> cars) {
		carRepository.saveAll(cars);
	}

	@Override
	public Car getByObjectId(String objectId) {
		return carRepository.findByObjectId(objectId);
	}

}
