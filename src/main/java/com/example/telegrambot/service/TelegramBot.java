package com.example.telegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return "7977528223:AAFXtCfJ1SDcyFCEh3wYV5EWzdIsMUHz5Ng";
    }

    @Override
    public String getBotUsername() {
        return "WeatherCourse_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (messageText.equals("/start") || messageText.equalsIgnoreCase("Начать") || messageText.equalsIgnoreCase("Привет")) {
            sendReplyMenu(chatId, "Привет! Я бот для проверки погоды. Используй команду /weather <город>, например, /weather Moscow");
        }
        else if (messageText.startsWith("/weather")) {
            String city = messageText.replace("/weather", "").trim();
            if (city.isEmpty()) {
                sendMessage(chatId, "Пожалуйста, укажите город, например, /weather Moscow");
            }
            else {
                sendMessage(chatId, ("Погода для " + city + " будет доступна скоро"));
            }
        }
        else if (messageText.equalsIgnoreCase("Погода")) {
            sendMessage(chatId, "Введите команду /weather <город>, например, /weather Moscow");
        }
        else {
            sendMessage(chatId, "Неизвестная команда. Используй /start, 'начать' или /weather <город>");
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendReplyMenu(long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        KeyboardRow row = new KeyboardRow();
        row.add("Привет");
        row.add("Погода");

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboardRowList);
        markup.setResizeKeyboard(true);

        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
