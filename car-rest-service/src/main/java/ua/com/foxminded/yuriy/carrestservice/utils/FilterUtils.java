package ua.com.foxminded.yuriy.carrestservice.utils;

import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.properties.SortingDefaultValues;

@Component
public class FilterUtils {
	private static final String SORT_ORDER = "sortOrder";
	private static final String PAGE = "page";
	private static final String SIZE = "size";
	private static final String SORT_BY = "sortBy";
	
	private String defaultSortParameter = SortingDefaultValues.SORT_BY;
	private String defaultPageNumber = SortingDefaultValues.PAGE;
	private String defaultPageSize = SortingDefaultValues.PAGE_SIZE;
	private String defaultSortOrder = SortingDefaultValues.SORT_ORDER;

	private static final String ERROR_MESSAGE_PAGE_SIZE = "Page or size can not be null.";

	private static final String ERROR_MESSAGE_NUMBER_FORMAT = "Page or size should be a number.";

	public Pageable getPage(String sortBy, String sortOrder, Integer page, Integer size) {
		try {
			Sort.Direction orderingDirection = Sort.Direction.fromString(sortOrder);
			Sort sortByRequest = Sort.by(orderingDirection, sortBy);
			return PageRequest.of(page, size, sortByRequest);
		} catch (IllegalArgumentException e) {
			throw new FilterIllegalArgumentException(e.getMessage());

		} catch (NullPointerException e) {
			throw new FilterIllegalArgumentException(ERROR_MESSAGE_PAGE_SIZE);
		}
	}

	public Pageable getPageFromFilters(Map<String, String> filters) {
		try {
			return getPage(filters.get(SORT_BY) == null ? defaultSortParameter : filters.remove(SORT_BY),
					filters.get(SORT_ORDER) == null ? defaultSortOrder : filters.remove(SORT_ORDER),
					Integer.valueOf(filters.get(PAGE) == null ? defaultPageNumber : filters.remove(PAGE)),
					Integer.valueOf(filters.get(SIZE) == null ? defaultPageSize : filters.remove(SIZE)));
		} catch (NumberFormatException e) {
			throw new FilterIllegalArgumentException(ERROR_MESSAGE_NUMBER_FORMAT);
		}

	}
}
