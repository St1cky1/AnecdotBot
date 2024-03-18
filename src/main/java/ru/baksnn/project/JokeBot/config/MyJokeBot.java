package ru.baksnn.project.JokeBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.baksnn.project.JokeBot.model.JokesModel;
import ru.baksnn.project.JokeBot.repository.JokesRepository;


import java.util.List;
import java.util.Random;

@Data
@Component
@Service
public class MyJokeBot extends TelegramLongPollingBot {

    private final JokesRepository jokesRepository;

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String token;
    @Override
    public String getBotUsername() {
        return botName;
    }
    @Override
    public String getBotToken() {
        return token;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("Показать шутку ✅".equals(messageText) || "/joke".equals(messageText)) {
                sendJokesWithButton(chatId);
            } else if ("/start".equals(messageText)) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                sendMessage(chatId, "Такой команды не существует. Введите /joke для получения шутки");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет " + name + ", приятно познакомиться! \nНажимай кнопку или вводи команду /joke для получения шутки";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendJokesWithButton(long chatId) {
        List<JokesModel> jokes = jokesRepository.findAll();

        if (jokes.isEmpty()) {
            sendMessage(chatId, "No jokes available.");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(jokes.size());
            JokesModel randomJoke = jokes.get(randomIndex);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(randomJoke.getJoke());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
