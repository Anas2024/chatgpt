package net.izicap.anas.aitraho.chatgptproject.repository;

import java.io.IOException;


import net.izicap.anas.aitraho.chatgptproject.model.QuestionAnswer;


public interface ChatGptRepository {
	void appendToCsv(QuestionAnswer questionAnswer) throws IOException;

}
