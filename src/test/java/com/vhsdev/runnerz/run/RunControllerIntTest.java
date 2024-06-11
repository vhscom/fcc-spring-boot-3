package com.vhsdev.runnerz.run;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntTest {

  @LocalServerPort
  int randomServerPort;

  RestClient restClient;

  @BeforeEach
  void setUp() {
    restClient = RestClient.create("http://localhost:" + randomServerPort);
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = restClient.get()
        .uri("/api/runs")
        .retrieve()
        .body(new ParameterizedTypeReference<>(){});

    assertEquals(10, runs.size());
  }
}
