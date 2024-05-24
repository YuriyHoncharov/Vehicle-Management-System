package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

	@Override
	public void loadToDataBase(List<CsvFileData> dataList) {

		Set<Car> allCars = new HashSet<>();
		Set<Model> allModels = new HashSet<>();
		Set<Brand> allBrands = new HashSet<>();
		Set<Category> allCategories = new HashSet<>();

		for (CsvFileData info : dataList) {

			Brand brand = new Brand();
			brand.setName(info.getBrand());
			allBrands.add(brand);
			Model model = new Model();
			model.setName(info.getModel());
			model.setBrand(brand);
			allModels.add(model);

			Set<Category> categories = new HashSet<>();
			for (String cat : info.getCategory()) {
				Category category = new Category();
				category.setName(cat);
				allCategories.add(category);

				categories.add(category);

				Car car = new Car();
				car.setObjectId(info.getObjectId());
				car.setProductionYear(info.getYear());
				car.setBrand(brand);
				car.setModel(model);
				car.setCategory(categories);
				allCars.add(car);
				categories.clear();
			}
		}
		categoryService.saveAll(allCategories.stream().toList());
		brandService.saveAll(allBrands.stream().toList());
		modelService.saveAll(allModels.stream().toList());
		carService.saveAll(allCars.stream().toList());
	}
}
