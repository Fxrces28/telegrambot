# Микросервис для получения погодных данных от OpenWeatherMap и сохранения запросов в PostgreSQL.

## Требования
1. Java 17
2. Maven 3.9.x
3. PostgreSQL (база данных weatherdb)
4. Ключ API OpenWeatherMap (получите на https://openweathermap.org)

## Настройка. 
### Клонируйте репозиторий: 
```
git clone https://github.com/Fxrces28/telegrambotBackEnd.git
cd telegrambotBackEnd
```

Настройте PostgreSQL:
1. Создайте базу данных:
2. CREATE DATABASE weatherdb;
3. Обновите src/main/resources/application.yml:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/weatherdb
    username: postgres
    password: YOUR_DB_PASSWORD
openweathermap:
  api:
    key: YOUR_API_KEY
```

4. Замените YOUR_DB_PASSWORD и YOUR_API_KEY на ваши значения.
5. Убедитесь, что PostgreSQL запущен.

## Запуск

```mvn spring-boot:run```

## Swagger UI
Документация API: http://localhost:8081/swagger-ui.html

## Тестирование
### Запустите юнит-тесты:
```mvn test```
