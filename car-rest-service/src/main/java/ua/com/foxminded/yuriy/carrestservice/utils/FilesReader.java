package ua.com.foxminded.yuriy.carrestservice.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FileReadingException;

@Component
@RequiredArgsConstructor
public class FilesReader {

	public List<String[]> readCSVRecords(String filePath) {
		File file = getFile(filePath);
		List<String[]> records = new ArrayList<>();
		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build()) {
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				records.add(values);
			}
			records.remove(0);
		} catch (Exception e) {
			throw new FileReadingException("Error during file reading, check if file exist or content format is correct.");
		}
		return records;
	}

	public File getFile(String filePath) {
		return new File(filePath);
	}
}
