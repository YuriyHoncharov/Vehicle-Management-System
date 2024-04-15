package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

	private final CarRepository carRepository;

	@Override
	public Long delete(Long id) {
		carRepository.deleteById(id);
		return id;
	}

	@Override
	public Car save(Car car) {
		return carRepository.save(car);
	}

	@Override
	public Page<Car> getAll(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	@Override
	public Optional<Car> getById(Long id) {
		return carRepository.findById(id);
	}

}
