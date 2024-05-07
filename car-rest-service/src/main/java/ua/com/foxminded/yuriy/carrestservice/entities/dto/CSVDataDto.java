package ua.com.foxminded.yuriy.carrestservice.entities.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CSVDataDto {
	private String objectId;
	private String brand;
	private int year;
	private String model;
	private Set<String> category;

}
