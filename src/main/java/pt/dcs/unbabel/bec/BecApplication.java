package pt.dcs.unbabel.bec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BecApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(BecApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BecApplication.class, args);
	}

	private final InputParserAndValidator parser;
	private final InputFileParser in;
	private final MovingAverageCalculator calculator;
	private final OutputFileWriter out;

	public BecApplication(InputParserAndValidator parser, InputFileParser in, MovingAverageCalculator calculator, OutputFileWriter out) {
		this.parser = parser;
		this.in = in;
		this.calculator = calculator;
		this.out = out;
	}

	@Override
	public void run(ApplicationArguments args) {
		try {
			Inputs inputs = parser.parseAndValidate(args);
			List<Map<String, Object>> events = in.parse(inputs.getInFile());
			List<Map<String, Object>> dataPoints = calculator.calculate(events, inputs.getWindowSize());
			out.export(dataPoints, inputs.getOutFile());
		} catch (InputValidationException e) {
			logger.error("Error using the application: [{}]" + System.lineSeparator() + "Usage: java -jar bec.jar --input_file=<json events file> --window_size=<number of minutes> [--output_file=<file to write output to>]", e.getMessage(), e);
		}
	}

}
