package ua.com.foxminded.yuriy.carrestservice.utils.mapper;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.BasicDataDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoId;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoName;

@Data
public class BasicDataDtoConverter {

	public static BasicDataDto convertToBasicDataDto(Object object) {
		Logger logger = LoggerFactory.getLogger(BasicDataDtoConverter.class);

		if (object == null) {
			logger.error("Cannot convert null object to BasicDataDto");
			return null;
		}
		BasicDataDto basicData = new BasicDataDto();
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(DtoId.class)) {
				field.setAccessible(true);
				try {
					basicData.setId((Long) field.get(object));
				} catch (IllegalArgumentException e) {
					logger.error("Error accessing field value for ID", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.error("Illegal argument for name field", e);
					e.printStackTrace();
				}
			} else if (field.isAnnotationPresent(DtoName.class)) {
				field.setAccessible(true);
				try {
					basicData.setName((String) field.get(object));
				} catch (IllegalArgumentException e) {
					logger.error("Error accessing field value for ID", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.error("Illegal argument for name field", e);
					e.printStackTrace();
				}
			}
		}
		return basicData;
	}
}
