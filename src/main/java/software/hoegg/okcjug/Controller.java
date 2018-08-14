package software.hoegg.okcjug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.hoegg.okcjug.model.ScreenInfo;

import java.text.DateFormat;
import java.util.Date;

@RestController
public class Controller {
	@Autowired
	DateFormat legibleDate;

	@RequestMapping("/screen")
	public ScreenInfo screenInfo() {
		final ScreenInfo screenInfo = new ScreenInfo();
		screenInfo.setBanner(String.format("Today is %s.", legibleDate.format(new Date())));
		return screenInfo;
	}
}
