package telegram.bot.superGroupManagerbot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {
	public static void main(String[] args) {
		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			MyTelegramBot allExampleBot = new MyTelegramBot();
			botsApi.registerBot(allExampleBot);
		} catch (TelegramApiException e) {
			System.out.println("EXCEPTION");
			main(args);
		}
	}

}
