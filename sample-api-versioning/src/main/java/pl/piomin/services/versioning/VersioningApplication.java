package pl.piomin.services.versioning;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import pl.piomin.services.versioning.model.Gender;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;
import pl.piomin.services.versioning.repository.PersonRepository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class VersioningApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(VersioningApplication.class).web(true).run(args);
	}

	@Bean("oldRepository")
	PersonRepository oldRepository() {
		PersonRepository repository = new PersonRepository();
		repository.add(new PersonOld(1L, "John Smith", Gender.MALE, new Date()));
		repository.add(new PersonOld(2L, "Lawrence Crawford", Gender.MALE, new Date()));
		repository.add(new PersonOld(3L, "Adam Blair", Gender.MALE, new Date()));
		repository.add(new PersonOld(4L, "Laura Saint", Gender.FEMALE, new Date()));
		return repository;
	}
	
	@Bean("currentRepository")
	PersonRepository currentRepository() {
		PersonRepository repository = new PersonRepository();
		repository.add(new PersonCurrent(1L, "John Smith", Gender.MALE, 33));
		repository.add(new PersonCurrent(2L, "Lawrence Crawford", Gender.MALE, 43));
		repository.add(new PersonCurrent(3L, "Adam Blair", Gender.MALE, 28));
		repository.add(new PersonCurrent(4L, "Laura Saint", Gender.FEMALE, 37));
		return repository;
	}

	@Bean
	public Docket swaggerPersonApi10() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("person-api-1.0")
				.select()
					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
					.paths(regex("/person/v1.0.*"))
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.0").title("Person API").description("Documentation Person API v1.0").build());
	}
	
	@Bean
	public Docket swaggerPersonApi11() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("person-api-1.1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
					.paths(regex("/person/v1.1.*"))
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.1").title("Person API").description("Documentation Person API v1.1").build());
	}
	
	@Bean
	public Docket swaggerPersonApi12() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("person-api-1.2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
					.paths(regex("/person/v1.2.*"))
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.2").title("Person API").description("Documentation Person API v1.2").build());
	}
	
}
