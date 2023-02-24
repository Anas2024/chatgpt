package net.izicap.anas.aitraho.chatgptproject.controller;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.izicap.anas.aitraho.chatgptproject.model.ChatRequest;
import net.izicap.anas.aitraho.chatgptproject.model.ChatResponse;
import net.izicap.anas.aitraho.chatgptproject.service.QuestionAnswerService;

import java.io.IOException;


//Declare the class as a REST controller and add the @Slf4j annotation for logging
@Slf4j
@RestController
public class QuestionAnswerController 
{
	// Declare a private instance variable for the QuestionAnswerService
	 private QuestionAnswerService questionAnswerService;
    
	// Constructor that takes a QuestionAnswerService object as a parameter and assigns it to the instance variable
    public QuestionAnswerController(QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}
    
    // Define a POST endpoint with the mapping "/getAnswer" that takes a ChatRequest object in the request body
	@PostMapping("/getAnswer")
    public ChatResponse getAnswer( @RequestBody ChatRequest chatRequest) {

        try {
        	// Call the getAnswer method on the QuestionAnswerService object, passing in the ChatRequest object
			return questionAnswerService.getAnswer(chatRequest);
		} catch (IOException e) {
			// Log an error message if an IOException is thrown and return a ChatResponse object with an error message
			e.printStackTrace();
			log.error("Error getting answer from chatGPT");
			return new ChatResponse("Error getting answer from chatGPT");
		}
    }

}
