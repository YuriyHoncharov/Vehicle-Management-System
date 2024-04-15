package ua.com.foxminded.yuriy.carrestservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;

public interface CarService {
	
	Long delete(Long id);
	Car save(Car car);
	Page<Car> getAll(Pageable pageable);
	Optional <Car> getById(Long id);

}
