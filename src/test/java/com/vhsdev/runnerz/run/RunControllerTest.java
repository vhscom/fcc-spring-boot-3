package com.vhsdev.runnerz.run;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RunController.class)
class RunControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  RunRepository repository;

  private final List<Run> runs = new ArrayList<>();

  @BeforeEach
  void setUp() {
    runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 3, Location.INDOOR));
  }

  @Test
  void shouldFindAllRuns() throws Exception {
    when(repository.findAll()).thenReturn(runs);
    mvc.perform(MockMvcRequestBuilders.get("/api/runs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(runs.size())));
    }
}
