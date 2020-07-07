package com.example.gsgateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class GsGatewayApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void contextLoads() throws Exception {
	    //Stubs
	    stubFor(get(urlEqualTo("http://localhost:8080/demo/greeting"))
	        .willReturn(aResponse()
	        .withBody("Hello world from demo service")));
	    stubFor(get(urlEqualTo("http://localhost:8080/demo/greeting"))
	        .willReturn(aResponse()
	        .withBody("fallback")
	        .withFixedDelay(3000)));

	    webClient
	      .get().uri("http://localhost:8080/demo/greeting")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBody()
	      .consumeWith(response -> 
	      	assertThat(response.getResponseBody()).isEqualTo("Hello world from demo service".getBytes()));

	    webClient
	      .get().uri("http://localhost:8080/demo/greeting")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBody()
	      .consumeWith(response -> 
	      	assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));
	  
	}

}
