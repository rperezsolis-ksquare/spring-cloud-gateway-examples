package com.example.gsgateway.customfilter;

import java.time.Duration;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

/**
 * See https://github.com/vladimir-bukhtoyarov/bucket4j
 */

@Component
public class Bucket4JRateLimiterGatewayFilterFactory extends 
AbstractGatewayFilterFactory<Bucket4JRateLimiterGatewayFilterFactory.Config>{

	public Bucket4JRateLimiterGatewayFilterFactory() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		Refill refill = Refill.greedy(config.refillTokens, Duration.ofSeconds(1));
        Bandwidth limit = Bandwidth.classic(config.capacity, refill);
		Bucket bucket = Bucket4j.builder().addLimit(limit).build();
		
		return (exchange, chain) -> {
			boolean consumed = bucket.tryConsume(1);
			if (consumed) {
				return chain.filter(exchange);
			}
			exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
			return exchange.getResponse().setComplete();
		};
	}
	
	public static class Config {
		private int capacity; 
		private int refillTokens; 
		
		public Config(int capacity, int refillTokens, int refillPeriod) {
			super();
			this.capacity = capacity;
			this.refillTokens = refillTokens;
		}

		public int getCapacity() {
			return capacity;
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		public int getRefillTokens() {
			return refillTokens;
		}

		public void setRefillTokens(int refillTokens) {
			this.refillTokens = refillTokens;
		}
	}
}
