package software.hoegg.okcjug.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "yahoo", url="https://weather-ydn-yql.media.yahoo.com")
@Service
public interface YahooWeatherClient {
	@RequestMapping(method = RequestMethod.GET, value = "/forecastrss", consumes = "application/xml")
	WeatherInfo currentWeather();

	public static class WeatherInfo {
		private String weather;
		private String temperature;

		public String getWeather() {
			return weather;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public String getTemperature() {
			return temperature;
		}

		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
	}
}
