package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.model.Investimento;
import org.pablofsc.service.InvestimentoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class InvestimentoResourceTest {

    @Inject
    InvestimentoResource resource;

    @Mock
    InvestimentoService investimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = investimentoService;
    }

    @Test
    void testObterInvestimentosPorCliente() {
        // Arrange
        Long clienteId = 1L;
        List<Investimento> expectedInvestimentos = List.of(new Investimento());
        when(investimentoService.obterInvestimentosPorCliente(anyLong())).thenReturn(expectedInvestimentos);

        // Act
        Response response = resource.obterInvestimentosPorCliente(clienteId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedInvestimentos, response.getEntity());
    }
}