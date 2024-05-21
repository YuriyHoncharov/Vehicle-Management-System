package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;

@Component
public class CarConverter {

	public CarDto convertToDto(Car car) {
		CarDto carDto = new CarDto();
		carDto.setBrand(car.getBrand().getName());
		carDto.setCategories(car.getCategory().stream().map(Category::getName).collect(Collectors.toSet()));
		carDto.setModel(car.getModel().getName());
		carDto.setProductionYear(car.getProductionYear());
		return carDto;
	}
	
	public CarDtoPage convertToPage(Page<Car>cars) {
		CarDtoPage carPageDto = new CarDtoPage();
		carPageDto.setTotalPages(cars.getTotalPages());
		carPageDto.setTotalElements(cars.getTotalElements());
		carPageDto.setCars(cars.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
		return carPageDto;
	}

}
