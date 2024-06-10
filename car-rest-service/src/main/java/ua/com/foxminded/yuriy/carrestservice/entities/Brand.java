package ua.com.foxminded.yuriy.carrestservice.entities;

import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoId;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.DtoName;

@Entity
@Data
@NoArgsConstructor
@Table
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DtoId
	private Long id;

	@Column(nullable = false, unique = true)
	@DtoName
	private String name;

	@OneToMany(mappedBy = "brand")
	private Set<Model> models;

	public Brand(String name) {
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
		Brand other = (Brand) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
