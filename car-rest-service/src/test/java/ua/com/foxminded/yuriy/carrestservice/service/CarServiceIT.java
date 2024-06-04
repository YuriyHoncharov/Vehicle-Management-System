package ua.com.foxminded.yuriy.carrestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import jakarta.transaction.Transactional;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/clear.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")

class CarServiceIT {

	@Autowired
	private CarService carService;

	private Map<String, String> filters = new HashMap<>();
	private static final String BRAND_TOYOTA = "Toyota";
	private static final String YEAR_2018 = "2018";

	@Test
	@Transactional
	void getAll_shouldApplyAllFiltersToTheSearch_ifDataExists() {
		filters.put("brand", BRAND_TOYOTA);
		filters.put("year", YEAR_2018);
		CarDtoPage page = carService.getAll(filters);
		assertTrue(page.getCars().stream().allMatch(
				car -> car.getBrand().equals(BRAND_TOYOTA) && car.getProductionYear() == Integer.valueOf(YEAR_2018)));
	}

	@Test
	void update_shouldUpdateAndReturnCarDtoObject_ifDataIsCorrect_andObjectIdIsDifferent() {
		CarPutDto car = new CarPutDto();
		car.setId(1L);
		List<Long> categories = new ArrayList<>();
		categories.add(2L);
		car.setCategories(categories);
		car.setBrandId(3L);
		car.setModelId(3L);
		car.setObjectId("11111");
		car.setProductionYear(199);
		CarDto updatedCar = carService.update(car);
		assertEquals("BMW", updatedCar.getBrand());
		assertEquals("3 Series", updatedCar.getModel());
		assertEquals(199 ,updatedCar.getProductionYear());
	}

	@Test
	void save_shouldSaveNewCar_ifObjectIdIsNotExistsYet() {
		CarPostDto carPost = new CarPostDto();
		carPost.setBrandId(1L);
		carPost.setModelId(1L);
		carPost.setObjectId("NEW OBJECTID");
		carPost.setProductionYear(2021);
		List<Long> categories = new ArrayList<>();
		categories.add(1L);
		carPost.setCategories(categories);
		CarDto carDto = carService.save(carPost);
		assertEquals(BRAND_TOYOTA, carDto.getBrand());
		assertEquals("Camry", carDto.getModel());
	}

	@Test
	void save_shouldNotSaveNewCar_ifObjectIdAlreadyExists() {
		CarPostDto carPost = new CarPostDto();
		carPost.setBrandId(1L);
		carPost.setModelId(1L);
		carPost.setObjectId("ABC123");
		carPost.setProductionYear(2021);
		List<Long> categories = new ArrayList<>();
		categories.add(1L);
		carPost.setCategories(categories);
		assertThrows(EntityAlreadyExistException.class, () -> carService.save(carPost));
	}
}
