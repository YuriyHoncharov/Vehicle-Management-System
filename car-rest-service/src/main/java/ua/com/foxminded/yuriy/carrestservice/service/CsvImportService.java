package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.List;
import ua.com.foxminded.yuriy.carrestservice.utils.CsvFileData;

public interface CsvImportService {

	void loadToDataBase(List<CsvFileData>data);

}
