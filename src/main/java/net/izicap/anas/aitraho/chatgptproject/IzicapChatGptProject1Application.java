package net.izicap.anas.aitraho.chatgptproject;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class IzicapChatGptProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(IzicapChatGptProject1Application.class, args);
	}
	/*
	 * just for testing localy 
	@Bean
	public ApplicationRunner applicationRunner(QuestionAnswerService questionAnswerService) {
		return args -> {
			CompletableFuture.runAsync(() -> {
				try (Scanner sc = new Scanner(System.in)) {
					while (true) {
						System.out.println("-------------------------------------------------------");
						System.out.println("Hello i am chatGPT :) Ask me any question you want ?!");
						System.out.println("-------------------------------------------------------");
						String question = sc.nextLine();
						System.out.println("-----------------------Begin ANSWER--------------------------------");
						ChatRequest chatRequest = new ChatRequest(question);
						String response = questionAnswerService.getAnswer(chatRequest).getAnswer();
						System.out.println(response);
						System.out.println("-----------------------END ANSWER-----------------------------------");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		};
	}*/

}
