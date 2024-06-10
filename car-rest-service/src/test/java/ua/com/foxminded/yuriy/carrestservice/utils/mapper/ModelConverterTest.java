package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ModelConverterTest {

	@InjectMocks
	private ModelConverter modelConverter;

	@Test
	void convertToModelDto_shouldReturnCorrectFields() {
		Model model = new Model();
		model.setId(1L);
		model.setName("modelName");
		Brand brand = new Brand();
		brand.setName("brandName");
		brand.setId(1L);
		model.setBrand(brand);
		BasicDataDto brandBasic = new BasicDataDto();
		brandBasic.setName("brandName");
		ModelDto modelDto = modelConverter.convetToModelDto(model);
		assertEquals(model.getId(), modelDto.getId());
		assertEquals(model.getName(), modelDto.getName());
		assertEquals(model.getBrand().getName(), modelDto.getBrand().getName());
	}

	@Test
	void convertToModelDtoPage_shouldReturnCorrectPage() {
		List<Model> modelList = new ArrayList<>();
		Model model = new Model();
		model.setId(1L);
		model.setName("modelName");
		Brand brand = new Brand();
		brand.setName("brandName");
		brand.setId(1L);
		model.setBrand(brand);
		for (int i = 0; i < 25; i++) {
			modelList.add(model);
		}
		Page<Model> page = new PageImpl<>(modelList, PageRequest.of(0, 10), 25);
		ModelDtoPage modelDtoPage = modelConverter.convertToModelDtoOPage(page);
		assertEquals(page.getTotalElements(), modelDtoPage.getTotalElements());
		assertEquals(page.getTotalPages(), modelDtoPage.getTotalPages());
		assertEquals(page.getContent().size(), modelDtoPage.getModels().size());
	}

	@Test
	void convertToModelBasic_shouldReturnCorrectFields() {
		Model model = new Model();
		model.setId(1L);
		model.setName("modelName");
		BasicDataDto modelBasicDto = BasicDataDtoConverter.convertToBasicDataDto(model);
		assertEquals(model.getId(), modelBasicDto.getId());
		assertEquals(model.getName(), modelBasicDto.getName());
	}
}
