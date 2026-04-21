package br.com.fiap.esg.model;

public record Indicador(
    Long id,
    Long cidadeId,
    String cidade,
    String categoria,
    String nome,
    Double valor,
    String unidade,
    Double meta,
    Boolean atingido,
    Integer ano
) {}
