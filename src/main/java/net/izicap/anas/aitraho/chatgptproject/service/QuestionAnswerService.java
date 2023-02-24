package net.izicap.anas.aitraho.chatgptproject.service;

import java.io.IOException;

import net.izicap.anas.aitraho.chatgptproject.model.ChatRequest;
import net.izicap.anas.aitraho.chatgptproject.model.ChatResponse;

public interface QuestionAnswerService
{
	ChatResponse getAnswer(ChatRequest question) throws IOException;
	

}
