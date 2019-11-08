package pt.dcs.unbabel.bec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InputFileParser {

    private static final Logger logger = LoggerFactory.getLogger(InputFileParser.class);

    private final ObjectMapper jsonObjectMapper;

    public InputFileParser(ObjectMapper jsonObjectMapper) {
        this.jsonObjectMapper = jsonObjectMapper;
    }

    List<Map<String, Object>> parse(File file) {
        try {
            return Files
                    .lines(Paths.get(file.toURI()))
                    .map(this::getEvent)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error reading file", e);
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    private Optional<Map<String, Object>> getEvent(String line) {
        try {
            return Optional.of(jsonObjectMapper.readValue(line, Map.class));
        } catch (JsonProcessingException e) {
            logger.error("Error transforming line into json object", e);
            return Optional.empty();
        }
    }
}
