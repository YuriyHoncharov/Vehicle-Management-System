package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.service.CsvImportService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvFileData;

@Service
@RequiredArgsConstructor
public class CsvImportServiceImpl implements CsvImportService {

	private final ModelService modelService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	private final CarService carService;
	
	@Transactional
	public void loadToDataBase(List<CsvFileData> dataList) {

		Set<Brand> brands = new HashSet<>();
		Set<Model> models = new HashSet<>();
		Set<Category> categories = new HashSet<>();
		Set<Car> cars = new HashSet<>();
		for (CsvFileData row : dataList) {
			Brand brand = new Brand(row.getBrand());
			brands.add(brand);
			Model model = new Model(row.getModel(), brand);
			models.add(model);
			Set<Category> carCategories = row.getCategory().stream().map(Category::new).collect(Collectors.toSet());
			categories.addAll(carCategories);
			Car car = new Car();
			car.setBrand(brand);
			car.setCategory(carCategories);
			car.setModel(model);
			car.setObjectId(row.getObjectId());
			car.setProductionYear(row.getYear());
			cars.add(car);
		}

		Map<String, Brand> savedBrands = getSavedBrands(brands);
		models = models.stream().map(model -> {
			model.setBrand(savedBrands.get(model.getBrand().getName()));
			return model;
		}).collect(Collectors.toSet());
		Map<String, Model> savedModels = getSavedModels(models);
		Map<String, Category> savedCategories = getSavedCategories(categories);

		cars = cars.stream().map(car -> {
			car.setBrand(savedBrands.get(car.getBrand().getName()));
			car.setModel(savedModels.get(car.getBrand().getName() + "-" + car.getModel().getName()));
			Set<Category> updatedCategories = car.getCategory().stream()
					.map(category -> savedCategories.get(category.getName())).collect(Collectors.toSet());
			car.setCategory(updatedCategories);
			return car;
		}).collect(Collectors.toSet());
		carService.saveAll(cars);
	}

	private Map<String, Brand> getSavedBrands(Set<Brand> brands) {
		brands = new HashSet<>(brandService.saveAll(brands));
		return brands.stream().collect(Collectors.toMap(Brand::getName, Function.identity()));
	}

	private Map<String, Model> getSavedModels(Set<Model> models) {
		models = new HashSet<>(modelService.saveAll(models));
		return models.stream().collect(
				Collectors.toMap(model -> model.getBrand().getName() + "-" + model.getName(), Function.identity()));
	}

	private Map<String, Category> getSavedCategories(Set<Category> categories) {
		categories = new HashSet<>(categoryService.saveAll(categories));
		return categories.stream().collect(Collectors.toMap(Category::getName, Function.identity()));
	}
}
