package com.example.demoservice;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class DemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}

    @GetMapping("/greeting")
    public String home() {
        return "Hello world from demo service";
    }
	
	@GetMapping("/setPath")
	public String setPathFilter() {
		return "Hello from SetPath filter";
	}
	
	//============================== Predicates ================================
	
	@GetMapping("/after")
	public String after() {
		return "After predicate factory";
	}

	@GetMapping("/before")
	public String before() {
		return "Before predicate factory";
	}
	
	@GetMapping("/between")
	public String between() {
		return "Between predicate factory";
	}
	
	@GetMapping("/cookie")
	public String cookie() {
		return "Cookie predicate factory";
	}
	
	@GetMapping("/header")
	public String header() {
		return "Header predicate factory";
	}
	
	@GetMapping("/host")
	public String host() {
		return "Host predicate factory";
	}
	
	@GetMapping("/method")
	public String method() {
		return "Method predicate factory";
	}
	
	@GetMapping("/query")
	public String query() {
		return "Query predicate factory";
	}
	
	@GetMapping("/remoteAddr")
	public String remoteAddr() {
		return "Remote Address predicate factory";
	}
	
	@GetMapping("/weight/high")
	public String weightHigh() {
		return "Weight High predicate factory";
	}
	
	@GetMapping("/weight/low")
	public String weightLow() {
		return "Weight Low predicate factory";
	}
	
	@GetMapping("/betweenHours")
	public String betweenHours() {
		return "Between Hours custom predicate factory";
	}

	//============================== Filters ================================
	
	@GetMapping("/addRequestHeader")
	public String addRequestHeader(@RequestHeader("X-Request-red") String header) {
		return "Add Request Header filter factory: " + header;
	}

	@GetMapping("/addRequestParameter")
	public String addRequestParameter(@RequestParam("color") String color) {
		return "Add Request Parameter filter factory: " + color;
	}

	@GetMapping("/addResponseHeader")
	public String addResponseHeader() {
		return "Add Response Header filter factory";
	}

	@GetMapping("/dedupeResponseHeader")
	public Mono<ResponseEntity<String>> dedupeResponseHeader() {
		return Mono.just(ResponseEntity.ok()
			      .header("color", "yellow")
			      .body("Dedupe Response Header filter factory"));
	}

	@GetMapping("/mapRequestHeader")
	public Mono<ResponseEntity<String>> mapRequestHeader(@RequestHeader("ToHeader") String header) {
		return Mono.just(ResponseEntity.ok()
			      .body("Map Request Header filter factory: " + header));
	}

	@GetMapping("/mypath/prefixPath")
	public String prefixPath() {
		return "Prefix Path filter factory";
	}

	@GetMapping("/preserveHostHeader")
	public String preserveHostHeader(@RequestHeader("Host") String header) {
		return "Preserve Host Header filter factory: " + header;
	}

	@GetMapping("/redisRateLimiter")
	public String redisRateLimiter() {
		return "Redis Rate Limiter filter";
	}
	
	@GetMapping("/rateLimiter")
	public String rateLimiter() {
		return "Rate Limiter filter";
	}
	
	@GetMapping("/bucket4jRateLimiter")
	public String bucket4jRateLimiter() {
		return "Bucket4J Rate Limiter filter";
	}

	@GetMapping("/redirect")
	public String redirect() {
		return "Redirect filter";
	}
	
	@GetMapping("/removeRequestHeader")
	public Mono<ResponseEntity<String>> removeRequestHeader(@RequestHeader Map<String, String> headers) {	 
	    return Mono.just(new ResponseEntity<String>(headers.toString(), HttpStatus.OK));
	}
	
	@GetMapping("/removeResponseHeader")
	public Mono<ResponseEntity<String>> removeResponseHeader() {
		return Mono.just(ResponseEntity.ok()
			      .header("color", "yellow")
			      .body("Remove Response Header filter factory"));
	}
	
	@GetMapping("/removeRequestParameter")
	public String removeRequestParameter(@RequestParam("color") Optional<String> color) {	 
		if (color.isPresent()) {
			return "Remove request parameter: " + color;
		} else {
			return "Remove request parameter: null" ;
		}
	}

	@PostMapping("/rewriteLocationResponseHeader")
	public Mono<ResponseEntity<String>> rewriteLocationResponseHeader() {
		//return "Rewrite Location Response Header filter factory: " + header;
		return Mono.just(ResponseEntity.ok()
			      .header("Location", "somelocation")
			      .body("Rewrite Location Response Header filter factory"));	
	}

	@GetMapping("/rewriteResponseHeader")
	public Mono<ResponseEntity<String>> rewriteResponseHeader() {
		return Mono.just(ResponseEntity.ok()
			      .header("X-Response-Red", "/42?user=ford&password=omg!what&flag=true")
			      .body("Rewrite Response Header filter factory"));	
	}

	@GetMapping("/saveSession")
	public Mono<ResponseEntity<String>> saveSession(@CookieValue("SESSION") String session) {
		return Mono.just(ResponseEntity.ok()
			      .body("Save Session filter factory: " + session));	
	}
	
	@GetMapping("/setRequestHeader")
	public String setRequestHeader(@RequestHeader("X-Request-Red") String header) {
		return "Set Request Header filter factory: " + header;
	}
	
	@GetMapping("/setResponseHeader")
	public Mono<ResponseEntity<String>> setResponseHeader() {
		return Mono.just(ResponseEntity.ok()
			      .header("X-Response-Red", "yellow")
			      .body("Set Response Header filter factory"));	
	}
	
	@GetMapping("/setStatus")
	public String setStatus() {
		return "Set Status filter factory";
	}
	
	@GetMapping("/stripPrefix")
	public String stripPrefix() {
		return "Strip Prefix filter factory";
	}	
	
	@GetMapping("/retry")
	public String retry() {
		return "Retry filter factory";
	}		
	
	@PostMapping("/requestSize")
	public String requestSize(@RequestParam("file") MultipartFile file) {
		return "Request Size filter factory";
	}	
	
	@PostMapping("/modifyRequestBody")
	public String modifyRequestBody(@RequestBody String body) {
		return "Modify Request Body filter factory: " + body;
	}
	
	@GetMapping("/modifyResponseBody")
	public String modifyResponseBody() {
		return "Modify Response Body filter factory";
	}
}
