package com.example.telegrambot.service;

import com.example.telegrambot.feign.WeatherClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramBotTest {
    @Mock
    private WeatherClient weatherClient;

    @Spy
    @InjectMocks
    private TelegramBot telegramBot;

    @Test
    public void testOnUpdateReceived_StartCommand() throws TelegramApiException {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(12345L);
        message.setChat(chat);
        message.setText("/start");
        update.setMessage(message);

        doReturn(new Message()).when(telegramBot).execute(any(SendMessage.class));

        telegramBot.onUpdateReceived(update);

        verify(telegramBot).execute(argThat((SendMessage sendMessage) ->
                sendMessage.getChatId().equals("12345") &&
                        sendMessage.getText().contains("Привет! Я бот для проверки погоды")));
    }

    @Test
    public void testOnUpdateReceived_WeatherCommand_Success() throws TelegramApiException {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(12345L);
        message.setChat(chat);
        message.setText("/weather Moscow,ru");
        update.setMessage(message);

        when(weatherClient.getWeather("Moscow,ru")).thenReturn("Погода в Moscow: Температура: 20.0°C");
        doReturn(new Message()).when(telegramBot).execute(any(SendMessage.class));

        telegramBot.onUpdateReceived(update);

        verify(weatherClient).getWeather("Moscow,ru");
        verify(telegramBot).execute(argThat((SendMessage sendMessage) ->
                sendMessage.getChatId().equals("12345") &&
                        sendMessage.getText().contains("Погода в Moscow")));
    }

    @Test
    public void testOnUpdateReceived_WeatherCommand_Failure() throws TelegramApiException {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(12345L);
        message.setChat(chat);
        message.setText("/weather InvalidCity");
        update.setMessage(message);

        when(weatherClient.getWeather("InvalidCity")).thenThrow(new RuntimeException("City not found"));
        doReturn(new Message()).when(telegramBot).execute(any(SendMessage.class));

        telegramBot.onUpdateReceived(update);

        verify(weatherClient).getWeather("InvalidCity");
        verify(telegramBot).execute(argThat((SendMessage sendMessage) ->
                sendMessage.getChatId().equals("12345") &&
                        sendMessage.getText().contains("Не удалось получить данные о погоде")));
    }

    @Test
    public void testOnUpdateReceived_EmptyWeatherCommand() throws TelegramApiException {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(12345L);
        message.setChat(chat);
        message.setText("/weather");
        update.setMessage(message);

        doReturn(new Message()).when(telegramBot).execute(any(SendMessage.class));

        telegramBot.onUpdateReceived(update);

        verify(telegramBot).execute(argThat((SendMessage sendMessage) ->
                sendMessage.getChatId().equals("12345") &&
                        sendMessage.getText().contains("Пожалуйста, укажите город")));
    }
}