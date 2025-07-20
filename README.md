# Микросервис для обработки запросов пользователей через Telegram-бота. Запрашивает погодные данные у микросервиса telegrambotBackEnd.

## Требования
1. Java 17
2. Maven 3.9.x
3. Telegram Bot Token (получите через @BotFather)
4. Запущенный микросервис telegrambotBackEnd на http://localhost:8081

## Настройка
1. Клонируйте репозиторий:
```
git clone https://github.com/Fxrces28/telegrambot.git
cd telegrambot
```

3. Обновите src/main/java/com.example.telegrambot/service/TelegramBot:
```java
      public String getBotToken() {
              return "YOUR_BOT_TOKEN";
          }
```
Замените YOUR_BOT_TOKEN на токен, полученный от @BotFather.

4. Убедитесь, что микросервис telegrambotBackEnd запущен.

## Запуск
``` mvn spring-boot:run ```
Бот начнёт обрабатывать команды через Telegram.
    
## Запустите тесты: 
``` mvn test ```

## Использование
Отправьте команду /weather <город> (например, /weather Moscow) в Telegram-боте. Бот запросит погоду у telegrambotBackEnd и вернёт ответ.      

