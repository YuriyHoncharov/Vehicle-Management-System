package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;
import ua.com.foxminded.yuriy.carrestservice.exception.FileReadingException;
import ua.com.foxminded.yuriy.carrestservice.exception.ValidationException;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.service.CsvImportService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

@Service
@RequiredArgsConstructor
public class CsvImportServiceImpl implements CsvImportService {

	private final ModelService modelService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	private final CarRepository carRepository;

	@Override
	public List<CSVDataDto> getAll(File file) {
		List<String[]> records = readCSVRecords(file);
		List<CSVDataDto> data = convertToDTOs(records);
		return data;
	}

	private List<String[]> readCSVRecords(File file) {
		List<String[]> records = new ArrayList<>();
		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build()) {
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				records.add(values);
			}
			records.remove(0);
		} catch (IOException e) {
			throw new FileReadingException("Error during file reading");
		} catch (CsvValidationException e) {
			throw new ValidationException("File format is incorrect : " + e);
		}
		return records;
	}

	private List<CSVDataDto> convertToDTOs(List<String[]> records) {
		List<CSVDataDto> data = new ArrayList<>();
		for (String[] strings : records) {
			String objectId = strings[0].trim();
			String brand = strings[1].trim();
			int year = Integer.parseInt(strings[2].trim());
			String model = strings[3].trim();
			String[] categories = strings[4].split(",");

			Set<String> categoryList = new HashSet<>();
			for (String cat : categories) {
				categoryList.add(cat.trim());
			}
			data.add(new CSVDataDto(objectId, brand, year, model, categoryList));
		}
		return data;
	}

	@Override
	public void loadToDataBase(List<CSVDataDto> dataList) {
		for (CSVDataDto info : dataList) {
			Car car = new Car();
			car.setObjectId(info.getObjectId());
			car.setProductionYear(info.getYear());
			Model model;
			Brand brand;
			Set<Category> categories = new HashSet<>();
			brand = brandService.save(info.getBrand());
			model = modelService.save(info.getModel(), brand);

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
