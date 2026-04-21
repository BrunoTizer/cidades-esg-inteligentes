package br.com.fiap.esg.model;

import java.util.Map;

public record ResumoGeral(
    int totalIndicadores,
    long metasAtingidas,
    long metasPendentes,
    double taxaSucesso,
    Map<String, Long> categorias
) {}
