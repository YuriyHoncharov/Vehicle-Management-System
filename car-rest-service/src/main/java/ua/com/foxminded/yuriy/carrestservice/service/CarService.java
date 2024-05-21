package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Map;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;

public interface CarService {

	Long delete(Long id);

	CarDto save(CarPostDto car);
	
	CarDto update(CarPutDto car);

	CarDto getById(Long id);

	CarDtoPage getAll(Map<String, String> filters);

}
