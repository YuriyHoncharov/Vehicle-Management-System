package ua.com.foxminded.yuriy.carrestservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FileReadingException;

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
    List<String[]> csvFormatData = filesReader.readCSVRecords(dataFilePath);
    List<CsvFileData> data = csvDataHandler.convertToDTOs(csvFormatData);
    assertEquals(7, data.size());
    assertEquals("brand1", data.get(0).getBrand());
    assertEquals("brand2", data.get(1).getBrand());
    assertEquals("brand3", data.get(2).getBrand());
    assertEquals(HashSet.class, data.get(0).getCategory().getClass());
    assertEquals(2020, data.get(0).getYear());
    assertEquals("model1", data.get(0).getModel());
    assertEquals("test1", data.get(0).getObjectId());
  }

  @Test
  void getAll_shouldThrowException_ifFileNotFound() {
    String dataFilePath = "src/test/resources/nonexistent.csv";
    Exception exception = assertThrows(RuntimeException.class, () -> {
      List<String[]> csvFormatData = filesReader.readCSVRecords(dataFilePath);
      csvDataHandler.convertToDTOs(csvFormatData);
    });
    String expectedMessage = "Error during file reading";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getAll_shouldReturnEmptyList_ifCsvFileIsEmpty() {
    String dataFilePath = "src/test/resources/empty.csv";
    List<String[]> csvFormatData = filesReader.readCSVRecords(dataFilePath);
    List<CsvFileData> data = csvDataHandler.convertToDTOs(csvFormatData);
    assertTrue(data.isEmpty());
  }

  @Test
  void readCSVRecords_shouldThrowException_ifCsvFileIsMalformed() {
    String dataFilePath = "src/test/resources/malformed.csv";
    Exception exception = assertThrows(FileReadingException.class, () -> {
      filesReader.readCSVRecords(dataFilePath);
    });

    String expectedMessage = "Error during file reading, check if file exist or content format is correct";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

}
