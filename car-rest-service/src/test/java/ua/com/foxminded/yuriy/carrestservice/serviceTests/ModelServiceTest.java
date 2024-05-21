package ua.com.foxminded.yuriy.carrestservice.serviceTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.impl.ModelServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.ModelConverter;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {
	
	@Mock
	private ModelRepository modelRepository;
	@Mock
	private ModelConverter modelConverter;
	@Mock
	private BrandService brandService;
	
	@InjectMocks
	private ModelServiceImpl modelService;
	
	@Test
	void getByName_shouldReturnCorrectModel_ifExist() {
		String name = "name";
		Model model = new Model();
		model.setName(name);				
		when(modelRepository.getByName(name)).thenReturn(Optional.of(model));
		assertTrue(modelService.getByName(name).getName().equals("name"));
		verify(modelRepository, times(1)).getByName(name);
	}
	
	@Test
	void getByName_shouldThrowException_ifNotPresentInDb() {
		String name = "name";			
		when(modelRepository.getByName(name)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> modelService.getByName(name));
	}
	
	@Test
	void save_shouldSaveNewModel_ifNotExistsAlready() {
		String notExistingModel = "notExistingModel";
		when(modelRepository.getByName(notExistingModel)).thenReturn(Optional.empty());
		
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("name");
		Model modelToSave = new Model();
		modelToSave.setBrand(brand);
		modelToSave.setId(1L);
		modelToSave.setName(notExistingModel);
		when(modelService.save(notExistingModel, brand)).thenReturn(modelToSave);
		Model savedModel = modelService.save(notExistingModel, brand);
		assertTrue(savedModel.getBrand().equals(modelToSave.getBrand()));
		verify(modelRepository, times(1)).save(any());
		
		
	}

}
