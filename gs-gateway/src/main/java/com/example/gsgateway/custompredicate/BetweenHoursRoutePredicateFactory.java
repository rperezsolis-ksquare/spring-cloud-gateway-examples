package com.example.gsgateway.custompredicate;

import java.time.LocalTime;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

public class BetweenHoursRoutePredicateFactory extends 
AbstractRoutePredicateFactory<BetweenHoursRoutePredicateFactory.Config> {

	public BetweenHoursRoutePredicateFactory() {
		super(Config.class);
	}

	@Override
    public Predicate<ServerWebExchange> apply(Config config) {        
        return (ServerWebExchange t) -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(config.after) && now.isBefore(config.before);
        };        
    }
	
	@Validated
    public static class Config {        
        private LocalTime after = LocalTime.of(7,0,0,0);
        private LocalTime before = LocalTime.of(11,0,0,0);
        
		public Config(String after, String before) {
			super();
			this.after = LocalTime.parse(after);
			this.before = LocalTime.parse(before);
		}

		public LocalTime getAfter() {
			return after;
		}

		public void setAfter(LocalTime after) {
			this.after = after;
		}

		public LocalTime getBefore() {
			return before;
		}

		public void setBefore(LocalTime before) {
			this.before = before;
		}
    }
}
