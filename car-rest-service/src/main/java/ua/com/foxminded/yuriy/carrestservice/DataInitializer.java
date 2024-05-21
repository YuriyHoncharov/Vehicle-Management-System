package ua.com.foxminded.yuriy.carrestservice;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;
import ua.com.foxminded.yuriy.carrestservice.service.CsvImportService;

@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired
	private CsvImportService csvImportService;

	@Value("${dataFile}")
	private String dataFilePath;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		File file = new File(dataFilePath);
		List<CSVDataDto> data = csvImportService.getAll(file);
		csvImportService.loadToDataBase(data);
	}

}
