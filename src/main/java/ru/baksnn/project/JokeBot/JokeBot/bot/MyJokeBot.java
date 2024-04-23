package ru.baksnn.project.JokeBot.JokeBot.bot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.baksnn.project.JokeBot.model.Jokes;
import ru.baksnn.project.JokeBot.repository.JokesRepository;
import ru.baksnn.project.JokeBot.service.UsersService;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Service
public class MyJokeBot extends TelegramLongPollingBot {

    private final JokesRepository jokesRepository;

    private final UsersService usersService;

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
                Jokes randomJoke = sendJokesWithButton(chatId);
                usersService.logJokeCall(chatId, randomJoke.getId(), randomJoke.getJoke()); // log the joke call
            } else if ("/start".equals(messageText)) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                sendMessage(chatId, "Такой команды не существует. Введите /joke для получения шутки");
            }
        }
    }

    public void startCommandReceived(long chatId, String name) {
        String answer = "Привет " + name + ", приятно познакомиться! \nНажимай кнопку или вводи команду /joke для получения шутки";
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Jokes sendJokesWithButton(long chatId) {
        List<Jokes> jokes = jokesRepository.findAll();

        if (jokes.isEmpty()) {
            sendMessage(chatId, "Шутки не существует");
            return null;
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(jokes.size());
            Jokes randomJoke = jokes.get(randomIndex);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(randomJoke.getJoke());

            //создание replykeyboardmarkup(кнопка снизу)
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setResizeKeyboard(true);
            keyboardMarkup.setOneTimeKeyboard(false);

            KeyboardRow row = new KeyboardRow();
            row.add("Показать шутку ✅");

            List<KeyboardRow> keyboard = new ArrayList<>();
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);

            message.setReplyMarkup(keyboardMarkup);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            return randomJoke;
        }
    }

}
