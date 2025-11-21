package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.model.SimulacaoHistorico;
import org.pablofsc.domain.model.SimulacaoPorProdutoDia;
import org.pablofsc.service.SimulacaoHistoricoService;
import org.pablofsc.service.SimulacaoPorProdutoDiaService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class SimulacaoHistoricoResourceTest {

    @Inject
    SimulacaoHistoricoResource resource;

    @Mock
    SimulacaoHistoricoService simulacaoHistoricoService;

    @Mock
    SimulacaoPorProdutoDiaService simulacaoPorProdutoDiaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = simulacaoHistoricoService;
        resource.produtoDiaService = simulacaoPorProdutoDiaService;
    }

    @Test
    void testListarSimulacoes() {
        // Arrange
        List<SimulacaoHistorico> expectedSimulacoes = List.of(new SimulacaoHistorico());
        when(simulacaoHistoricoService.listarSimulacoes()).thenReturn(expectedSimulacoes);

        // Act
        Response response = resource.listarSimulacoes();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedSimulacoes, response.getEntity());
    }

    @Test
    void testListarSimulacoesPorProdutoDia() {
        // Arrange
        List<SimulacaoPorProdutoDia> expectedSimulacoes = List.of(new SimulacaoPorProdutoDia());
        when(simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia()).thenReturn(expectedSimulacoes);

        // Act
        Response response = resource.listarSimulacoesPorProdutoDia();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedSimulacoes, response.getEntity());
    }
}