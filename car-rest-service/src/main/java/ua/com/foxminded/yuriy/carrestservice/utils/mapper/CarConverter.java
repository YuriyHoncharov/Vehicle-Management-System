package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;

@Component
public class CarConverter {

	public CarDto convertToDto(Car car) {
		CarDto carDto = new CarDto();
		carDto.setObjectId(car.getObjectId());
		carDto.setBrand(BasicDataDtoConverter.convertToBasicDataDto(car.getBrand()));
		carDto.setCategories(
				car.getCategory().stream().map(BasicDataDtoConverter::convertToBasicDataDto).collect(Collectors.toList()));
		carDto.setModel(BasicDataDtoConverter.convertToBasicDataDto(car.getModel()));
		carDto.setProductionYear(car.getProductionYear());
		carDto.setId(car.getId());
		return carDto;
	}

	public CarDtoPage convertToPage(Page<Car> cars) {
		CarDtoPage carPageDto = new CarDtoPage();
		carPageDto.setTotalPages(cars.getTotalPages());
		carPageDto.setTotalElements(cars.getTotalElements());
		carPageDto.setCars(cars.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
		return carPageDto;
	}
}
