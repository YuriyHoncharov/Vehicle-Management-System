package ua.com.foxminded.yuriy.carrestservice.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table

public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "object_Id")
	private String objectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_Id")
	private Brand brand;

	@Column(name = "production_year")
	private int productionYear;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_Id")
	private Model model;

	@ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinTable(name = "car_category", joinColumns = @JoinColumn(name = "car_Id"), inverseJoinColumns = @JoinColumn(name = "category_Id"))
	private Set<Category> category;
}
