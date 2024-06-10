package ua.com.foxminded.yuriy.carrestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.impl.BrandServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.BrandConverter;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

	@Mock
	private BrandRepository brandRepository;

	@Mock
	private ModelRepository modelRepository;

	@Mock
	private BrandConverter brandConverter;

	@InjectMocks
	private BrandServiceImpl brandService;

	@Test
	void update_shouldUpdateBrandName_ifBrandNameIsNotExists() {
		BrandPutDto brandPutDto = new BrandPutDto();
		Long brandId = 1L;
		brandPutDto.setId(brandId);
		brandPutDto.setName("testBrand");
		when(brandRepository.findByName(brandPutDto.getName())).thenReturn(Optional.empty());
		Brand brandToUpdate = new Brand();
		brandToUpdate.setName("newBrandName");
		List<Long> models = new ArrayList<>();
		models.add(1L);
		brandPutDto.setModels(models);
		Model model = new Model("MODEL");
		model.setId(1L);
		when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandToUpdate));
		when(modelRepository.findById(1L)).thenReturn(Optional.of(model));
		brandService.update(brandPutDto);
		verify(brandRepository, times(1)).save(any(Brand.class));
	}

	@Test
	void getByName_shouldRetunBrand_ifExist() {
		Brand brand = new Brand();
		String brandName = "brandTest";
		when(brandRepository.getByName(brandName)).thenReturn(Optional.of(brand));
		Brand extractedBrand = brandService.getByName(brandName);
		verify(brandRepository, times(1)).getByName(brandName);
		assertNotNull(extractedBrand);
	}

	@Test
	void save_shouldSaveBrand_ifNotAlreadyExists() {
		BrandPostDto brandPostDto = new BrandPostDto();
		brandPostDto.setName("newBrand");
		List<Long> models = new ArrayList<>();
		models.add(1L);
		brandPostDto.setModels(models);
		Model model = new Model("MODEL");
		model.setId(1L);
		when(brandRepository.getByName("newBrand")).thenReturn(Optional.empty());
		when(modelRepository.findById(1L)).thenReturn(Optional.of(model));
		brandService.save(brandPostDto);
		verify(brandRepository, times(1)).save(any(Brand.class));
	}

	@Test
	void save_shouldNotSaveBrand_ifAlreadyExist() {
		BrandPostDto brandPostDto = new BrandPostDto();
		brandPostDto.setName("newBrand");
		Brand brand = new Brand();
		when(brandRepository.getByName("newBrand")).thenReturn(Optional.of(brand));
		assertThrows(EntityAlreadyExistException.class, () -> brandService.save(brandPostDto));
	}

	@Test
	void getDtoById_shouldReturnDtoClass_byId() {
		Brand brand = new Brand();
		brand.setId(33L);
		brand.setName("name");
		BrandDto brandDto = new BrandDto();
		brandDto.setId(33L);
		brandDto.setName("name");
		when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
		when(brandConverter.convertToDto(brand)).thenReturn(brandDto);
		BrandDto actualBrand = brandService.getDtoById(1L);
		assertEquals(actualBrand, brandDto);

	}

}
