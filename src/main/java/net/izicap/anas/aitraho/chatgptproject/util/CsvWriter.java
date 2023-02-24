package net.izicap.anas.aitraho.chatgptproject.util;

import java.io.IOException;

import net.izicap.anas.aitraho.chatgptproject.model.QuestionAnswer;

public interface CsvWriter {
	
	void write(QuestionAnswer questionAnswer) throws IOException;

}
