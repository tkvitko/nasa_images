package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class NasaTgBot extends TelegramLongPollingBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;
    public static final String API_URL = "https://api.nasa.gov/planetary/apod";
    public static final String API_KEY = "3CbZKc0hub8ZasoiN4qzaDSOI6YPRqSdf0jhJfHO";
    public static final String NASA_URL = API_URL + "?api_key=" + API_KEY;

    public NasaTgBot(String botName, String botToken) {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String msg, long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(msg);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String answer = update.getMessage().getText();
            String[] separatedAnswer = answer.split(" ");
            String action = separatedAnswer[0];

            switch (action) {
                case "/help":
                    sendMessage("Я бот, присылающий картинку дня. напиши /start", chatId);
                    break;
                case "/start":
                case "/image":
                    String url = Utils.getUrl(NASA_URL);
                    sendMessage(url, chatId);
                    break;
                case "/date":
                    String date = separatedAnswer[1];
                    url = Utils.getUrl(NASA_URL + "&date=" + date);
                    sendMessage(url, chatId);
                    break;
                default:
                    sendMessage("Я не понимаю чего ты хочешь", chatId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
