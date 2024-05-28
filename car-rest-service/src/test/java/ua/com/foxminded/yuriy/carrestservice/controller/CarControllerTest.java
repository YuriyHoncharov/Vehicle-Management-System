package ua.com.foxminded.yuriy.carrestservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarPutDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryBasicDto;
import ua.com.foxminded.yuriy.carrestservice.service.CarService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

	@Mock
	private MockMvc mockMvc;

	@Mock
	private CarService carService;

	@InjectMocks
	private CarController carController;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void getAllCars_shouldReturnStatusOk() throws Exception {
		Map<String, String> filters = new HashMap<>();
		CarDtoPage carPage = new CarDtoPage();
		when(carService.getAll(filters)).thenReturn(carPage);
		mockMvc.perform(get("/api/v1/cars")).andExpect(status().isOk());
	}

	@Test
	void save_shouldSaveNewCar() throws Exception {

		CarPostDto carPostDto = new CarPostDto();
		carPostDto.setBrandId(1L);
		Set<Long> categories = new HashSet<>();
		categories.add(1L);
		carPostDto.setModelId(1L);
		carPostDto.setObjectId("111");
		carPostDto.setProductionYear(1);
		carPostDto.setCategories(categories);
		mockMvc.perform(post("/api/v1/cars").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(carPostDto))).andExpect(status().isCreated());
	}

	@Test
	void update_shouldUpdateCarInfo() throws Exception {
		CarPutDto carPut = new CarPutDto();
		carPut.setBrandId(1L);
		carPut.setId(1L);
		Set<Long> categories = new HashSet<>();
		categories.add(1L);
		carPut.setCategories(categories);
		carPut.setModelId(1L);
		carPut.setObjectId("11");
		carPut.setProductionYear(2020);
		mockMvc.perform(put("/api/v1/cars").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(carPut))).andExpect(status().isOk());
	}

	@Test
	void delete_shouldDeleteCarSuccessfully() throws Exception {
		Long id = 1L;
		mockMvc.perform(delete("/api/v1/cars/{id}", id)).andExpect(status().isOk());
	}

	@Test
	void get_shouldReturnCarById() throws Exception {
		Long id = 1L;
		CarDto carDto = new CarDto();
		Set<CategoryBasicDto> categories = new HashSet<>();
		CategoryBasicDto catDto = new CategoryBasicDto();
		catDto.setId(1L);
		catDto.setName("nameDto");
		categories.add(catDto);
		carDto.setBrand("brand");
		carDto.setId(1L);
		carDto.setModel("model");
		carDto.setProductionYear(1);
		carDto.setCategories(categories);
		when(carService.getById(id)).thenReturn(carDto);
		mockMvc.perform(get("/api/v1/cars/{id}", id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(carDto.getId())).andExpect(jsonPath("$.brand").value(carDto.getBrand()));
	}
}