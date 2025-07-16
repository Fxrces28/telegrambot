package com.example.telegrambot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="weather-service", url="http://localhost:8081")
public interface WeatherClient {
    @GetMapping("/api/weather/{city}")
    String getWeather(@PathVariable("city") String city);
}
