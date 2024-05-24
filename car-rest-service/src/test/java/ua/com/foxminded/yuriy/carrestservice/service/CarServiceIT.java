package ua.com.foxminded.yuriy.carrestservice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import jakarta.transaction.Transactional;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.repository.CarRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.car.CarSpecificationManager;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/clear.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class CarServiceIT {
	
	@Autowired
	private CarService carService;
		
	private Map<String, String>filters = new HashMap<>();
	private static final String BRAND_TOYOTA = "Toyota";
	private static final String YEAR_2018 = "2018";
		
	@Test
	@Transactional
	void getAll_shouldApplyAllFiltersToTheSearch_ifDataExists() {
		filters.put("brand", BRAND_TOYOTA);
		filters.put("year", YEAR_2018);
		CarDtoPage page = carService.getAll(filters);
		assertTrue(page.getCars().stream().allMatch(car -> car.getBrand().equals(BRAND_TOYOTA) && car.getProductionYear() == Integer.valueOf(YEAR_2018)));		
	}
	
	@Test
	void update_shouldUpdateAndReturnCarDtoObject_ifDataIsCorrect() {
		CarPutDto car = new CarPutDto();
		car.setId(1L);
		Set<Long>categories = new HashSet<>();
		categories.add(2L);
		car.setCategories(categories);
		car.setBrandId(2L);
		car.setModelId(3L);
		car.setObjectId("11111");
		car.setProductionYear(199);		
		CarDto updatedCar = carService.update(car);
		assertTrue(updatedCar.getBrand().equals("Honda"));
		assertTrue(updatedCar.getModel().equals("3 Series"));		
		assertTrue(updatedCar.getProductionYear() == 199);		
		assertTrue(carService.update(car).getClass() == CarDto.class);
		
	} 
	

}
