package com.celonis.microservices.weather.api.it;

import com.celonis.microservices.weather.api.infrastructure.adapter.out.database.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.internal.Files;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.wiremock.spring.EnableWireMock;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWireMock
@ActiveProfiles("it-tests")
public class IntegrationTestUtils {
    static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withDatabaseName("demo")
            .withUsername("root")
            .withPassword("pass");
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WeatherRepository weatherRepository;
    @Autowired
    ObjectMapper objectMapper;
    private static WireMockServer wireMockServer;

    @Value("${app.weather-api.api-key}")
    private String apiKey;

    static void initContainers() {
        MY_SQL_CONTAINER.start();
    }

    static void stopContainers() {
        MY_SQL_CONTAINER.stop();
    }

    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }

    @BeforeAll
    public static void beforeAll() {
        initContainers();
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
    }

    @AfterAll
    public static void afterAll() {
        stopContainers();
        wireMockServer.stop();
    }

    @DynamicPropertySource
    public static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registerProperties(registry);
    }

    @BeforeEach
    public void beforeEach() {
        this.weatherRepository.deleteAll();
    }

    public void mockExternalApi(String city) throws IOException {
        var file = new File("src/test/resources/it", "ExternalAPIResponse_success.json");
        var externalApiResponse = Files.read(file, Charset.defaultCharset());
        stubFor(get(urlPathEqualTo("/v1/forecast.json"))
                .withQueryParam("q", equalTo(city))
                .withQueryParam("key", equalTo(apiKey))
                .withQueryParam("days", equalTo("2"))
                .willReturn(aResponse().withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withResponseBody(Body.fromJsonBytes(externalApiResponse.getBytes())))
        );
    }
}
