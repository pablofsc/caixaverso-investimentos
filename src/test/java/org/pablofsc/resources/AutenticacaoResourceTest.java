package org.pablofsc.resources;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.service.AutenticacaoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@QuarkusTest
class AutenticacaoResourceTest {

    @Inject
    AutenticacaoResource resource;

    @Mock
    AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource.autenticacaoService = autenticacaoService;
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest request = new LoginRequest("user", "pass");
        LoginResponse expectedResponse = new LoginResponse("token123", "user@example.com", "User Name");
        when(autenticacaoService.autenticar(any(LoginRequest.class))).thenReturn(expectedResponse);

        // Act
        Response response = resource.login(request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
    }

    @Test
    void testLoginFailure() {
        // Arrange
        LoginRequest request = new LoginRequest("user", "wrongpass");
        when(autenticacaoService.autenticar(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        Response response = resource.login(request);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Credenciais inválidas", ((ErrorResponse) response.getEntity()).getMensagem());
    }

    @Test
    void testRegistrarSuccess() {
        // Arrange
        LoginRequest request = new LoginRequest("newuser", "pass");

        // Act
        Response response = resource.registrar(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof AutenticacaoResource.MessageResponse);
        assertEquals("Usuário registrado com sucesso", ((AutenticacaoResource.MessageResponse) response.getEntity()).mensagem());
    }

    @Test
    void testRegistrarFailure() {
        // Arrange
        LoginRequest request = new LoginRequest("existinguser", "pass");
        doThrow(new RuntimeException("User already exists")).when(autenticacaoService).registrar(any(LoginRequest.class));

        // Act
        Response response = resource.registrar(request);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErrorResponse);
        assertEquals("Erro ao registrar usuário", ((ErrorResponse) response.getEntity()).getMensagem());
    }
}
