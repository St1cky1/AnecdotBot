package ru.baksnn.project.JokeBot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.baksnn.project.JokeBot.Config.BotConfig;

@SpringBootApplication
public class JokeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokeBotApplication.class, args);
	}

	@Bean
	public CommandLineRunner registerTelegramBot(BotConfig botConfig) {
		return args -> {
			try {
				TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
				botsApi.registerBot(botConfig);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		};
	}
}
