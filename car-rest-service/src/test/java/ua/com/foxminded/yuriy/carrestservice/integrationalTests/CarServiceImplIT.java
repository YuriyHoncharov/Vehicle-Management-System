package ua.com.foxminded.yuriy.carrestservice.integrationalTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import jakarta.transaction.Transactional;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.CarPageDto;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;

@SpringBootTest
@Sql(scripts = { "/schema.sql", "/test-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarServiceImplIT {
	
	@Autowired
	private CarService carService;
	
	private Map<String, String>filters = new HashMap<>();
	private static final String BRAND_TOYOTA = "Toyota";
	private static final String YEAR_2018 = "2018";
		
	@Test
	@Transactional
	public void getAll_shouldApplyAllFiltersToTheSearch_ifDataExists() {
		filters.put("brand", BRAND_TOYOTA);
		filters.put("year", YEAR_2018);
		CarPageDto page = carService.getAll(filters);
		assertTrue(page.getCars().stream().allMatch(car -> car.getBrand().equals(BRAND_TOYOTA) && car.getProductionYear() == Integer.valueOf(YEAR_2018)));
		
	}

}
