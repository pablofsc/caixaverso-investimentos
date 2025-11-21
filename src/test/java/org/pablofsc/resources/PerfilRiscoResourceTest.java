package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.response.PerfilRiscoResponse;
import org.pablofsc.service.PerfilRiscoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class PerfilRiscoResourceTest {

    @Inject
    PerfilRiscoResource resource;

    @Mock
    PerfilRiscoService perfilRiscoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = perfilRiscoService;
    }

    @Test
    void testObterPerfilRisco() {
        // Arrange
        Long clienteId = 1L;
        PerfilRiscoResponse expectedResponse = new PerfilRiscoResponse(1L, "CONSERVADOR", 10, "Perfil conservador");
        when(perfilRiscoService.obterPerfilRisco(anyLong())).thenReturn(expectedResponse);

        // Act
        Response response = resource.obterPerfilRisco(clienteId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
    }
}