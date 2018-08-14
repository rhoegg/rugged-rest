package software.hoegg.okcjug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableFeignClients
public class SmartwatchBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartwatchBackendApplication.class, args);
	}

	@Bean
	public DateFormat legibleDateFormat() {
		return new SimpleDateFormat("MMMM d");
	}
}
