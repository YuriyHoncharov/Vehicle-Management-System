package ua.com.foxminded.yuriy.carrestservice.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ApiKeyAuthExtractor {

	@Value("${apiKey}")
	private String apiKey;

	public Optional<Authentication> extract(HttpServletRequest request) {
		String providedKey = request.getHeader("ApiKey");
		if (providedKey == null || !providedKey.equals(apiKey)) {
			return Optional.empty();
		}
		return Optional.of(new ApiKeyAuth(providedKey, AuthorityUtils.NO_AUTHORITIES));
	}
}
