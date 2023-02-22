package pl.piomin.services.versioning;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.versioning.model.Gender;
import pl.piomin.services.versioning.model.PersonOld;
import pl.piomin.services.versioning.repository.PersonRepository;

import java.time.LocalDate;

@SpringBootApplication
public class VersioningApplication {

	public static void main(String[] args) {
		SpringApplication.run(VersioningApplication.class, args);
	}

	@Bean
	PersonRepository repository() {
		PersonRepository repository = new PersonRepository();
		repository.add(new PersonOld(1L, "John Smith", Gender.MALE, LocalDate.parse("1977-01-20")));
		repository.add(new PersonOld(2L, "Lawrence Crawford", Gender.MALE, LocalDate.parse("1987-01-20")));
		repository.add(new PersonOld(3L, "Adam Blair", Gender.MALE, LocalDate.parse("1982-01-20")));
		repository.add(new PersonOld(4L, "Laura Saint", Gender.FEMALE, LocalDate.parse("1965-01-20")));
		return repository;
	}

	@Bean
	public GroupedOpenApi personApiViaHeaders() {
		return GroupedOpenApi.builder()
				.group("person-via-headers")
				.pathsToMatch("/persons-via-headers/**")
				.build();
	}

	@Bean
	public GroupedOpenApi personApi10() {
		return GroupedOpenApi.builder()
				.group("person-api-1.0")
				.pathsToMatch("/person/v1.0/**")
				.build();
	}

	@Bean
	public GroupedOpenApi personApi11() {
		return GroupedOpenApi.builder()
				.group("person-api-1.1")
				.pathsToMatch("/person/v1.1/**")
				.build();
	}

	@Bean
	public GroupedOpenApi personApi12() {
		return GroupedOpenApi.builder()
				.group("person-api-1.2")
				.pathsToMatch("/person/v1.2/**")
				.build();
	}

//	@Bean
//	public Docket swaggerPersonApi10() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("person-api-1.0")
//				.select()
//					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
//					.paths(regex("/person/v1.0.*"))
//				.build()
//				.apiInfo(new ApiInfoBuilder().version("1.0").title("Person API").description("Documentation Person API v1.0").build());
//	}
//
//	@Bean
//	public Docket swaggerPersonApi11() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("person-api-1.1")
//				.select()
//					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
//					.paths(regex("/person/v1.1.*"))
//				.build()
//				.apiInfo(new ApiInfoBuilder().version("1.1").title("Person API").description("Documentation Person API v1.1").build());
//	}
//
//	@Bean
//	public Docket swaggerPersonApi12() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("person-api-1.2")
//				.select()
//					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
//					.paths(regex("/person/v1.2.*"))
//				.build()
//				.apiInfo(new ApiInfoBuilder().version("1.2").title("Person API").description("Documentation Person API v1.2").build());
//	}
	
}
