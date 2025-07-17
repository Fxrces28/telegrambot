package com.example.telegrambot.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
@Tag(name = "Test API", description = "Тестовый API для проверки работоспособности микросервиса А")
public class TestController {
    @Operation(summary = "Проверка работоспособнсти", description = "Возвращает сообщение, подтверждающее, что микросервис А работает")
    @ApiResponse(responseCode = "200", description = "Микросервис работает")
    @GetMapping
    public String test() {
        return "Микросервис А (Телеграмм бот) работает!";
    }
}
