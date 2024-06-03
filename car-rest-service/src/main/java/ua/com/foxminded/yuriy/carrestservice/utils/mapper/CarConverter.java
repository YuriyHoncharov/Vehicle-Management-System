package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;

@Component
@RequiredArgsConstructor
public class CarConverter {

	private final CategoryConverter categoryConverter;

	public CarDto convertToDto(Car car) {
		CarDto carDto = new CarDto();
		carDto.setBrand(car.getBrand().getName());
		carDto.setCategories(
				car.getCategory().stream().map(categoryConverter::convertToBasic).collect(Collectors.toList()));
		carDto.setModel(car.getModel().getName());
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
