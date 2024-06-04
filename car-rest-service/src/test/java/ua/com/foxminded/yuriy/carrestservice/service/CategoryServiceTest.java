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
import ua.com.foxminded.yuriy.carrestservice.entities.dto.categoryDto.CategoryPostDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.repository.CategoryRepository;
import ua.com.foxminded.yuriy.carrestservice.service.impl.CategoryServiceImpl;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.CategoryConverter;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private CategoryConverter categoryConverter;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	
	@Test
	void save_shouldSaveNewCategory_ifNotAlreadyExists() {
		String categoryName = "anyName";
		when(categoryRepository.getByName(categoryName)).thenReturn(Optional.empty());
		CategoryPostDto newCategory = new CategoryPostDto();
		newCategory.setName(categoryName);
		categoryService.save(newCategory);
		verify(categoryRepository, times(1)).save(any());
	}
	
	@Test
	void getByName_shouldThrowException_ifNotExist() {
		String nonExistingCategory = "NotExist";
		when(categoryRepository.getByName(nonExistingCategory)).thenReturn(Optional.empty());		
		assertThrows(EntityNotFoundException.class, () -> categoryService.getByName(nonExistingCategory));
	}
}
