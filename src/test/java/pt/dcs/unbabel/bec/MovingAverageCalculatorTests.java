package pt.dcs.unbabel.bec;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovingAverageCalculatorTests {

    private MovingAverageCalculator calculator;

    @BeforeAll
    void setup() {
        calculator = new MovingAverageCalculator();
    }

    @Test
    void calculate_withEventsOrderedByTimestamp() {

        List<Map<String, Object>> events = new ArrayList<>();
        events.add(TestUtil.getEvent1());
        events.add(TestUtil.getEvent2());
        events.add(TestUtil.getEvent3());

        List<Map<String, Object>> dataPoints = calculator.calculate(events, 10);

        TestUtil.makeAssertions(dataPoints);
    }

    @Test
    void calculate_withEventsNotOrderedByTimestamp() {

        List<Map<String, Object>> events = new ArrayList<>();
        events.add(TestUtil.getEvent3());
        events.add(TestUtil.getEvent1());
        events.add(TestUtil.getEvent2());

        List<Map<String, Object>> dataPoints = calculator.calculate(events, 10);

        TestUtil.makeAssertions(dataPoints);
    }
}
