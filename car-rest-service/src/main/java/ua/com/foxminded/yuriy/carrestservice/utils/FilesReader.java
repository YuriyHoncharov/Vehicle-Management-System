package ua.com.foxminded.yuriy.carrestservice.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FileReadingException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.ValidationException;

@Component
@RequiredArgsConstructor
public class FilesReader {
	
	public List<String[]> readCSVRecords(File file) {
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
	

}
