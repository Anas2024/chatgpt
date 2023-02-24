package net.izicap.anas.aitraho.chatgptproject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.izicap.anas.aitraho.chatgptproject.model.QuestionAnswer;


//The @Component annotation is used to indicate that this class is a Spring component, which can be managed by Spring's IoC container.
//The @Slf4j annotation is used to generate a logger for this class.
@Component
@Slf4j
public class CsvWriterImpl implements CsvWriter {

	// A constant string representing the header for the CSV file.
    private static final String HEADER = "Question;Answer\n";
    //inject the value of the "csvPath" property into the csvFilePath field.
    @Value("${csvPath}")
    private String csvFilePath;

    // This method is used to write the given QuestionAnswer object to a CSV file.
    // It throws an IOException if there is an error while writing to the file.
    public void write(QuestionAnswer questionAnswer) throws IOException {
    	// A boolean flag that indicates whether the file is new or not.
        boolean isNewFile = !new File(csvFilePath).exists();
        // A try-with-resources block is used to ensure that the FileWriter is closed after it is used.
        try (FileWriter csvWriter = new FileWriter(csvFilePath, true)) {
        	// If the file is new, the HEADER is written to the file.
            if (isNewFile) {
                csvWriter.append(HEADER);
            }
            // The question and answer values are written to the file in CSV format.
            csvWriter.append(String.format("\"%s\";\"%s\"\n", questionAnswer.getQuestion(), questionAnswer.getAnswer()));
            // The writer is flushed to ensure that all data is written to the file.
            csvWriter.flush();
        }catch (FileNotFoundException e) {
        	// If the file is not found, an error message is logged.
        	e.printStackTrace();
			log.error("CSV file not found");
        }
    }
}

