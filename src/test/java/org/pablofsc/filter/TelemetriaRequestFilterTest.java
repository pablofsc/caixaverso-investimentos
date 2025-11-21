package org.pablofsc.filter;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

@QuarkusTest
class TelemetriaRequestFilterTest {

    @Test
    void testFilterSetsTempoInicioProperty() throws IOException {
        // Arrange
        TelemetriaRequestFilter filter = new TelemetriaRequestFilter();
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);

        // Act
        filter.filter(requestContext);

        // Assert
        verify(requestContext).setProperty(eq("tempoInicio"), anyLong());
    }
}
