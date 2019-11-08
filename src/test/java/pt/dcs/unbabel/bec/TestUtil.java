package pt.dcs.unbabel.bec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface TestUtil {

    Logger logger = LoggerFactory.getLogger(TestUtil.class);

    ObjectMapper jsonMapper = JsonMapper.builder().build();

    static Map<String, Object> getEvent1() {
        Map<String, Object> event = new HashMap<>();
        event.put("timestamp", "2018-12-26 18:11:08.509654");
        event.put("duration", 20);
        return event;
    }

    static Map<String, Object> getEvent2() {
        Map<String, Object> event = new HashMap<>();
        event.put("timestamp", "2018-12-26 18:15:19.903159");
        event.put("duration", 31);
        return event;
    }

    static Map<String, Object> getEvent3() {
        Map<String, Object> event = new HashMap<>();
        event.put("timestamp", "2018-12-26 18:23:19.903159");
        event.put("duration", 54);
        return event;
    }

    static List<Map<String, Object>> parseFile(File file) {
        try {
            return Files
                    .lines(Paths.get(file.toURI()))
                    .map(TestUtil::getJsonObject)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error reading file", e);
            return new ArrayList<>();
        }
    }

    static void writeFile(List<Map<String, Object>> jsonObjects, File file) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(file.toURI()), StandardOpenOption.CREATE)) {
            for (Map<String, Object> movingAverage : jsonObjects) {
                String json = jsonMapper.writeValueAsString(movingAverage);
                logger.debug(json);
                bw.write(json);
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error("Error writing to file [{}]", file.getAbsolutePath(), e);
        }
    }

    @SuppressWarnings("unchecked")
    static Optional<Map<String, Object>> getJsonObject(String line) {
        try {
            return Optional.of(jsonMapper.readValue(line, Map.class));
        } catch (JsonProcessingException e) {
            logger.error("Error transforming line into json object", e);
            return Optional.empty();
        }
    }

    static void makeAssertions(List<Map<String, Object>> dataPoints) {
        assertEquals(14, dataPoints.size());
        assertEquals("2018-12-26 18:11:00.000000", dataPoints.get(0).get("date"));
        assertEquals(0.0, dataPoints.get(0).get("average_delivery_time"));
        assertEquals("2018-12-26 18:12:00.000000", dataPoints.get(1).get("date"));
        assertEquals(20.0, dataPoints.get(1).get("average_delivery_time"));
        assertEquals("2018-12-26 18:13:00.000000", dataPoints.get(2).get("date"));
        assertEquals(20.0, dataPoints.get(2).get("average_delivery_time"));
        assertEquals("2018-12-26 18:14:00.000000", dataPoints.get(3).get("date"));
        assertEquals(20.0, dataPoints.get(3).get("average_delivery_time"));
        assertEquals("2018-12-26 18:15:00.000000", dataPoints.get(4).get("date"));
        assertEquals(20.0, dataPoints.get(4).get("average_delivery_time"));
        assertEquals("2018-12-26 18:16:00.000000", dataPoints.get(5).get("date"));
        assertEquals(25.5, dataPoints.get(5).get("average_delivery_time"));
        assertEquals("2018-12-26 18:17:00.000000", dataPoints.get(6).get("date"));
        assertEquals(25.5, dataPoints.get(6).get("average_delivery_time"));
        assertEquals("2018-12-26 18:18:00.000000", dataPoints.get(7).get("date"));
        assertEquals(25.5, dataPoints.get(7).get("average_delivery_time"));
        assertEquals("2018-12-26 18:19:00.000000", dataPoints.get(8).get("date"));
        assertEquals(25.5, dataPoints.get(8).get("average_delivery_time"));
        assertEquals("2018-12-26 18:20:00.000000", dataPoints.get(9).get("date"));
        assertEquals(25.5, dataPoints.get(9).get("average_delivery_time"));
        assertEquals("2018-12-26 18:21:00.000000", dataPoints.get(10).get("date"));
        assertEquals(25.5, dataPoints.get(10).get("average_delivery_time"));
        assertEquals("2018-12-26 18:22:00.000000", dataPoints.get(11).get("date"));
        assertEquals(31.0, dataPoints.get(11).get("average_delivery_time"));
        assertEquals("2018-12-26 18:23:00.000000", dataPoints.get(12).get("date"));
        assertEquals(31.0, dataPoints.get(12).get("average_delivery_time"));
        assertEquals("2018-12-26 18:24:00.000000", dataPoints.get(13).get("date"));
        assertEquals(42.5, dataPoints.get(13).get("average_delivery_time"));
    }
}
