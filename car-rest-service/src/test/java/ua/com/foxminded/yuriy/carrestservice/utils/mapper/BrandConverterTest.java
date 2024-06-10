package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Repeat;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BrandConverterTest {

	@Mock
	private ModelConverter modelConverter;

	@InjectMocks
	private BrandConverter brandConverter;

	@Test
    void convertToDto_shouldReturnCorrectFields() {
   	 Brand brand = new Brand();
   	 brand.setId(1L);
   	 brand.setName("name");
   	 Set<Model>models = new HashSet<>();
   	 Model model = new Model();
   	 model.setId(1L);
   	 model.setName("model");
   	 model.setBrand(brand);
   	 models.add(model);
   	 brand.setModels(models);
   	 BrandDto brandDto = brandConverter.convertToDto(brand);
   	 assertEquals(brand.getId(), brandDto.getId());
   	 assertEquals(brand.getName(), brandDto.getName());
   	 assertEquals(brand.getModels().size(), brandDto.getModels().size());
    
   	    	 
    }
	
	@Test
	 void convertToPageDto_shouldReturnCorrectPage(){
		 List<Brand>brandList = new ArrayList<>();
		 Brand brand = new Brand();
   	 brand.setId(1L);
   	 brand.setName("name");   	 
   	 Set<Model>models = new HashSet<>();
   	 Model model = new Model();
   	 model.setId(1L);
   	 model.setName("model");
   	 model.setBrand(brand);
		 models.add(model);
		 brand.setModels(models);
   	 for(int i = 0; i < 25; i++) {
   		 brandList.add(brand);
   	 }
   	 Page<Brand>page = new PageImpl<>(brandList, PageRequest.of(0, 10), 25);   	 
   	 BrandDtoPage brandDtoPage = brandConverter.convertToPageDto(page);
   	 assertEquals(page.getContent().size(), brandDtoPage.getTotalElements());
   	 assertEquals(page.getTotalPages(), brandDtoPage.getTotalPages());
	 }
}