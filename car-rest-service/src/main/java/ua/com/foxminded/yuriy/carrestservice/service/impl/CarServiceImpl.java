package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CarPageDto;
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
	private final BrandService brandService;
	private final ModelService modelService;
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
	public Car save(Car car) {
		return carRepository.save(car);
	}

	@Override
	public Page<Car> getAll(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	@Override
	public Optional<Car> getById(Long id) {
		return carRepository.findById(id);
	}

	@Override
	public CarPageDto getAll(Map<String, String> filters) {
		Pageable pageReqeust = filterUtils.getPageFromFilters(filters);
		Specification<Car> specification = null;
		for (Map.Entry<String, String> entry : filters.entrySet()) {
			Specification<Car> sc = specificationManager.get(entry.getKey(), entry.getValue().split(SPLIT_TO_ARRAY));
			specification = specification == null ? Specification.where(sc) : specification.and(sc);
		}
		return carConverter.convertToPage(carRepository.findAll(specification, pageReqeust));
	}

	@Override
	public void uploadDataFromCSV(List<CSVDataDto> dataList) {
		for (CSVDataDto info : dataList) {
			Car car = new Car();
			car.setObjectId(info.getObjectId());
			car.setProductionYear(info.getYear());
			Model model;
			Brand brand;
			Set<Category> categories = null;
			// Model
			model = modelService.save(info.getModel());
			// Brand
			brand = brandService.save(info.getBrand(), model);
			// Category
			for (String cat : info.getCategory()) {
				Category category = categoryService.save(cat);
				categories.add(category);
			}
			car.setBrand(brand);
			car.setModel(model);
			car.setCategory(categories);
			carRepository.save(car);
		}

	}
}
