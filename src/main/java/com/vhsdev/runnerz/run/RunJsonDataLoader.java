package com.vhsdev.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
  private final JdbcClientRunRepository runRepository;
  private final ObjectMapper objectMapper;

  public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
    this.runRepository = runRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (runRepository.count() == 0) {
      try (InputStream inputStream = getClass().getResourceAsStream("/data/runs.json")) {
        Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
        log.info("Reading {} runs from JSON file and saving it to a database.",
            allRuns.runs().size());
        runRepository.saveAll(allRuns.runs());
      } catch (IOException e) {
        log.error("Failed to load runs from JSON file", e);
      }
    } else {
      log.info("Not loading Runs from JSON file because the collection contains data.");
    }
  }
}
