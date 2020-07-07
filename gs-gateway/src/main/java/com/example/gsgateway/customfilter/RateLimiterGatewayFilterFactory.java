package com.example.gsgateway.customfilter;

import java.util.concurrent.TimeUnit;

import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * See https://github.com/bbeck/token-bucket
 */

@Component
public class RateLimiterGatewayFilterFactory extends 
AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {
	
	public RateLimiterGatewayFilterFactory() {
        super(Config.class);
    }

	@Override
	public GatewayFilter apply(Config config) {
		final TokenBucket tokenBucket = TokenBuckets.builder()
				.withCapacity(config.capacity)
				.withFixedIntervalRefillStrategy(config.refillTokens, config.refillPeriod, TimeUnit.SECONDS)
				.build();

		return (exchange, chain) -> {
			boolean consumed = tokenBucket.tryConsume();
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
		private int refillPeriod; 
		
		public Config(int capacity, int refillTokens, int refillPeriod) {
			super();
			this.capacity = capacity;
			this.refillTokens = refillTokens;
			this.refillPeriod = refillPeriod;
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

		public int getRefillPeriod() {
			return refillPeriod;
		}

		public void setRefillPeriod(int refillPeriod) {
			this.refillPeriod = refillPeriod;
		}
	}
}