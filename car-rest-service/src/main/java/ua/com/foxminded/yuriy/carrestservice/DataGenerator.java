package ua.com.foxminded.yuriy.carrestservice;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.CsvImportService;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvDataHandler;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvFileData;
import ua.com.foxminded.yuriy.carrestservice.utils.FilesReader;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataGenerator {

	@Autowired
	private CsvImportService csvImportService;
	@Autowired
	private FilesReader filesReader;
	@Autowired
	private CsvDataHandler csvDataHandler;	
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private CarRepository carRepository;

	@Value("${dataFile}")
	private String dataFilePath;
	
	@PostConstruct
	public void generateData() {		
		File file = new File(dataFilePath);
		List<String[]> dataFromCsv = filesReader.readCSVRecords(file);
		List<CsvFileData> data = csvDataHandler.convertToDTOs(dataFromCsv);
		csvImportService.loadToDataBase(data);
		
		
		List<Brand>brands = brandRepository.findAll();
		List<Model>models = modelRepository.findAll();
		List<Car>cars = carRepository.findAll();		
	}
}
