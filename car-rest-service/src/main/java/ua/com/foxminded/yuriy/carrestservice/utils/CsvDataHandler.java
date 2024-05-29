package ua.com.foxminded.yuriy.carrestservice.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class CsvDataHandler {

	public List<CsvFileData> convertToDTOs(List<String[]> records) {
		List<CsvFileData> data = new ArrayList<>();
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
			data.add(new CsvFileData(objectId, brand, year, model, categoryList));
		}
		return data;
	}
}
