package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;
import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;

@Component
public class CarYearOfProductionSpecification implements SpecificationProvider<Car> {

	private static final String FILTER_KEY = "year";
	private static final String FIELD_PARAM = "productionYear";

	@Override
	public Specification<Car> getSpecification(String[] years) {

		Optional<Integer> minYear = getYearValueFromString(years.length > 0 ? years[0] : null);
		Optional<Integer> maxYear = getYearValueFromString(years.length > 1 ? years[1] : null);

		return ((root, query, criteriaBuilder) -> {
			List<Predicate> yearFilters = new ArrayList<>();

			minYear.ifPresent(
					min -> yearFilters.add(criteriaBuilder.greaterThanOrEqualTo(root.get(FIELD_PARAM), minYear.get())));
			maxYear.ifPresent(
					min -> yearFilters.add(criteriaBuilder.lessThanOrEqualTo(root.get(FIELD_PARAM), maxYear.get())));

			return criteriaBuilder.and(yearFilters.toArray(new Predicate[0]));
		});
	}

	@Override
	public String getFilterKey() {
		return FILTER_KEY;
	}

	private Optional<Integer> getYearValueFromString(String value) {
		return Optional.ofNullable(value).filter(year -> !year.trim().isEmpty()).map(notNullValue -> {
			try {
				return Integer.parseInt(notNullValue.trim());
			} catch (NumberFormatException e) {
				throw new FilterIllegalArgumentException("Invalid year format : " + notNullValue);
			}
		});
	}

}
