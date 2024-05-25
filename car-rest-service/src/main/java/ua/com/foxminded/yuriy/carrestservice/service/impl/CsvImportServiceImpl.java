package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.ArrayList;
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

		Set<String> brandNames = new HashSet<>();
		Set<String> categoryNames = new HashSet<>();
		Map<String, String> modelBrandMap = new HashMap<>();
		List<Car> cars = new ArrayList<>();

		for (CsvFileData data : dataList) {
			brandNames.add(data.getBrand());
			categoryNames.addAll(data.getCategory());
			modelBrandMap.put(data.getModel(), data.getBrand());

			Car car = new Car();
			car.setObjectId(data.getObjectId());
			car.setBrand(new Brand(data.getBrand()));
			car.setModel(new Model(data.getModel()));
			car.setProductionYear(data.getYear());
			cars.add(car);
		}

		List<Brand> brands = brandService.saveAll(new ArrayList<>(brandNames));
		Map<String, Brand> brandMap = brands.stream().collect(Collectors.toMap(Brand::getName, Function.identity()));

		List<Model> models = modelService.saveAll(modelBrandMap, brandMap);
		Map<String, Model> modelMap = models.stream().collect(Collectors.toMap(Model::getName, Function.identity()));

		List<Category> categories = categoryService.saveAll(new ArrayList<>(categoryNames));
		Map<String, Category> categoryMap = categories.stream()
				.collect(Collectors.toMap(Category::getName, Function.identity()));

		for (Car car : cars) {

			String brandName = dataList.stream().filter(data -> data.getObjectId().equals(car.getObjectId()))
					.map(CsvFileData::getBrand).findFirst().orElse(null);

			String modelName = dataList.stream().filter(data -> data.getObjectId().equals(car.getObjectId()))
					.map(CsvFileData::getModel).findFirst().orElse(null);

			car.setBrand(brandMap.get(brandName));
			car.setModel(modelMap.get(modelName));

			Set<Category> carCategories = new HashSet<>();
			for (String categoryName : dataList.stream().filter(data -> data.getObjectId().equals(car.getObjectId()))
					.flatMap(data -> data.getCategory().stream()).collect(Collectors.toSet())) {
				carCategories.add(categoryMap.get(categoryNames));
			}
			car.setCategory(carCategories);
		}
		carService.saveAll(cars);
	}

}
