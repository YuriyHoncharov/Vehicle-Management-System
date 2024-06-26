package ua.com.foxminded.yuriy.carrestservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.impl.ModelServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.ModelConverter;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

	@Mock
	private ModelRepository modelRepository;
	@Mock
	private ModelConverter modelConverter;
	@Mock
	private BrandService brandService;

	@InjectMocks
	private ModelServiceImpl modelService;

	@Test
	void save_shouldSaveNewModel_ifNotExistsAlready() {
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("name");
		String notExistingModel = "notExistingModel";
		ModelPostDto modelToSave = new ModelPostDto();
		modelToSave.setBrandId(1L);
		modelToSave.setName(notExistingModel);
		when(brandService.getById(anyLong())).thenReturn(brand);
		when(modelRepository.getByNameAndBrandId(notExistingModel, 1L)).thenReturn(Optional.empty());
		ModelDto savedModel = modelService.save(modelToSave);
		verify(modelRepository, times(1)).save(any(Model.class));
	}
}
