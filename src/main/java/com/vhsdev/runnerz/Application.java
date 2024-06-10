package com.vhsdev.runnerz;

import com.vhsdev.runnerz.run.Location;
import com.vhsdev.runnerz.run.Run;
import com.vhsdev.runnerz.user.UserHttpClient;
import com.vhsdev.runnerz.user.UserRestClient;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    log.info("Application started successfully!");
  }

  /**
   * This magic is described at 2:43:11 in the video. It allows us to leverage the UserHttpClient.
   * The UserHttpClient is a proxy that allows us to make HTTP requests to the JSONPlaceholder API.
   */
  @Bean
  UserHttpClient userHttpClient() {
    RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(
            RestClientAdapter.create(restClient))
        .build();
    return factory.createClient(UserHttpClient.class);
  }

  @Bean
  CommandLineRunner runner(UserHttpClient client) {
    return args -> {
      log.info("Users: {}", client.findAll());

      // log an individual user
      log.info("User: {}", client.findById(1));
    };
  }

//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			Run run = new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plus(1,
//					ChronoUnit.HOURS), 5, Location.OUTDOOR);
//			log.info("Run: {}", run);
//		};
//	}
}
