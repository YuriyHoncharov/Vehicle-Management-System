package ua.com.foxminded.yuriy.carrestservice.utils;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsvFileData {
	private String objectId;
	private String brand;
	private int year;
	private String model;
	private Set<String> category;
}
