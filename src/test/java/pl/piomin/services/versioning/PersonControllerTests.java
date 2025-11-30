package pl.piomin.services.versioning;

import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.context.WebApplicationContext;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTests {

    private WebApplicationContext context;
    private RestTestClient restTestClient;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context)
                .baseUrl("/persons")
                .apiVersionInserter(ApiVersionInserter.usePathSegment(1))
                .build();
    }

    @Test
    @Order(1)
    void addV0() {
        restTestClient.post()
                .apiVersion("v1.1")
                .body(Instancio.create(PersonOld.class))
                .exchange()
                .expectBody(PersonOld.class)
                .value(personOld -> assertNotNull(personOld.getId()));
    }

    @Test
    @Order(2)
    void addV2() {
        restTestClient.post()
                .apiVersion("v1.2")
                .body(Instancio.create(PersonCurrent.class))
                .exchange()
                .expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }

    @Test
    @Order(3)
    void findByIdV0() {
        restTestClient.get().uri("/{id}", 1)
                .apiVersion("v1.0")
                .exchange()
                .expectBody(PersonOld.class)
                .value(personOld -> assertNotNull(personOld.getId()));
    }

    @Test
    @Order(3)
    void findByIdV2() {
        restTestClient.get().uri("/{id}", 2)
                .apiVersion("v1.2")
                .exchange()
                .expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }

    @Test
    @Order(3)
    void findByIdV2ToV1Compability() {
        restTestClient.get().uri("/{id}", 1)
                .apiVersion("v1.2")
                .exchange()
                .expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }

    @Test
    @Order(4)
    void delete() {
        restTestClient.delete().uri("/{id}", 5)
                .apiVersion("v1.2")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
