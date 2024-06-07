package com.vhsdev.runnerz.run;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class RunRepository {

  private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
  private final JdbcClient jdbcClient;

  public RunRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Run> findAll() {
    return jdbcClient.sql("select * from run")
        .query(Run.class)
        .list();
  }

  public Optional<Run> findById(Integer id) {
    return jdbcClient.sql("select id,title,started_on,completed_on,miles,location from run where id = :id")
        .param("id", id)
        .query(Run.class)
        .optional();
  }
}
