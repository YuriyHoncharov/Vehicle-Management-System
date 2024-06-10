package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.carDto.CarDtoPage;

@ExtendWith(MockitoExtension.class)
class CarConverterTest {

	@InjectMocks
	private CarConverter carConverter;

	@Test
	void convertToDto_shouldReturnCorrectFields() {
		Car car = new Car();
		car.setObjectId("objectId");

		Brand brand = new Brand();
		brand.setName("brandName");
		brand.setId(1L);
		car.setBrand(brand);

		Model model = new Model();
		model.setName("modelName");
		model.setId(1L);
		car.setModel(model);

		car.setProductionYear(2021);
		car.setId(1L);

		Set<Category> categories = new HashSet<>();
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryName");
		categories.add(category);
		car.setCategory(categories);
		BasicDataDto modelBasic = new BasicDataDto();
		modelBasic.setName("modelName");
		BasicDataDto brandBasic = new BasicDataDto();
		brandBasic.setName("brandName");
		CarDto carDto = carConverter.convertToDto(car);
		
		

		assertEquals(car.getObjectId(), carDto.getObjectId());
		assertEquals(car.getBrand().getName(), carDto.getBrand().getName());
		assertEquals(car.getCategory().size(), carDto.getCategories().size());
		assertEquals(car.getModel().getName(), carDto.getModel().getName());
		assertEquals(car.getProductionYear(), carDto.getProductionYear());
		assertEquals(car.getId(), carDto.getId());
	}

	@Test
	void convertToPageDto_shouldReturnCorrectPage() {
		List<Car> carList = new ArrayList<>();
		Car car = new Car();
		car.setObjectId("objectId");

		Brand brand = new Brand();
		brand.setName("brandName");
		brand.setId(1L);
		car.setBrand(brand);

		Model model = new Model();
		model.setName("modelName");
		model.setId(1L);
		car.setModel(model);

		car.setProductionYear(2021);
		car.setId(1L);

		Set<Category> categories = new HashSet<>();
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryName");
		categories.add(category);
		car.setCategory(categories);

		for (int i = 0; i < 25; i++) {
			carList.add(car);
		}

		Page<Car> page = new PageImpl<>(carList, PageRequest.of(0, 10), 25);

		CarDtoPage carDtoPage = carConverter.convertToPage(page);

		assertEquals(page.getTotalElements(), carDtoPage.getTotalElements());
		assertEquals(page.getTotalPages(), carDtoPage.getTotalPages());
		assertEquals(page.getContent().size(), carDtoPage.getCars().size());
	}
}
