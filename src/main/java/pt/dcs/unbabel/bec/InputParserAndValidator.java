package pt.dcs.unbabel.bec;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static java.lang.String.format;

@Service
public class InputParserAndValidator {

    Inputs parseAndValidate(ApplicationArguments args) throws InputValidationException {
        File inFile = parseAndValidateInputFile(args);
        File outFile = parseAndValidateOutputFile(args);
        int windowSize = parseAndValidateWindowSize(args);
        return new Inputs(inFile, outFile, windowSize);
    }

    private File parseAndValidateInputFile(ApplicationArguments args) throws InputValidationException {
        List<String> values = args.getOptionValues("input_file");
        if (values == null || values.isEmpty()) {
            throw new InputValidationException("No input file was provided");
        }
        if (values.size() != 1) {
            throw new InputValidationException("More than 1 input file was provided");
        }
        File file = new File(values.get(0));
        if (!file.exists() || !file.canRead()) {
            throw new InputValidationException(format("Input file [%s] does not exist or cannot be read", file.getAbsolutePath()));
        }
        return file;
    }

    private File parseAndValidateOutputFile(ApplicationArguments args) throws InputValidationException {
        File file = new File("out.json");
        List<String> values = args.getOptionValues("output_file");
        if (values != null) {
            if (values.isEmpty()) {
                throw new InputValidationException("No output file was provided");
            }
            if (values.size() != 1) {
                throw new InputValidationException("More than 1 output file was provided");
            }
            file = new File(values.get(0));
        }
        return file;
    }

    private int parseAndValidateWindowSize(ApplicationArguments args) throws InputValidationException {
        List<String> values = args.getOptionValues("window_size");
        if (values == null || values.isEmpty()) {
            throw new InputValidationException("No window size was provided");
        }
        if (values.size() != 1) {
            throw new InputValidationException("More than 1 window size was provided");
        }
        int windowSize;
        try {
            windowSize = Integer.parseInt(values.get(0));
        } catch (NumberFormatException e) {
            throw new InputValidationException("Provided window size is not a positive integer value");
        }
        if (windowSize < 0) {
            throw new InputValidationException("Provided window size is not a positive integer value");
        }
        return windowSize;
    }
}