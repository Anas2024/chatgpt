package net.izicap.anas.aitraho.chatgptproject.service;


import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.izicap.anas.aitraho.chatgptproject.model.ChatRequest;
import net.izicap.anas.aitraho.chatgptproject.model.ChatResponse;
import net.izicap.anas.aitraho.chatgptproject.model.QuestionAnswer;
import net.izicap.anas.aitraho.chatgptproject.repository.ChatGptRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService 
{
	
	//get apiUrl value from application.properties
	@Value("${apiUrl}")
    private String API_URL;
	
	//get apiKey value from application.properties
    @Value("${apiKey}")
    private String API_KEY;
    
    //get apiModel value from application.properties
    @Value("${apiModel}")
    private String MODEL;
    
    //get csvPath value from application.properties
    @Value("${csvPath}")
    private String CSV_FILE;
    
    // Declare a private instance variable for the ChatGptRepository
    private ChatGptRepository chatGptRepository;
    

    public QuestionAnswerServiceImpl(ChatGptRepository chatGptRepository) 
    {
		this.chatGptRepository = chatGptRepository;
	}


	public ChatResponse getAnswer(ChatRequest chatRequest) throws IOException 
	{
    	String question = chatRequest.getQuestion();
    	
        // Create request payload
        JSONObject payload = new JSONObject();
        payload.put("model", MODEL);
        payload.put("prompt", question);
        payload.put("temperature", 0.7);
        payload.put("max_tokens", 1024);
        
        // Send request to ChatGPT API
        String response = sendPostRequest(API_URL, payload.toString(), API_KEY);
        
        // Extract answer from response
        JSONObject jsonObject = new JSONObject(response);
        JSONArray choices = jsonObject.getJSONArray("choices");
        String answer = choices.getJSONObject(0).getString("text");
        ChatResponse chatResponse = new ChatResponse(answer);
        
        // Append question-answer pair to CSV file
        QuestionAnswer questionAnswer = new QuestionAnswer(question, answer);
        chatGptRepository.appendToCsv(questionAnswer);
        return chatResponse;

    }


    
    // Send HTTP POST request with payload and API key
    
    public String sendPostRequest(String urlString, String payload, String apiKey) throws IOException 
    {
    	// Create a URL object from the urlString parameter
        URL url = new URL(urlString);
        
        // Open an HttpURLConnection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // Set the request method to POST
        connection.setRequestMethod("POST");
        
        // Set the Content-Type header to application/json
        connection.setRequestProperty("Content-Type", "application/json");
        
        // Set the Authorization header to Bearer + apiKey
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        
        // Enable output on the connection
        connection.setDoOutput(true);
        
        // Write the payload to the connection's output stream
        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
        }
        
        // Read the response from the connection's input stream
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            
        // Loop through each line of the response and append it to the StringBuilder
            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }
         // Return the response as a String
            return response.toString();
        }
    }


}

