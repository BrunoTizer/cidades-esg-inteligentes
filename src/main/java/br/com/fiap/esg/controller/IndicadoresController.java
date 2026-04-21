package br.com.fiap.esg.controller;

import br.com.fiap.esg.model.Indicador;
import br.com.fiap.esg.model.ResumoGeral;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/indicadores")
public class IndicadoresController {

    private static final List<Indicador> INDICADORES = List.of(
        new Indicador(1L, 1L, "São Paulo", "Ambiental", "Área Verde por Habitante", 7.5, "m²/hab", 12.0, false, 2024),
        new Indicador(2L, 1L, "São Paulo", "Social", "Índice de Educação", 0.82, "índice", 0.90, false, 2024),
        new Indicador(3L, 2L, "Curitiba", "Ambiental", "Coleta Seletiva", 85.0, "%", 80.0, true, 2024),
        new Indicador(4L, 2L, "Curitiba", "Governança", "Transparência Fiscal", 92.0, "pontos", 85.0, true, 2024),
        new Indicador(5L, 3L, "Florianópolis", "Social", "Acesso à Saúde", 78.5, "%", 90.0, false, 2024)
    );

    @GetMapping
    public List<Indicador> listar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Long cidadeId,
            @RequestParam(required = false) Boolean atingido) {
        return INDICADORES.stream()
            .filter(i -> categoria == null || i.categoria().equalsIgnoreCase(categoria))
            .filter(i -> cidadeId == null || i.cidadeId().equals(cidadeId))
            .filter(i -> atingido == null || i.atingido().equals(atingido))
            .toList();
    }

    @GetMapping("/resumo/geral")
    public ResumoGeral resumoGeral() {
        long atingidas = INDICADORES.stream().filter(Indicador::atingido).count();
        long pendentes = INDICADORES.size() - atingidas;
        double taxa = (double) atingidas / INDICADORES.size() * 100;
        Map<String, Long> categorias = INDICADORES.stream()
            .collect(Collectors.groupingBy(Indicador::categoria, Collectors.counting()));
        return new ResumoGeral(INDICADORES.size(), atingidas, pendentes, taxa, categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Indicador> buscarPorId(@PathVariable Long id) {
        return INDICADORES.stream()
            .filter(i -> i.id().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
