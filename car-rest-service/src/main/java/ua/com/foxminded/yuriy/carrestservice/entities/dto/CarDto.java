package ua.com.foxminded.yuriy.carrestservice.entities.dto;

import java.util.Set;
import lombok.Data;

@Data
public class CarDto {
	private String brand;
	private String model;
	private int productionYear;
	private Set<String> categories;
}
