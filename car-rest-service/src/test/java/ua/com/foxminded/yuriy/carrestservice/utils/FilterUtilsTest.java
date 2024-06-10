package ua.com.foxminded.yuriy.carrestservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FilterIllegalArgumentException;

@ExtendWith(MockitoExtension.class)
class FilterUtilsTest {

	@Mock
	private FilterUtils filterUtils;

	@BeforeEach
	void setUp() {
		filterUtils = new FilterUtils();
	}

	@Test
	void getPageFromFilters_shouldGetPageable_andRemoveFromFilters() {
		Map<String, String> filters = new HashMap<>();
		filters.put("sortBy", "name");
		filters.put("sortOrder", "desc");
		filters.put("page", "1");
		filters.put("size", "10");
		Sort sort = Sort.by(Sort.Direction.DESC, "name");
		Pageable pageInfo = PageRequest.of(1, 10, sort);
		Pageable pageable = filterUtils.getPageFromFilters(filters);
		assertEquals(pageInfo, pageable);
		assertEquals(1, pageable.getPageNumber());
		assertEquals(10, pageable.getPageSize());
		assertEquals(sort, pageable.getSort());
		assertTrue(filters.isEmpty());
	}

	@Test
	void testGetPageFromFiltersWithInvalidPageNumber() {

		Map<String, String> filters = new HashMap<>();
		filters.put("sortBy", "nam1");
		filters.put("sortOrder", "descMaybe");
		filters.put("page", "f");
		filters.put("size", "f");
		assertThrows(FilterIllegalArgumentException.class, () -> {
			filterUtils.getPageFromFilters(filters);
		});
	}

	@Test
	void testGetPageFromFiltersWithInvalidSortOrder() {

		Map<String, String> filters = new HashMap<>();
		filters.put("sortBy", "nam1");
		filters.put("sortOrder", "descMaybe");
		filters.put("page", "1");
		filters.put("size", "10");
		assertThrows(FilterIllegalArgumentException.class, () -> {
			filterUtils.getPageFromFilters(filters);
		});
	}

}
