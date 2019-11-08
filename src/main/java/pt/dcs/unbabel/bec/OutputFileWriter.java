package pt.dcs.unbabel.bec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Service
public class OutputFileWriter {

    private static final Logger logger = LoggerFactory.getLogger(OutputFileWriter.class);

    private final ObjectMapper jsonObjectMapper;

    public OutputFileWriter(ObjectMapper jsonObjectMapper) {
        this.jsonObjectMapper = jsonObjectMapper;
    }

    void export(List<Map<String, Object>> dataPoints, File file) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(file.toURI()), StandardOpenOption.CREATE)) {
            for (Map<String, Object> movingAverage : dataPoints) {
                String json = jsonObjectMapper.writeValueAsString(movingAverage);
                logger.debug(json);
                bw.write(json);
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error("Error writing to output file [{}]", file.getAbsolutePath(), e);
        }
    }
}
