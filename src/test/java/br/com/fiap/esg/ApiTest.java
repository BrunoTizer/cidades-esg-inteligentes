package br.com.fiap.esg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthRetorna200ComStatusOk() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("ok"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.uptime").exists());
    }

    @Test
    void healthReadyRetorna200() throws Exception {
        mockMvc.perform(get("/api/health/ready"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("ready"));
    }

    @Test
    void cidadesRetornaListaCompleta() throws Exception {
        mockMvc.perform(get("/api/cidades"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void cidadePorIdRetornaCidadeCorreta() throws Exception {
        mockMvc.perform(get("/api/cidades/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("São Paulo"))
            .andExpect(jsonPath("$.estado").value("SP"));
    }

    @Test
    void cidadePorIdInexistenteRetorna404() throws Exception {
        mockMvc.perform(get("/api/cidades/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void cidadesFiltradaPorEstadoRetornaCorretamente() throws Exception {
        mockMvc.perform(get("/api/cidades").param("estado", "PR"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nome").value("Curitiba"));
    }

    @Test
    void indicadoresRetornaListaCompleta() throws Exception {
        mockMvc.perform(get("/api/indicadores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void indicadoresFiltradosPorCategoria() throws Exception {
        mockMvc.perform(get("/api/indicadores").param("categoria", "Ambiental"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void resumoGeralRetornaEstatisticasCompletas() throws Exception {
        mockMvc.perform(get("/api/indicadores/resumo/geral"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalIndicadores").value(5))
            .andExpect(jsonPath("$.metasAtingidas").exists())
            .andExpect(jsonPath("$.metasPendentes").exists())
            .andExpect(jsonPath("$.taxaSucesso").exists())
            .andExpect(jsonPath("$.categorias").exists());
    }

    @Test
    void indicadorPorIdInexistenteRetorna404() throws Exception {
        mockMvc.perform(get("/api/indicadores/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void rootRetornaInformacoesDoProjeto() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").exists());
    }
}
