package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CSVDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CarPageDto;

public interface CarService {
	
	Long delete(Long id);
	Car save(Car car);
	Page<Car> getAll(Pageable pageable);
	Optional <Car> getById(Long id);
	CarPageDto getAll(Map<String, String>filters);
	void uploadDataFromCSV(List<CSVDataDto>dataList);
}
