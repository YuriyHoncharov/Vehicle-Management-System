package ua.com.foxminded.yuriy.carrestservice.integrationalTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarBrandSpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarYearOfProductionSpecification;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		CarRepository.class }))
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CarRepositoryIT {

	@Autowired
	private CarRepository carRepository;

	private static final String YEAR_2020 = "2020";
	private static final String YEAR_2019 = "2019";
	private static final String INCORRECT_YEAR = "2000";
	 private static final String HONDA_BRAND = "Honda";
	 private static final String TOYOTA_BRAND = "Toyota";
	
	

	@Test
	public void getAllCar_shouldReturnCorrectCarsByYear_ifDataExists() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car>carSpecification = specification.getSpecification(new String[]{YEAR_2020});
		Page<Car>carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 5));
		assertFalse(carPage.isEmpty());
		assertTrue(carPage.getContent().stream().allMatch(car -> car.getProductionYear() == Integer.valueOf(YEAR_2020)));
	}
	
	@Test
	public void getAllCar_shouldReturnEmptyList_dataNotExist() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car>carSpecification = specification.getSpecification(new String[] {INCORRECT_YEAR});
		Page<Car>carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 10));
		assertTrue(carPage.isEmpty());
	}
	
	@Test
	public void getAllCar_shouldReturnCorrectCarsByBrand_ifDataExist() {
		CarBrandSpecification spec = new CarBrandSpecification();
		Specification<Car>carSpec = spec.getSpecification(new String[]{HONDA_BRAND, TOYOTA_BRAND});
		Page<Car>carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));		
		assertTrue(carPage.getContent().size() == 2);
	}
}
