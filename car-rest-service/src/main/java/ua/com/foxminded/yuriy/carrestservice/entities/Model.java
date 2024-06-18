package ua.com.foxminded.yuriy.carrestservice.entities;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoId;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoName;

@Entity
@Data
@Table
@RequiredArgsConstructor
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@DtoId
	private Long id;

	@Column(name = "name")
	@DtoName
	private String name;

	@ManyToOne
	@JoinColumn(name = "brand_Id")
	private Brand brand;

	public Model(String name, Brand brand) {
		this.name = name;
		this.brand = brand;
	}

	public Model(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model other = (Model) obj;
		return Objects.equals(brand, other.brand) && Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, name);
	}

}
