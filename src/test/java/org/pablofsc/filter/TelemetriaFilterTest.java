package org.pablofsc.filter;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.service.TelemetriaService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

@QuarkusTest
class TelemetriaFilterTest {

    @Mock
    TelemetriaService telemetriaService;

    TelemetriaFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new TelemetriaFilter();
        filter.telemetriaService = telemetriaService;
    }

    @Test
    void testFilterSkipsTelemetriaPath() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/telemetria");

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService, never()).registrarTelemetria(anyString(), anyLong());
    }

    @Test
    void testFilterSkipsTelemetriaSubPath() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/telemetria/stats");

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService, never()).registrarTelemetria(anyString(), anyLong());
    }

    @Test
    void testFilterProcessesNormalPath() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/investimentos");
        when(requestContext.getProperty("tempoInicio")).thenReturn(System.currentTimeMillis() - 100L);

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService).registrarTelemetria(eq("investimentos"), anyLong());
    }

    @Test
    void testFilterProcessesPathWithParams() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/investimentos/123");
        when(requestContext.getProperty("tempoInicio")).thenReturn(System.currentTimeMillis() - 200L);

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService).registrarTelemetria(eq("investimentos"), anyLong());
    }

    @Test
    void testFilterProcessesSimulacoesPorProdutoDia() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/simulacoes/por-produto-dia");
        when(requestContext.getProperty("tempoInicio")).thenReturn(System.currentTimeMillis() - 150L);

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService).registrarTelemetria(eq("simulacoes/por-produto-dia"), anyLong());
    }

    @Test
    void testFilterSkipsWhenTempoInicioIsNull() throws IOException {
        // Arrange
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);
        UriInfo uriInfo = mock(UriInfo.class);

        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getPath()).thenReturn("/investimentos");
        when(requestContext.getProperty("tempoInicio")).thenReturn(null);

        // Act
        filter.filter(requestContext, responseContext);

        // Assert
        verify(telemetriaService, never()).registrarTelemetria(anyString(), anyLong());
    }

    @Test
    void testExtrairNomeEndpoint() {
        // Test various paths
        assertEquals("investimentos", filter.extrairNomeEndpoint("/investimentos"));
        assertEquals("investimentos", filter.extrairNomeEndpoint("investimentos/123"));
        assertEquals("simulacoes/por-produto-dia", filter.extrairNomeEndpoint("simulacoes/por-produto-dia"));
        assertEquals("auth", filter.extrairNomeEndpoint("/auth/login"));
        assertEquals("perfil-risco", filter.extrairNomeEndpoint("perfil-risco/456"));
    }
}
