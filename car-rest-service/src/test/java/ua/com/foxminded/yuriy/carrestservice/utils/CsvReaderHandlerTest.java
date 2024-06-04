package ua.com.foxminded.yuriy.carrestservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsvReaderHandlerTest {

	@Mock
	private FilesReader filesReader;
	@Mock
	private CsvDataHandler csvDataHandler;
	
	@BeforeEach
	void setUp() {
		filesReader = new FilesReader();
		csvDataHandler = new CsvDataHandler();
	}
	
	// I know that i should separate these two classes and test them one per one.
	
	@Test
	void getAll_shouldGetCorrectData_ifFormatIsCorrect() {
		String dataFilePath = "src/test/resources/test.csv";
		File file = new File(dataFilePath);
		List<String[]> csvFormatData = filesReader.readCSVRecords(file);
		List<CsvFileData> data = csvDataHandler.convertToDTOs(csvFormatData);
		assertEquals(7, data.size());
		assertEquals("brand1",data.get(0).getBrand());
		assertEquals("brand2", data.get(1).getBrand());
		assertEquals("brand3", data.get(2).getBrand());
		assertEquals(HashSet.class, data.get(0).getCategory().getClass());
		assertEquals(2020, data.get(0).getYear());
		assertEquals("model1", data.get(0).getModel());
		assertEquals("test1", data.get(0).getObjectId());
	}	
}
