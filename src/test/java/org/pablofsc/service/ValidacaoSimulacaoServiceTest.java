package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.exception.ParametroInvalidoException;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoSimulacaoServiceTest {

  private ValidacaoSimulacaoService validacaoSimulacaoService;

  @BeforeEach
  void setUp() {
    validacaoSimulacaoService = new ValidacaoSimulacaoService();
  }

  @Test
  void testValidarRequestValido() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 12, "CDB");

    // Act & Assert
    assertDoesNotThrow(() -> validacaoSimulacaoService.validar(request));
  }

  @Test
  void testValidarValorNull() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, null, 12, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarValorZero() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 0.0, 12, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarValorNegativo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, -100.0, 12, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarValorAbaixoMinimo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 50.0, 12, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor mínimo é 100.0", exception.getMessage());
  }

  @Test
  void testValidarValorAcimaMaximo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 2000000.0, 12, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor máximo é 1000000.0", exception.getMessage());
  }

  @Test
  void testValidarValorMinimo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 100.0, 12, "CDB");

    // Act & Assert
    assertDoesNotThrow(() -> validacaoSimulacaoService.validar(request));
  }

  @Test
  void testValidarValorMaximo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000000.0, 12, "CDB");

    // Act & Assert
    assertDoesNotThrow(() -> validacaoSimulacaoService.validar(request));
  }

  @Test
  void testValidarPrazoNull() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, null, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Prazo deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarPrazoZero() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 0, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Prazo deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarPrazoNegativo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, -1, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Prazo deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarPrazoAbaixoMinimo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 0, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Prazo deve ser maior que zero", exception.getMessage());
  }

  @Test
  void testValidarPrazoAcimaMaximo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 400, "CDB");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Prazo máximo é 360 meses", exception.getMessage());
  }

  @Test
  void testValidarPrazoMinimo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 1, "CDB");

    // Act & Assert
    assertDoesNotThrow(() -> validacaoSimulacaoService.validar(request));
  }

  @Test
  void testValidarPrazoMaximo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 360, "CDB");

    // Act & Assert
    assertDoesNotThrow(() -> validacaoSimulacaoService.validar(request));
  }

  @Test
  void testValidarTipoProdutoNull() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 12, null);

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Tipo de produto é obrigatório", exception.getMessage());
  }

  @Test
  void testValidarTipoProdutoVazio() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 12, "");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Tipo de produto é obrigatório", exception.getMessage());
  }

  @Test
  void testValidarTipoProdutoApenasEspacos() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 1000.0, 12, "   ");

    // Act & Assert
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Tipo de produto é obrigatório", exception.getMessage());
  }

  @Test
  void testValidarMultiplasValidacoes() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 0.0, 0, "");

    // Act & Assert - Deve falhar na primeira validação (valor)
    ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
      validacaoSimulacaoService.validar(request);
    });
    assertEquals("Valor deve ser maior que zero", exception.getMessage());
  }
}
