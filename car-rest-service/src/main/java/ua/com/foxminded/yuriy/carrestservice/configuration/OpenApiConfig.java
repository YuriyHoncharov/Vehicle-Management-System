package ua.com.foxminded.yuriy.carrestservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Component
public class OpenApiConfig {

	private final String schemeName = "bearerAuth";
	private final String bearerFormat = "JWT";
	private final String scheme = "bearer";

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.contact(new Contact().email("yuriyhoncharov@gmail.com").name("Yuriy Honcharov")
								.url("https://www.linkedin.com/in/yuriy-honcharov-b61752267/"))
						.description("OpenApi Documentation for Car Service Web Application")
						.title("OpenApi specification - Car Service").version("1.0"))
				.addServersItem(new Server().description("Local ENV").url("http://localhost:8080"))
				.addSecurityItem(new SecurityRequirement().addList(schemeName))
				.components(new Components().addSecuritySchemes(schemeName, new SecurityScheme().name(schemeName)
				.type(Type.HTTP).bearerFormat(bearerFormat).in(In.HEADER).scheme(scheme)));
	}
}
