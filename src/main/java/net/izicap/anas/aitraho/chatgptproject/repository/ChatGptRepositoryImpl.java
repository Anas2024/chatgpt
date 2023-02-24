package net.izicap.anas.aitraho.chatgptproject.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import net.izicap.anas.aitraho.chatgptproject.model.QuestionAnswer;
import net.izicap.anas.aitraho.chatgptproject.util.CsvWriter;

@Repository
public class ChatGptRepositoryImpl implements ChatGptRepository{
	private CsvWriter csvWriter;
	

	public ChatGptRepositoryImpl(CsvWriter csvWriter) {
		this.csvWriter = csvWriter;
	}


	@Override
	public synchronized void appendToCsv(QuestionAnswer questionAnswer) throws IOException {
		csvWriter.write(questionAnswer);
		
	}

}
