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

@Component
@Slf4j
public class CsvWriterImpl implements CsvWriter {

    private static final String HEADER = "Question;Answer\n";
    @Value("${csvPath}")
    private String csvFilePath;

    public void write(QuestionAnswer questionAnswer) throws IOException {
        boolean isNewFile = !new File(csvFilePath).exists();

        try (FileWriter csvWriter = new FileWriter(csvFilePath, true)) {
            if (isNewFile) {
                csvWriter.append(HEADER);
            }
            csvWriter.append(String.format("\"%s\";\"%s\"\n", questionAnswer.getQuestion(), questionAnswer.getAnswer()));
            csvWriter.flush();
        }catch (FileNotFoundException e) {
        	e.printStackTrace();
			log.error("CSV file not found");
        }
    }
}