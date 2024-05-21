package ua.com.foxminded.yuriy.carrestservice.service;

import java.io.File;
import java.util.List;

import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;

public interface CsvImportService {

	List<CSVDataDto> getAll(File file);
	void loadToDataBase(List<CSVDataDto>data);

}
