package com.example.gsgateway.custompredicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPredicatesConfig {
	
	@Bean
	public BetweenHoursRoutePredicateFactory betweenHours() {
		return new BetweenHoursRoutePredicateFactory();
	}
}
