package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelBasicDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;

@Component
@AllArgsConstructor
public class ModelConverter {

	public ModelDto convetToModelDto(Model model) {
		ModelDto modelDto = new ModelDto();
		modelDto.setName(model.getName());
		modelDto.setBrand(model.getBrand().getName());
		modelDto.setId(model.getId());
		return modelDto;
	}

	public ModelDtoPage convertToModelDtoOPage(Page<Model> models) {
		ModelDtoPage modelDtoPage = new ModelDtoPage();
		modelDtoPage.setTotalElements(models.getTotalElements());
		modelDtoPage.setTotalPages(models.getTotalPages());
		modelDtoPage.setModels(models.getContent().stream().map(this::convetToModelDto).collect(Collectors.toList()));
		return modelDtoPage;
	}
	
	public ModelBasicDto convertToBasic(Model model) {
		ModelBasicDto modelBasicDto = new ModelBasicDto();
		modelBasicDto.setId(model.getId());
		modelBasicDto.setName(model.getName());
		return modelBasicDto;
	}

}
