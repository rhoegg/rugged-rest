package software.hoegg.okcjug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SmartwatchBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartwatchBackendApplication.class, args);
	}

	@Bean
	public DateFormat legibleDateFormat() {
		return new SimpleDateFormat("MMMM d");
	}
}
