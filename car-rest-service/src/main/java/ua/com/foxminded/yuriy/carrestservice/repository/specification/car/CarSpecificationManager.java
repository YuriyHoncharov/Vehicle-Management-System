package ua.com.foxminded.yuriy.carrestservice.repository.specification.car;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import ua.com.foxminded.yuriy.carrestservice.entities.Car;
import ua.com.foxminded.yuriy.carrestservice.exception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationManager;
import ua.com.foxminded.yuriy.carrestservice.repository.specification.SpecificationProvider;

@Component
public class CarSpecificationManager implements SpecificationManager<Car> {

	private final Map<String, SpecificationProvider<Car>> providerMap;

	public CarSpecificationManager(List<SpecificationProvider<Car>> carSpecificationList) {
		this.providerMap = carSpecificationList.stream()
				.collect(Collectors.toMap(SpecificationProvider::getFilterKey, Function.identity()));
	}

	@Override
	public Specification<Car> get(String filterKey, String[] params) {
		if (!providerMap.containsKey(filterKey)) {
			throw new FilterIllegalArgumentException("Key " + filterKey + " is not valid for filtering");
		}
		return providerMap.get(filterKey).getSpecification(params);
	}

}
