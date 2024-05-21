package ua.com.foxminded.yuriy.carrestservice.serviceTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.impl.BrandServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.BrandConverter;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock
	private BrandRepository brandRepository;

	@Mock
	private ModelRepository modelRepository;

	@Mock
	private BrandConverter brandConverter;

	@InjectMocks
	private BrandServiceImpl brandService;

	@Test
	void save_shouldSaveNewBrand_ifNotExistAlready() {
		String existingBrand = "test";
		Optional<Brand> brand = Optional.empty();
		when(brandRepository.getByName(existingBrand)).thenReturn(brand);
		brandService.save(existingBrand);
		verify(brandRepository, times(1)).save(any());
	}

	@Test
	void update_shouldUpdateModels_ifModelExists() {

		BrandPutDto brandPutDto = new BrandPutDto();
		Long brandId = 1L;
		brandPutDto.setId(brandId);

		Set<String> model = new HashSet<>();
		model.add("model");
		brandPutDto.setModels(model);

		Brand brandToUpdate = new Brand();
		Model modelToUpdate = new Model();
		when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandToUpdate));
		String newModel = "model";
		when(modelRepository.getByName(newModel)).thenReturn(Optional.of(modelToUpdate));

		brandService.update(brandPutDto);

		verify(brandRepository, times(1)).save(any(Brand.class));
	}
	
	@Test
	void getByName_shouldRetunBrand_ifExist() {
		Brand brand = new Brand();
		String brandName = "brandTest";
		when(brandRepository.getByName(brandName)).thenReturn(Optional.of(brand));
		Brand extractedBrand = brandService.getByName(brandName);
		verify(brandRepository,times(1)).getByName(brandName);
		assertTrue(!extractedBrand.equals(null));
	}

}
