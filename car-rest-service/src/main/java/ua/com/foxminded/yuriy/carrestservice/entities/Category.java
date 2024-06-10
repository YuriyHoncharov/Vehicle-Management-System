package ua.com.foxminded.yuriy.carrestservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoId;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoName;

@Entity
@Data
@NoArgsConstructor
@Table

public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@DtoId
	private Long id;

	@Column(name = "name")
	@DtoName
	private String name;	

	public Category(String name) {
		this.name = name;
	}	
}
