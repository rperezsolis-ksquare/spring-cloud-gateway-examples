package com.example.gsgateway;

import java.time.Duration;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class GsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsGatewayApplication.class, args);
	}
	
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
	    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
	        .circuitBreakerConfig(CircuitBreakerConfig.custom()
	            .slidingWindowSize(6)
	            .failureRateThreshold(50)
	            .build())
	        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(1000)).build()).build());
	}

	@GetMapping("/fallback")
	public Mono<String> fallback(@RequestHeader Map<String, String> headers) {
		return Mono.just("Fallback response");
	}
	
	static class Hello {
	    String message;

	    public Hello() { }

	    public Hello(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
	}
}
