package pt.dcs.unbabel.bec;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pt.dcs.unbabel.bec.DateTimeUtil.*;

@Service
public class MovingAverageCalculator {

    List<Map<String, Object>> calculate(List<Map<String, Object>> events, int windowSize) {
        List<Map<String, Object>> dataPoints = new ArrayList<>();
        if (events != null && !events.isEmpty()) {
            events.sort((o1, o2) -> { // assuming the events may not be ordered by timestamp
                LocalDateTime o1Timestamp = DateTimeUtil.parse((String) o1.get("timestamp"));
                LocalDateTime o2Timestamp = DateTimeUtil.parse((String) o2.get("timestamp"));
                return o1Timestamp.compareTo(o2Timestamp);
            });
            LocalDateTime firstMinute = getFirstMinute(events);
            LocalDateTime lastMinute = getLastMinute(events);
            while (firstMinute.isBefore(lastMinute) || firstMinute.equals(lastMinute)) {
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("date", format(firstMinute));
                dataPoint.put("average_delivery_time", getAverageDeliveryTime(events, firstMinute, windowSize));
                dataPoints.add(dataPoint);
                firstMinute = firstMinute.plusMinutes(1);
            }
        }
        return dataPoints;
    }

    private LocalDateTime getFirstMinute(List<Map<String, Object>> events) {
        return roundFloorToTheMinute(parse((String) events.get(0).get("timestamp")));
    }

    private LocalDateTime getLastMinute(List<Map<String, Object>> events) {
        return roundCeilingToTheMinute(parse((String) events.get(events.size() - 1).get("timestamp")));
    }

    private double getAverageDeliveryTime(List<Map<String, Object>> events, LocalDateTime minute, int windowSize) {
        return events.stream()
                .filter(event -> isEventInsideTimeWindow(event, minute.minusMinutes(windowSize), minute))
                .mapToDouble(event -> ((Number) event.get("duration")).doubleValue())
                .average()
                .orElse(0);
    }

    private boolean isEventInsideTimeWindow(Map<String, Object> event, LocalDateTime begin, LocalDateTime end) {
        LocalDateTime timestamp = parse((String) event.get("timestamp"));
        return (timestamp.equals(begin) || timestamp.isAfter(begin)) && (timestamp.isBefore(end) || timestamp.equals(end));
    }
}
