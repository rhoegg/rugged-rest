package software.hoegg.okcjug.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import software.hoegg.okcjug.DeviceInfo;
import software.hoegg.okcjug.config.WeatherUnlockedConfig;

@FeignClient(name = "weather", configuration = WeatherUnlockedClient.ApiConfig.class, fallback = WeatherUnlockedClient.Fallback.class)
@Service
public interface WeatherUnlockedClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/current/{location}", consumes = "application/json")
	WeatherInfo currentWeather(@PathVariable("location") String location);

	default String formatLocation(DeviceInfo deviceInfo) {
		return String.format("%s,%s", deviceInfo.getLocation().getLatitude(), deviceInfo.getLocation().getLongitude());
	}

	public static class WeatherInfo {
		@JsonProperty("wx_desc")
		private String weather;
		@JsonProperty("temp_f")
		private Double temperature;

		public String getWeather() {
			return weather;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public Double getTemperature() {
			return temperature;
		}

		public void setTemperature(Double temperature) {
			this.temperature = temperature;
		}
	}

	@Configuration
	public static class ApiConfig {
		final private WeatherUnlockedConfig config;
		public ApiConfig(WeatherUnlockedConfig config) {
			this.config = config;
		}

		@Bean
		public RequestInterceptor authenticator() {
			return new Authenticator(config);
		}
	}

	public static class Authenticator implements RequestInterceptor {
		final WeatherUnlockedConfig config;
		public Authenticator(WeatherUnlockedConfig config) {
			this.config = config;
		}

		@Override
		public void apply(RequestTemplate template) {
			template.query("app_id", config.getAppId());
			template.query("app_key", config.getKey());
		}
	}


	@Service
	public static class Fallback implements WeatherUnlockedClient {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		@Autowired
		private YahooWeatherClient yahooWeatherClient;
		@Autowired
		private DeviceInfo deviceInfo;

		@Override
		public WeatherInfo currentWeather(String location) {
			logger.warn("Using fallback weather service");
			YahooWeatherClient.WeatherInfo yahooWeather = yahooWeatherClient.currentWeather();
			WeatherInfo weather = new WeatherInfo();
			weather.setWeather(yahooWeather.getWeather());
			weather.setTemperature(yahooWeather.getTemperature());
			return weather;
		}
	}
}
