package ua.com.foxminded.yuriy.carrestservice;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.service.CsvImportService;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvDataHandler;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvFileData;
import ua.com.foxminded.yuriy.carrestservice.utils.FilesReader;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataGenerator {

	private CsvImportService csvImportService;
	private FilesReader filesReader;
	private CsvDataHandler csvDataHandler;

	@Autowired
	public DataGenerator(CsvImportService csvImportService, FilesReader filesReader, CsvDataHandler csvDataHandler) {
		this.csvImportService = csvImportService;
		this.filesReader = filesReader;
		this.csvDataHandler = csvDataHandler;
	}

	@Value("${dataFile}")
	private String dataFilePath;

	@PostConstruct
	public void generateData() {
		File file = new File(dataFilePath);
		List<String[]> dataFromCsv = filesReader.readCSVRecords(file);
		List<CsvFileData> data = csvDataHandler.convertToDTOs(dataFromCsv);
		csvImportService.loadToDataBase(data);
	}

}
