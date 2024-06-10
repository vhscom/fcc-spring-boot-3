package com.vhsdev.runnerz.run;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcRunRepository {

  private static final Logger log = LoggerFactory.getLogger(JdbcRunRepository.class);
  private final JdbcClient jdbcClient;

  public JdbcRunRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Run> findAll() {
    return jdbcClient.sql("select * from run")
        .query(Run.class)
        .list();
  }

  public Optional<Run> findById(Integer id) {
    return jdbcClient.sql(
            "select id,title,started_on,completed_on,miles,location from run where id = :id")
        .param("id", id)
        .query(Run.class)
        .optional();
  }

  public void create(Run run) {
    var updated = jdbcClient.sql(
            "insert into run (id,title,started_on,completed_on,miles,location) values (?,?,?,?,?,?)")
        .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(),
            run.location().toString()))
        .update();

    Assert.state(updated == 1, "Failed to create run" + run.title());
  }

  public void update(Run run, Integer id) {
    var updated = jdbcClient.sql(
            "update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
        .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(),
            run.location().toString(), id))
        .update();

    Assert.state(updated == 1, "Failed to update run" + run.title());
  }

  public void delete(Integer id) {
    var updated = jdbcClient.sql("delete from run where id = ?")
        .param("id", id)
        .update();

    Assert.state(updated == 1, "Failed to delete run" + id);
  }

  public int count() {
    return jdbcClient.sql("select * from run").query().listOfRows().size();
  }

  public void saveAll(List<Run> runs) {
    runs.stream().forEach(this::create);
  }

  public List<Run> findByLocation(Location location) {
    return jdbcClient.sql("select * from run where location = :location")
        .param("location", location) // not using toString() here
        .query(Run.class)
        .list();
  }

}
