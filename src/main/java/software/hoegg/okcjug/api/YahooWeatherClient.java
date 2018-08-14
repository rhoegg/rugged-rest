package software.hoegg.okcjug.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "yahoo", url="https://weather-ydn-yql.media.yahoo.com", fallback = YahooWeatherClient.Fallback.class)
@Service
public interface YahooWeatherClient {
	@RequestMapping(method = RequestMethod.GET, value = "/forecastrss", consumes = "application/xml")
	WeatherInfo currentWeather();

	public static class WeatherInfo {
		private String weather;
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

	@Service
	public static class Fallback implements YahooWeatherClient {

		@Override public WeatherInfo currentWeather() {
			final WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setTemperature(75d);
			weatherInfo.setWeather("Probably OK");
			return weatherInfo;
		}
	}
}
