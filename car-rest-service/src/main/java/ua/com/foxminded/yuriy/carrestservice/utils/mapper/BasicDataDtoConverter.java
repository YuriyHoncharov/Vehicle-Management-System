package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoId;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoName;

@Slf4j
public class BasicDataDtoConverter {

	public static BasicDataDto convertToBasicDataDto(Object object) {

		if (object == null) {
			log.error("Cannot convert null object to BasicDataDto");
			return null;
		}
		BasicDataDto basicData = new BasicDataDto();

		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(DtoId.class) || field.isAnnotationPresent(DtoName.class)) {
				field.setAccessible(true);
				try {
					if (field.isAnnotationPresent(DtoId.class)) {
						basicData.setId((Long) field.get(object));
					} else if (field.isAnnotationPresent(DtoName.class)) {
						basicData.setName((String) field.get(object));
					}
				} catch (IllegalAccessException e) {
					log.error("Error acessing field value for : {} : {}", field.getName(), e.getMessage());
				}
			}
		}
		return basicData;
	}
}
