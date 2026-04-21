package br.com.fiap.esg.controller;

import br.com.fiap.esg.model.Cidade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades")
public class CidadesController {

    private static final List<Cidade> CIDADES = List.of(
        new Cidade(1L, "São Paulo", "SP", 12325232L, "Sudeste"),
        new Cidade(2L, "Curitiba", "PR", 1948626L, "Sul"),
        new Cidade(3L, "Florianópolis", "SC", 537211L, "Sul"),
        new Cidade(4L, "Porto Alegre", "RS", 1332570L, "Sul"),
        new Cidade(5L, "Belo Horizonte", "MG", 2530701L, "Sudeste")
    );

    @GetMapping
    public List<Cidade> listar(
            @RequestParam(required = false) String regiao,
            @RequestParam(required = false) String estado) {
        return CIDADES.stream()
            .filter(c -> regiao == null || c.regiao().equalsIgnoreCase(regiao))
            .filter(c -> estado == null || c.estado().equalsIgnoreCase(estado))
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
        return CIDADES.stream()
            .filter(c -> c.id().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
