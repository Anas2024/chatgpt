package net.izicap.anas.aitraho.chatgptproject;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.izicap.anas.aitraho.chatgptproject.model.ChatRequest;
import net.izicap.anas.aitraho.chatgptproject.model.ChatResponse;
import net.izicap.anas.aitraho.chatgptproject.repository.ChatGptRepository;
import net.izicap.anas.aitraho.chatgptproject.service.QuestionAnswerService;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionAnswerService questionAnswerService;
    @MockBean
    private ChatGptRepository chatGptRepository;
    @Test
    public void getAnswerTest() throws Exception {
        ChatRequest chatRequest = new ChatRequest("Capital of France?");
        ChatResponse chatResponse = new ChatResponse("Paris");

        when(questionAnswerService.getAnswer(chatRequest)).thenReturn(chatResponse);

		mockMvc.perform(post("/getAnswer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(chatRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.answer").value("Paris"));
    }
    
 
    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(obj);
    }
}

