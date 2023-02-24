package net.izicap.anas.aitraho.chatgptproject.controller;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.izicap.anas.aitraho.chatgptproject.model.ChatRequest;
import net.izicap.anas.aitraho.chatgptproject.model.ChatResponse;
import net.izicap.anas.aitraho.chatgptproject.service.QuestionAnswerService;

import java.io.IOException;


@Slf4j
@RestController
public class QuestionAnswerController {
	 private QuestionAnswerService questionAnswerService;
    
    public QuestionAnswerController(QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}

	@PostMapping("/getAnswer")
    public ChatResponse getAnswer( @RequestBody ChatRequest chatRequest) {

        try {
			return questionAnswerService.getAnswer(chatRequest);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error getting answer from chatGPT");
			return new ChatResponse("Error getting answer from chatGPT");
		}
    }

}

