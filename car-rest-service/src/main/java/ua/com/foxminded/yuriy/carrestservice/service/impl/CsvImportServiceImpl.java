package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
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
		brands = new HashSet<>(brandService.saveAll(brands));
		Map<String, Brand> savedBrands = brands.stream().collect(Collectors.toMap(Brand::getName, Function.identity()));
		models = models.stream().map(model -> {
			model.setBrand(savedBrands.get(model.getBrand().getName()));
			return model;
		}).collect(Collectors.toSet());
		models = new HashSet<>(modelService.saveAll(models));
		Map<String, Model> savedModels = models.stream().collect(
				Collectors.toMap(model -> model.getBrand().getName() + "-" + model.getName(), Function.identity()));
		categories = new HashSet<>(categoryService.saveAll(categories));
		Map<String, Category> savedCategories = categories.stream()
				.collect(Collectors.toMap(Category::getName, Function.identity()));
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
}
