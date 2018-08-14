package software.hoegg.okcjug;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FunctionalTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private DateFormat legibleDateFormat = new SimpleDateFormat("MMMM d");

	@Autowired
	private MockMvc mvc;

	@Test
	public void screenInfoShouldIncludeDate() throws Exception {
		Date now = new Date();

		logger.info(String.format("Today is %s", legibleDateFormat.format(now)));
		this.mvc.perform(get("/screen"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.banner", containsString(legibleDateFormat.format(now))));
	}
}
