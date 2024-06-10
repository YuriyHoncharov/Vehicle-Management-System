package ua.com.foxminded.yuriy.carrestservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarBrandSpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarCategorySpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarModelSpecification;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarSpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarYearOfProductionSpecification;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { CarRepository.class,
		CarSpecificationManager.class }))
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/clear.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
class CarRepositoryIT {

	@Autowired
	private CarRepository carRepository;

	private static final String YEAR_2017 = "2017";
	private static final String YEAR_2020 = "2020";
	private static final String INCORRECT_YEAR = "2200";
	private static final String HONDA_BRAND = "Honda";
	private static final String TOYOTA_BRAND = "Toyota";
	private static final String TOYOYA_MODEL = "Camry";
	private static final String HONDA_MODEL = "Accord";
	private static final String HONDA_CATEGORY = "Sedan";
	private static final String TOYOYA_CATEGORY = "SUV";

	@Test
	void getAllCar_shouldReturnCorrectCarsByYear_ifDataExists() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car> carSpecification = specification.getSpecification(new String[] { YEAR_2020 });
		Page<Car> carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 5));
		assertFalse(carPage.isEmpty());
		assertTrue(carPage.getContent().stream().allMatch(car -> car.getProductionYear() == Integer.valueOf(YEAR_2020)));
	}

	@Test
	void getAllCar_shouldReturnEmptyList_dataNotExist() {
		CarYearOfProductionSpecification specification = new CarYearOfProductionSpecification();
		Specification<Car> carSpecification = specification.getSpecification(new String[] { INCORRECT_YEAR });
		Page<Car> carPage = carRepository.findAll(carSpecification, PageRequest.of(0, 10));
		assertTrue(carPage.isEmpty());
	}

	@Test
	void getAllCar_shouldReturnCorrectCarsByBrand_ifDataExist() {
		CarBrandSpecification spec = new CarBrandSpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { HONDA_BRAND, TOYOTA_BRAND });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertEquals(2, carPage.getContent().size());
	}

	@Test
	void getAllCar_shouldreturnCorrectCatByModel_ifDataExist() {
		CarModelSpecification spec = new CarModelSpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { TOYOYA_MODEL, HONDA_MODEL });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertEquals(2, carPage.getContent().size());
	}

	@Test
	void getAllCar_shouldReturnCorrectCarByCategory_ifDataExist() {
		CarCategorySpecification spec = new CarCategorySpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { TOYOYA_CATEGORY, HONDA_CATEGORY });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertEquals(3, carPage.getContent().size());
	}

	@Test
	void getAllCar_shouldReturnCorrectCarByMultipleFilter_ifDataExist() {

		// YEAR
		CarYearOfProductionSpecification yearSpec = new CarYearOfProductionSpecification();
		Specification<Car> carYearSpec = yearSpec.getSpecification(new String[] { YEAR_2020 });

		// CATEGORY

		CarCategorySpecification categorySpec = new CarCategorySpecification();
		Specification<Car> carCategorySpec = categorySpec.getSpecification(new String[] { HONDA_CATEGORY });

		// BRAND

		CarBrandSpecification brandSpec = new CarBrandSpecification();
		Specification<Car> carBrandSpec = brandSpec.getSpecification(new String[] { HONDA_BRAND });

		// MODEL

		CarModelSpecification modelSpec = new CarModelSpecification();
		Specification<Car> carModelSpec = modelSpec.getSpecification(new String[] { HONDA_MODEL });

		Specification<Car> combinedSpec = Specification.where(carYearSpec).and(carCategorySpec).and(carBrandSpec)
				.and(carModelSpec);

		Page<Car> carPage = carRepository.findAll(combinedSpec, PageRequest.of(0, 10));
		Car car = carPage.getContent().get(0);
		assertEquals(1, carPage.getContent().size());
		assertEquals(Integer.parseInt(YEAR_2020), car.getProductionYear());
		assertTrue(car.getCategory().stream().anyMatch(category -> category.getName().equals(HONDA_CATEGORY)));
		assertEquals(HONDA_BRAND, car.getBrand().getName());
		assertEquals(HONDA_MODEL, car.getModel().getName());
	}

	@Test
	void getAllCar_shouldReturnCorrectCarsBetweenTwoYearsProductionFilters() {
		CarYearOfProductionSpecification carYearSpecification = new CarYearOfProductionSpecification();
		Specification<Car> specification = carYearSpecification.getSpecification(new String[] { YEAR_2017, YEAR_2020 });
		Page<Car> page = carRepository.findAll(specification, PageRequest.of(0, 10));
		assertEquals(4, page.getContent().size());
	}

	@Test
	void getAllCar_incorrectFilterName_shouldReturnEmptyPage() {
		CarModelSpecification spec = new CarModelSpecification();
		Specification<Car> carSpec = spec.getSpecification(new String[] { "taktak", "dada" });
		Page<Car> carPage = carRepository.findAll(carSpec, PageRequest.of(0, 10));
		assertEquals(0, carPage.getContent().size());
	}

}
