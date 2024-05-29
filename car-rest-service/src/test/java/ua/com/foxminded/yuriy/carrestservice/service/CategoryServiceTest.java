package ua.com.foxminded.yuriy.carrestservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.exception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.CategoryRepository;
import ua.com.foxminded.yuriy.carrestservice.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	
	@Test
	void save_shouldSaveNewCategory_ifNotAlreadyExists() {
		String categoryName = "anyName";
		when(categoryRepository.getByName(categoryName)).thenReturn(Optional.empty());
		categoryService.save(categoryName);
		verify(categoryRepository, times(1)).save(any());
	}
	
	@Test
	void save_shouldNotSaveNewCategory_ifAlreadyExists() {
		String categoryName = "anyName";
		Category category = new Category();
		category.setName(categoryName);
		when(categoryRepository.getByName(categoryName)).thenReturn(Optional.of(category));
		categoryService.save(categoryName);
		verify(categoryRepository, times(0)).save(any());
	}
	
	@Test
	void getByName_shouldThrowException_ifNotExist() {
		String nonExistingCategory = "NotExist";
		when(categoryRepository.getByName(nonExistingCategory)).thenReturn(Optional.empty());		
		assertThrows(EntityNotFoundException.class, () -> categoryService.getByName(nonExistingCategory));
	}
}
