package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDtoPage;

@Component
public class ModelConverter {

	public ModelDto convetToModelDto(Model model) {
		ModelDto modelDto = new ModelDto();
		modelDto.setName(model.getName());
		modelDto.setBrand(BasicDataDtoConverter.convertToBasicDataDto(model.getBrand()));
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
}
