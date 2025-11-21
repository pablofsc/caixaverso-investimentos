package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.response.TelemetriaResponse;
import org.pablofsc.service.TelemetriaService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class TelemetriaResourceTest {

    @Inject
    TelemetriaResource resource;

    @Mock
    TelemetriaService telemetriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = telemetriaService;
    }

    @Test
    void testObterTelemetrias() {
        // Arrange
        TelemetriaResponse expectedResponse = new TelemetriaResponse();
        when(telemetriaService.obterTelemetrias()).thenReturn(expectedResponse);

        // Act
        Response response = resource.obterTelemetrias();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
    }
}