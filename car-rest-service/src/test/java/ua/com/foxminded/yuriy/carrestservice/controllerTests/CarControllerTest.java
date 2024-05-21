package ua.com.foxminded.yuriy.carrestservice.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.yuriy.carrestservice.controller.CarController;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;
import ua.com.foxminded.yuriy.carrestservice.utils.FilterUtils;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

	@Mock
	private MockMvc mockMvc;

	@Mock
	private CarService carService;

	@Mock
	private SpecificationManager<Car> manager;

	@Mock
	private FilterUtils filterUtils;

	@InjectMocks
	private CarController carController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

	}

	@Test
	void getAllCars_shouldReturnStatusOk() throws Exception {
		Map<String, String> filters = new HashMap<>();
		CarDtoPage carPage = new CarDtoPage();
		when(carService.getAll(filters)).thenReturn(carPage);
		mockMvc.perform(get("/api/v1/cars", filters)).andExpect(status().isOk());
	}
}
