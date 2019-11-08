package pt.dcs.unbabel.bec;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InputParserAndValidatorTests {

    private InputParserAndValidator parser;

    @BeforeAll
    void setup() {
        parser = new InputParserAndValidator();
    }

    @Test
    void noInputFile() {
        ApplicationArguments args = new DefaultApplicationArguments("--window_size=10");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("No input file was provided", e.getMessage());
        }
    }

    @Test
    void moreThan1InputFile() {
        ApplicationArguments args = new DefaultApplicationArguments("--input_file=events1.json", "--input_file=events2.json", "--window_size=10");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("More than 1 input file was provided", e.getMessage());
        }
    }

    @Test
    void inputFileDoesNotExist() throws IOException {
        File file = File.createTempFile("events", ".json");
        String name = file.getAbsolutePath();
        file.delete();
        ApplicationArguments args = new DefaultApplicationArguments("--input_file=" + name, "--window_size=10");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals(format("Input file [%s] does not exist or cannot be read", name), e.getMessage());
        }
    }

    @Test
    void noWindowSize() throws IOException {
        File file = File.createTempFile("events", ".json");
        ApplicationArguments args = new DefaultApplicationArguments("--input_file=" + file.getAbsolutePath());
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("No window size was provided", e.getMessage());
        }
        file.delete();
    }

    @Test
    void moreThan1WindowSize() throws IOException {
        File file = File.createTempFile("events", ".json");
        ApplicationArguments args = new DefaultApplicationArguments("--input_file=" + file.getAbsolutePath(), "--window_size=10", "--window_size=12");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("More than 1 window size was provided", e.getMessage());
        }
        file.delete();
    }

    @Test
    void invalidWindowSize() throws IOException {
        File file = File.createTempFile("events", ".json");
        ApplicationArguments args = new DefaultApplicationArguments("--input_file=" + file.getAbsolutePath(), "--window_size=-1");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("Provided window size is not a positive integer value", e.getMessage());
        }

        args = new DefaultApplicationArguments("--input_file=" + file.getAbsolutePath(), "--window_size=ABC");
        try {
            parser.parseAndValidate(args);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InputValidationException);
            assertEquals("Provided window size is not a positive integer value", e.getMessage());
        }

        file.delete();
    }

}
