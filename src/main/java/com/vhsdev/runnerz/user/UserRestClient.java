package com.vhsdev.runnerz.user;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserRestClient {

  private final RestClient restClient;

  public UserRestClient(RestClient.Builder restClientBuilder) {
    this.restClient = restClientBuilder.baseUrl("https://jsonplaceholder.typicode.com/")
        .build();
  }

  public List<User> findAll() {
//    return restClient.get()
//        .uri("/users")
//        .retrieve()
//        .bodyToFlux(User.class)
//        .collectList()
//        .block();

    // Above is what Co-Pilot suggested. Below is what Dan Vega suggested.
    return restClient.get()
        .uri("/users")
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }

  public User findById(Integer id) {
//    return restClient.get()
//        .uri("/users/{id}", id)
//        .retrieve()
//        .bodyToMono(User.class)
//        .block();

      // Above is what Co-Pilot suggested. Below is what Dan Vega suggested.
      return restClient.get()
          .uri("/users/{id}", id)
          .retrieve()
          .body(User.class);
  }
}
