package br.com.fiap.esg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @Value("${app.version:1.0.0}")
    private String version;

    @Value("${SPRING_PROFILES_ACTIVE:development}")
    private String environment;

    @GetMapping("/")
    public Map<String, String> info() {
        return Map.of(
            "nome", "Cidades ESG Inteligentes",
            "versao", version,
            "ambiente", environment,
            "descricao", "API REST para monitoramento de indicadores ESG em cidades brasileiras"
        );
    }
}
