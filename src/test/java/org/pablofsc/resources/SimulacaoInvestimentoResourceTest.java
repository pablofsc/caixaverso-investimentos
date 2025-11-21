package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ParametroInvalidoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.service.SimulacaoInvestimentoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class SimulacaoInvestimentoResourceTest {

    @Inject
    SimulacaoInvestimentoResource resource;

    @Mock
    SimulacaoInvestimentoService simulacaoInvestimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = simulacaoInvestimentoService;
    }

    @Test
    void testSimularInvestimentoSuccess() {
        // Arrange
        SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();
        SimulacaoInvestimentoResponse expectedResponse = new SimulacaoInvestimentoResponse();
        when(simulacaoInvestimentoService.simularInvestimento(any(SimulacaoInvestimentoRequest.class))).thenReturn(expectedResponse);

        // Act
        Response response = resource.simularInvestimento(request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
    }

    @Test
    void testSimularInvestimentoClienteNaoEncontrado() {
        // Arrange
        SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();
        when(simulacaoInvestimentoService.simularInvestimento(any(SimulacaoInvestimentoRequest.class))).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        // Act
        Response response = resource.simularInvestimento(request);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Cliente não encontrado", ((ErrorResponse) response.getEntity()).getMensagem());
    }

    @Test
    void testSimularInvestimentoProdutoNaoEncontrado() {
        // Arrange
        SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();
        when(simulacaoInvestimentoService.simularInvestimento(any(SimulacaoInvestimentoRequest.class))).thenThrow(new ProdutoNaoEncontradoException("Produto não encontrado", null));

        // Act
        Response response = resource.simularInvestimento(request);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Produto não encontrado", ((ErrorResponse) response.getEntity()).getMensagem());
    }

    @Test
    void testSimularInvestimentoParametroInvalido() {
        // Arrange
        SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();
        when(simulacaoInvestimentoService.simularInvestimento(any(SimulacaoInvestimentoRequest.class))).thenThrow(new ParametroInvalidoException("Parâmetro inválido"));

        // Act
        Response response = resource.simularInvestimento(request);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Parâmetro inválido", ((ErrorResponse) response.getEntity()).getMensagem());
    }
}
