package pt.dcs.unbabel.bec;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputFileParserTests {

    private static final Logger logger = LoggerFactory.getLogger(InputFileParserTests.class);

    @Test
    void parse() throws IOException {

        InputFileParser in = new InputFileParser(TestUtil.jsonMapper);

        File inputFile = createInputFile();
        List<Map<String, Object>> events = in.parse(inputFile);
        assertEquals(3, events.size());
        assertEquals(TestUtil.getEvent1().get("timestamp"), events.get(0).get("timestamp"));
        assertEquals(TestUtil.getEvent1().get("duration"), events.get(0).get("duration"));
        assertEquals(TestUtil.getEvent2().get("timestamp"), events.get(1).get("timestamp"));
        assertEquals(TestUtil.getEvent2().get("duration"), events.get(1).get("duration"));
        assertEquals(TestUtil.getEvent3().get("timestamp"), events.get(2).get("timestamp"));
        assertEquals(TestUtil.getEvent3().get("duration"), events.get(2).get("duration"));

        inputFile.deleteOnExit();
    }

    private File createInputFile() throws IOException {
        File file = File.createTempFile("events", ".json");
        logger.debug(file.getAbsolutePath());
        List<Map<String, Object>> events = new ArrayList<>();
        events.add(TestUtil.getEvent1());
        events.add(TestUtil.getEvent2());
        events.add(TestUtil.getEvent3());
        TestUtil.writeFile(events, file);
        return file;
    }
}
