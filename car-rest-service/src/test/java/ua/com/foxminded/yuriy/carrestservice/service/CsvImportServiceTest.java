package ua.com.foxminded.yuriy.carrestservice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.CategoryService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;
import ua.com.foxminded.yuriy.carrestservice.service.impl.CsvImportServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvDataHandler;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvFileData;
import ua.com.foxminded.yuriy.carrestservice.utils.FilesReader;

@ExtendWith(MockitoExtension.class)
public class CsvImportServiceTest {

	@Mock
	private ModelService modelService;
	@Mock
	private CategoryService categoryService;
	@Mock
	private BrandService brandService;
	@Mock
	private CarService carService;
	@Mock
	private FilesReader filesReader;
	@Mock
	private CsvDataHandler csvDataHandler;
	@InjectMocks
	private CsvImportServiceImpl csvService;

	@BeforeEach
	void setUp() {
		filesReader = new FilesReader();
		csvDataHandler = new CsvDataHandler();
	}

	@Test
	void getAll_shouldGetCorrectData_ifFormatIsCorrect() {

		String dataFilePath = "src/test/resources/test.csv";
		File file = new File(dataFilePath);
		List<String[]> csvFormatData = filesReader.readCSVRecords(file);
		List<CsvFileData> data = csvDataHandler.convertToDTOs(csvFormatData);
		assertTrue(data.size() == 7);
		assertTrue(data.get(0).getBrand().equals("brand1"));
		assertTrue(data.get(1).getBrand().equals("brand2"));
		assertTrue(data.get(2).getBrand().equals("brand3"));
		assertTrue(data.get(0).getCategory().getClass().equals(HashSet.class));
		assertTrue(data.get(0).getYear() == 2020);
		assertTrue(data.get(0).getModel().equals("model1"));
		assertTrue(data.get(0).getObjectId().equals("test1"));
	}

	@Test
	void loadToDataBase_shouldLoadAnyEntity_ifDataIsCorrect() {
		String dataFilePath = "src/test/resources/test.csv";
		File file = new File(dataFilePath);
		List<String[]> csvFormatData = filesReader.readCSVRecords(file);
		List<CsvFileData> data = csvDataHandler.convertToDTOs(csvFormatData);
		csvService.loadToDataBase(data);
		assertTrue(data.size() == 7);
		
	}

}
