package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.SetJoin;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.entities.Category;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;
 
@Component
public class CarCategorySpecification implements SpecificationProvider<Car> {

	private static final String FILTER_KEY = "category";
	private static final String FIELD_CAR_PARAM = "category";
	private static final String FIELD_CATEGORY_PARAM = "name";

	@Override
	public Specification<Car> getSpecification(String[] categories) {
		return ((root, query, criteriaBuilder) -> {
			SetJoin<Car, Category> join = root.joinSet(FIELD_CAR_PARAM, JoinType.LEFT);
			CriteriaBuilder.In<String> predicate = criteriaBuilder.in(join.get(FIELD_CATEGORY_PARAM));
			for (String category : categories) {
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
