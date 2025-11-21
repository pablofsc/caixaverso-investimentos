package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.model.ProdutoRecomendado;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRecomendadoServiceTest {

  private MotorRecomendacaoService motorRecomendacaoService;
  private ProdutoRecomendadoService produtoRecomendadoService;

  @BeforeEach
  void setUp() {
    motorRecomendacaoService = mock(MotorRecomendacaoService.class);
    produtoRecomendadoService = new ProdutoRecomendadoService(motorRecomendacaoService);
  }

  @Test
  void testObterProdutosRecomendadosConservador() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.CONSERVADOR;

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Conservador")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("CDB Moderado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    when(motorRecomendacaoService.obterProdutosPorPerfil(perfil))
        .thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoRecomendado> result = produtoRecomendadoService.obterProdutosRecomendados(perfil);

    // Assert
    assertEquals(2, result.size());

    ProdutoRecomendado primeiro = result.get(0);
    assertEquals(1L, primeiro.getId());
    assertEquals("CDB Conservador", primeiro.getNome());
    assertEquals(TipoProdutoEnum.CDB, primeiro.getTipo());
    assertEquals(0.08, primeiro.getRentabilidade());
    assertEquals(NivelRiscoEnum.BAIXO, primeiro.getRisco());

    ProdutoRecomendado segundo = result.get(1);
    assertEquals(2L, segundo.getId());
    assertEquals("CDB Moderado", segundo.getNome());
    assertEquals(TipoProdutoEnum.CDB, segundo.getTipo());
    assertEquals(0.10, segundo.getRentabilidade());
    assertEquals(NivelRiscoEnum.ALTO, segundo.getRisco());

    verify(motorRecomendacaoService).obterProdutosPorPerfil(perfil);
  }

  @Test
  void testObterProdutosRecomendadosModerado() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.MODERADO;

    ProdutoEntity produto = ProdutoEntity.builder()
        .id(3L)
        .nome("Fundo Multimercado")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    when(motorRecomendacaoService.obterProdutosPorPerfil(perfil))
        .thenReturn(Collections.singletonList(produto));

    // Act
    List<ProdutoRecomendado> result = produtoRecomendadoService.obterProdutosRecomendados(perfil);

    // Assert
    assertEquals(1, result.size());

    ProdutoRecomendado recomendado = result.get(0);
    assertEquals(3L, recomendado.getId());
    assertEquals("Fundo Multimercado", recomendado.getNome());
    assertEquals(TipoProdutoEnum.FUNDO, recomendado.getTipo());
    assertEquals(0.12, recomendado.getRentabilidade());
    assertEquals(NivelRiscoEnum.ALTO, recomendado.getRisco());

    verify(motorRecomendacaoService).obterProdutosPorPerfil(perfil);
  }

  @Test
  void testObterProdutosRecomendadosAgressivo() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.AGRESSIVO;

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(4L)
        .nome("Ações Blue Chip")
        .tipo(TipoProdutoEnum.RENDA_FIXA)
        .rentabilidade(0.18)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(5L)
        .nome("Fundo de Ações")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.20)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    when(motorRecomendacaoService.obterProdutosPorPerfil(perfil))
        .thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoRecomendado> result = produtoRecomendadoService.obterProdutosRecomendados(perfil);

    // Assert
    assertEquals(2, result.size());

    ProdutoRecomendado primeiro = result.get(0);
    assertEquals(4L, primeiro.getId());
    assertEquals("Ações Blue Chip", primeiro.getNome());
    assertEquals(TipoProdutoEnum.RENDA_FIXA, primeiro.getTipo());
    assertEquals(0.18, primeiro.getRentabilidade());
    assertEquals(NivelRiscoEnum.ALTO, primeiro.getRisco());

    ProdutoRecomendado segundo = result.get(1);
    assertEquals(5L, segundo.getId());
    assertEquals("Fundo de Ações", segundo.getNome());
    assertEquals(TipoProdutoEnum.FUNDO, segundo.getTipo());
    assertEquals(0.20, segundo.getRentabilidade());
    assertEquals(NivelRiscoEnum.MUITO_ALTO, segundo.getRisco());

    verify(motorRecomendacaoService).obterProdutosPorPerfil(perfil);
  }

  @Test
  void testObterProdutosRecomendadosListaVazia() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.CONSERVADOR;

    when(motorRecomendacaoService.obterProdutosPorPerfil(perfil))
        .thenReturn(Collections.emptyList());

    // Act
    List<ProdutoRecomendado> result = produtoRecomendadoService.obterProdutosRecomendados(perfil);

    // Assert
    assertTrue(result.isEmpty());
    verify(motorRecomendacaoService).obterProdutosPorPerfil(perfil);
  }

  @Test
  void testObterProdutosRecomendadosNullPerfil() {
    // Arrange
    when(motorRecomendacaoService.obterProdutosPorPerfil(null))
        .thenThrow(new IllegalArgumentException("Perfil não pode ser nulo"));

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> produtoRecomendadoService.obterProdutosRecomendados(null));

    verify(motorRecomendacaoService).obterProdutosPorPerfil(null);
  }
}
