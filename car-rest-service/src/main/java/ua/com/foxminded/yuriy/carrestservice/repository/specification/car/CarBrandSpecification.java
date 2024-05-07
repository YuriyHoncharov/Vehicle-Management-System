package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;

@Component
public class CarBrandSpecification implements SpecificationProvider<Car> {

	private static final String FILTER_KEY = "brand";
	private static final String FIELD_CAR_PARAM = "brand";
	private static final String FIELD_BRAND_PARAM = "name";

	@Override
	public Specification<Car> getSpecification(String[] brands) {
		return ((root, query, criteriaBuilder) -> {
			Join<Car, Brand> join = root.join(FIELD_CAR_PARAM, JoinType.INNER);
			CriteriaBuilder.In<String> predicate = criteriaBuilder.in(join.get(FIELD_BRAND_PARAM));
			for (String category : brands) {
				predicate.value(category);
			}
			return criteriaBuilder.and(predicate);
		});
	}

	@Override
	public String getFilterKey() {
		return FILTER_KEY;
	}

}
