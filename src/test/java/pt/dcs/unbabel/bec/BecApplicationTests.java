package pt.dcs.unbabel.bec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class BecApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(BecApplicationTests.class);

	@Test
	void run() throws IOException {

		ObjectMapper jsonMapper = TestUtil.jsonMapper;
		InputParserAndValidator parser = new InputParserAndValidator();
		InputFileParser in = new InputFileParser(jsonMapper);
		MovingAverageCalculator calculator = new MovingAverageCalculator();
		OutputFileWriter out = new OutputFileWriter(jsonMapper);

		BecApplication app = new BecApplication(parser, in, calculator, out);

		File inputFile = File.createTempFile("events", ".json");
		logger.debug(inputFile.getAbsolutePath());
		List<Map<String, Object>> events = new ArrayList<>();
		events.add(TestUtil.getEvent1());
		events.add(TestUtil.getEvent2());
		events.add(TestUtil.getEvent3());
		TestUtil.writeFile(events, inputFile);

		File outputFile = File.createTempFile("out", ".json");
		logger.debug(outputFile.getAbsolutePath());

		ApplicationArguments args = new DefaultApplicationArguments(
				"--input_file=" + inputFile.getAbsolutePath(),
				"--window_size=10",
				"--output_file=" + outputFile.getAbsolutePath());
		app.run(args);

		List<Map<String, Object>> result = TestUtil.parseFile(outputFile);
		TestUtil.makeAssertions(result);

		inputFile.deleteOnExit();
		outputFile.deleteOnExit();
	}

}
