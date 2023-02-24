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
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
	
	@Value("${apiUrl}")
    private String API_URL;
    @Value("${apiKey}")
    private String API_KEY;
    @Value("${apiModel}")
    private String MODEL;
    @Value("${csvPath}")
    private String CSV_FILE;
    
    private ChatGptRepository chatGptRepository;
    

    public QuestionAnswerServiceImpl(ChatGptRepository chatGptRepository) {
		this.chatGptRepository = chatGptRepository;
	}


	public ChatResponse getAnswer(ChatRequest chatRequest) throws IOException {
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
    
    public String sendPostRequest(String urlString, String payload, String apiKey) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }

            return response.toString();
        }
    }

}

