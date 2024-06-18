package ua.com.foxminded.yuriy.carrestservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
//@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Yuriy Honcharov", email = "yuriyhoncharov@gmail.com", url = "https://www.linkedin.com/in/yuriy-honcharov-b61752267/"), description = "OpenApi Documentation for Car Service Web Application",title = "OpenApi specification - Car Service", version = "1.0"), servers = @Server(description = "Local ENV", url = "http://localhost:8080"))
//@Server(description = "Prod ENV", url = "http://localhost:8080")
//@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, description = "JWT TOKEN", scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
//@SecurityRequirement(name = "bearerAuth")
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.contact(new Contact().email("yuriyhoncharov@gmail.com").name("Yuriy Honcharov")
								.url("https://www.linkedin.com/in/yuriy-honcharov-b61752267/"))
						.description("OpenApi Documentation for Car Service Web Application")
						.title("OpenApi specification - Car Service").version("1.0"))
				.addServersItem(new Server().description("Local ENV").url("http://localhost:8080"))
				.addServersItem(new Server().description("Local PROD").url("http://localhost:8080"))
				.schemaRequirement("bearerAuth",
						new SecurityScheme().name("bearerAuth").type(Type.HTTP).description("JWT TOKEN").scheme("bearer")
								.bearerFormat("JWT").in(In.HEADER))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}
