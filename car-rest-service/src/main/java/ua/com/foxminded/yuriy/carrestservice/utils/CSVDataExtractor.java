package ua.com.foxminded.yuriy.carrestservice.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;
import ua.com.foxminded.yuriy.carrestservice.exception.FileReadingException;


@Component
public class CSVDataExtractor {
	

	public List<CSVDataDto> uploadFromCSV(File file) {
		List<String[]> records = new ArrayList<>();
		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build()) {
			String[] values = null;
			while ((values = csvReader.readNext()) != null) {
				records.add(values);
			}
			records.remove(0);
			List<CSVDataDto> data = new ArrayList<>();
			for (String[] strings : records) {

				String objectId = strings[0];
				String brand = strings[1];
				int year = Integer.parseInt(strings[4]);
				String model = strings[3];
				String[] categories = strings[4].split(",");
				Set<String> categoryList = null;
				for (String cat : categories) {
					categoryList.add(cat);
				}

				data.add(new CSVDataDto(objectId, brand, year, model, categoryList));
			}
			return data;
		} catch (FileReadingException e) {
			throw new FileReadingException("File was not found");
		} catch (IOException e) {
			throw new FileReadingException("Error during file reading");
		} catch (CsvValidationException e) {
			throw new FileReadingException("Invalid row format");
		}
	}
}
