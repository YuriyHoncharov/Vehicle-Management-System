package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;

@Component
public class CarModelSpecification implements SpecificationProvider<Car> {

	private static final String FILTER_KEY = "model";
	private static final String FIELD_CAR_PARAM = "model";
	private static final String FIELD_MODEL_PARAM = "name";

	@Override
	public Specification<Car> getSpecification(String[] models) {
		return ((root, query, criteriaBuilder) -> {
			Join<Car, Model> join = root.join(FIELD_CAR_PARAM, JoinType.INNER);
			CriteriaBuilder.In<String> predicate = criteriaBuilder.in(join.get(FIELD_MODEL_PARAM));
			for (String category : models) {
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
