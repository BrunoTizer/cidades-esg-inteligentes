package br.com.fiap.esg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final long startTime = System.currentTimeMillis();

    @GetMapping
    public Map<String, Object> health() {
        return Map.of(
            "status", "ok",
            "timestamp", Instant.now().toString(),
            "uptime", (System.currentTimeMillis() - startTime) / 1000.0
        );
    }

    @GetMapping("/ready")
    public Map<String, Object> ready() {
        return Map.of(
            "status", "ready",
            "timestamp", Instant.now().toString()
        );
    }
}
