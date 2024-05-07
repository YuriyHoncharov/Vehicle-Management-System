package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.CriteriaBuilder;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;

@Component
public class CarYearOfProductionSpecification implements SpecificationProvider<Car>{
	
	private static final String FILTER_KEY = "year";
   private static final String FIELD_PARAM = "productionYear";

	@Override
	public Specification<Car> getSpecification(String[] years) {
		return ((root, query, criteriaBuilder) ->{
			CriteriaBuilder.In<String>predicate = criteriaBuilder.in(root.get(FIELD_PARAM));
			for(String category : years) {
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
