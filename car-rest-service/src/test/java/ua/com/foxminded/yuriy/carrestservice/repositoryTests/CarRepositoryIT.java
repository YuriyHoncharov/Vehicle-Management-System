package ua.com.foxminded.yuriy.carrestservice.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarBrandSpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarCategorySpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarModelSpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarSpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarYearOfProductionSpecification;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		CarRepository.class, CarSpecificationManager.class }))
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CarRepositoryIT {

	@Autowired
	private CarRepository carRepository;
	@Autowired
	private CarSpecificationManager specificationManager;
		
	private static final String YEAR_2020 = "2020";
	private static final String YEAR_2019 = "2019";
	private static final String INCORRECT_YEAR = "2200";
	private static final String HONDA_BRAND = "Honda";
	private static final String TOYOTA_BRAND = "Toyota";
	private static final String TOYOYA_MODEL = "Camry";
	private static final String HONDA_MODEL = "Accord";
	private static final String HONDA_CATEGORY = "Sedan";
	private static final String TOYOYA_CATEGORY = "SUV";
	
	
	

	@Test
	public void getAllCar_shouldReturnCorrectCarsByYear_ifDataExists() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car> carSpecification = specification.getSpecification(new String[] { YEAR_2020 });
		Page<Car> carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 5));
		assertFalse(carPage.isEmpty());
		assertTrue(carPage.getContent().stream().allMatch(car -> car.getProductionYear() == Integer.valueOf(YEAR_2020)));
	}

	@Test
	public void getAllCar_shouldReturnEmptyList_dataNotExist() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car> carSpecification = specification.getSpecification(new String[] { INCORRECT_YEAR });
		Page<Car> carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 10));
		assertTrue(carPage.isEmpty());
	}

	@Test
	public void getAllCar_shouldReturnCorrectCarsByBrand_ifDataExist() {
		CarBrandSpecification spec = new CarBrandSpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { HONDA_BRAND, TOYOTA_BRAND });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertTrue(carPage.getContent().size() == 2);
	}

	@Test
	public void getAllCar_shouldreturnCorrectCatByModel_ifDataExist() {
		CarModelSpecification spec = new CarModelSpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { TOYOYA_MODEL, HONDA_MODEL });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertTrue(carPage.getContent().size() == 2);
	}

	@Test
	public void getAllCar_shouldReturnCorrectCarByCategory_ifDataExist() {
		CarCategorySpecification spec = new CarCategorySpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { TOYOYA_CATEGORY, HONDA_CATEGORY });
		Page<Car>carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertTrue(carPage.getContent().size() == 3);
	}
	
	@Test
	public void getAllCar_shouldReturnCorrectCarByMultipleFilter_ifDataExist() {
		
		// YEAR
		CarYearOfProductionSpecification yearSpec = new CarYearOfProductionSpecification();
		Specification<Car> carYearSpec = yearSpec.getSpecification(new String[] { YEAR_2020 });
		
		// CATEGORY 
		
		CarCategorySpecification categorySpec = new CarCategorySpecification();
		Specification<Car> carCategorySpec
		= categorySpec.getSpecification(new String[] { HONDA_CATEGORY });
		
		// BRAND
		
		CarBrandSpecification brandSpec = new CarBrandSpecification();
		Specification<Car> carBrandSpec = brandSpec.getSpecification(new String[] { HONDA_BRAND });
		
		// MODEL
		
		CarModelSpecification modelSpec = new CarModelSpecification();
		Specification<Car> carModelSpec = modelSpec.getSpecification(new String[] { HONDA_MODEL });
		
				
		Specification<Car>combinedSpec = Specification.where(carYearSpec).and(carCategorySpec).and(carBrandSpec).and(carModelSpec);
		
		Page<Car>carPage = carRepository.findAll(combinedSpec, PageRequest.of(0, 10));
		Car car = carPage.getContent().get(0);
		assertTrue(carPage.getContent().size() == 1);
		assertTrue(car.getProductionYear() == Integer.parseInt(YEAR_2020));
		assertTrue(car.getCategory().stream().anyMatch(category -> category.getName().equals(HONDA_CATEGORY)));
		assertTrue(car.getBrand().getName().equals(HONDA_BRAND));
		assertTrue(car.getModel().getName().equals(HONDA_MODEL));
		
	}
}
