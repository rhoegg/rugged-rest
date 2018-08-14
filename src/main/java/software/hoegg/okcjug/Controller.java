package software.hoegg.okcjug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.hoegg.okcjug.api.WeatherUnlockedClient;
import software.hoegg.okcjug.model.ScreenInfo;

import java.text.DateFormat;
import java.util.Date;

@RestController
public class Controller {
	@Autowired
	private DateFormat legibleDate;
	@Autowired
	private WeatherUnlockedClient weatherUnlockedClient;
	@Autowired
	private DeviceInfo deviceInfo;

	@RequestMapping("/screen")
	public ScreenInfo screenInfo() {
		final ScreenInfo screenInfo = new ScreenInfo();
		screenInfo.setBanner(String.format("Today is %s.", legibleDate.format(new Date())));
		screenInfo.setWeather(lookupWeather());
		return screenInfo;
	}

	private String lookupWeather() {
		WeatherUnlockedClient.WeatherInfo info =
			weatherUnlockedClient.currentWeather(weatherUnlockedClient.formatLocation(deviceInfo));
		return info.getWeather();
	}

}
