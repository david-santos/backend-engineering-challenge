package pt.dcs.unbabel.bec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.*;

import static pt.dcs.unbabel.bec.DateTimeUtil.format;

public class RandomInputGenerator {

    private static final int WINDOW_SIZE = 10;

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = JsonMapper.builder().build();
        List<Map<String, Object>> events = new ArrayList<>();
        for (int i = 0; i < WINDOW_SIZE; i++) {
            Map<String, Object> event = new HashMap<>();
            event.put("timestamp", getTimestamp());
            event.put("translation_id", getId());
            event.put("source_language", getSourceLanguage());
            event.put("target_language", getTargetLanguage());
            event.put("client_name", getClientName());
            event.put("event_name", getEventName());
            event.put("duration", getDuration());
            event.put("nr_words", getNrWords());
            events.add(event);
        }
//        events.sort((o1, o2) -> {
//            LocalDateTime o1Timestamp = (LocalDateTime) o1.get("timestamp");
//            LocalDateTime o2Timestamp = (LocalDateTime) o2.get("timestamp");
//            return o1Timestamp.compareTo(o2Timestamp);
//        });
        for (Map<String, Object> event : events) {
            LocalDateTime timestamp = (LocalDateTime) event.get("timestamp");
            event.put("timestamp", format(timestamp));
            System.out.println(mapper.writeValueAsString(event));
        }
    }

    private static LocalDateTime getTimestamp() {
        return LocalDateTime.now().minusMinutes(getRandomIntegerBetween0AndMax(WINDOW_SIZE + 10));
    }

    private static String getId() {
        return UUID.randomUUID().toString();
    }

    private static String getSourceLanguage() {
        return "en";
    }

    private static String getTargetLanguage() {
        return "fr";
    }

    private static String getClientName() {
        return "easyjet";
    }

    private static String getEventName() {
        return "translation_delivered";
    }

    private static int getDuration() {
        return getRandomIntegerBetween0AndMax(100);
    }

    private static int getNrWords() {
        return getRandomIntegerBetween0AndMax(250);
    }

    private static int getRandomIntegerBetween0AndMax(int max) {
        return (int) (Math.random() * (max + 1));
    }
}
