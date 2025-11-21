package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.model.ProdutoRecomendado;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.service.ProdutoRecomendadoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class ProdutoRecomendadoResourceTest {

    @Inject
    ProdutoRecomendadoResource resource;

    @Mock
    ProdutoRecomendadoService produtoRecomendadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.service = produtoRecomendadoService;
    }

    @Test
    void testObterProdutosRecomendadosSuccess() {
        // Arrange
        String perfil = "conservador";
        List<ProdutoRecomendado> expectedProdutos = List.of(new ProdutoRecomendado());
        when(produtoRecomendadoService.obterProdutosRecomendados(any())).thenReturn(expectedProdutos);

        // Act
        Response response = resource.obterProdutosRecomendados(perfil);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedProdutos, response.getEntity());
    }

    @Test
    void testObterProdutosRecomendadosInvalidPerfil() {
        // Arrange
        String perfil = "invalid";

        // Act
        Response response = resource.obterProdutosRecomendados(perfil);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Perfil inválido. Use: CONSERVADOR, MODERADO ou AGRESSIVO",
                ((ErrorResponse) response.getEntity()).getMensagem());
    }
}
