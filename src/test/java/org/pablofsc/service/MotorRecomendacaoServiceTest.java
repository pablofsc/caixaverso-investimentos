package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.FrequenciaMovimentacoesEnum;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.PreferenciaRentLiqEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.repository.ProdutoRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MotorRecomendacaoServiceTest {

  private ProdutoRepository produtoRepository;
  private PerfilRiscoService perfilRiscoService;
  private MotorRecomendacaoService motorRecomendacaoService;

  @BeforeEach
  void setUp() {
    produtoRepository = mock(ProdutoRepository.class);
    perfilRiscoService = mock(PerfilRiscoService.class);
    motorRecomendacaoService = new MotorRecomendacaoService(produtoRepository, perfilRiscoService);
  }

  @Test
  void testObterProdutosPorPerfilConservador() {
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
        .nome("CDB Agressivo")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.15)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosPorPerfil(perfil);

    // Assert
    assertEquals(1, result.size());
    assertEquals(produto1.getId(), result.get(0).getId());
    verify(produtoRepository).listAll();
  }

  @Test
  void testObterProdutosPorPerfilModerado() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.MODERADO;

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Moderado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("CDB Muito Alto")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.20)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosPorPerfil(perfil);

    // Assert
    assertEquals(1, result.size());
    assertEquals(produto1.getId(), result.get(0).getId());
    verify(produtoRepository).listAll();
  }

  @Test
  void testObterProdutosPorPerfilAgressivo() {
    // Arrange
    PerfilCliente perfil = PerfilCliente.AGRESSIVO;

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Baixo")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("CDB Muito Alto")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.20)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosPorPerfil(perfil);

    // Assert
    assertEquals(2, result.size());
    verify(produtoRepository).listAll();
  }

  @Test
  void testObterProdutosCompativeisClienteConservador() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Conservador")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Baixo")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("Fundo Alto")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.15)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.CONSERVADOR);
    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosCompativeis(cliente, "CDB");

    // Assert
    assertEquals(1, result.size());
    assertEquals(produto1.getId(), result.get(0).getId());
    verify(perfilRiscoService).classificarPerfil(cliente);
    verify(produtoRepository).listAll();
  }

  @Test
  void testObterProdutosCompativeisTipoProdutoNaoEncontrado() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.CONSERVADOR);
    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1));

    // Act & Assert
    ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, () -> {
      motorRecomendacaoService.obterProdutosCompativeis(cliente, "POUPANCA");
    });
    assertEquals("Produto do tipo 'POUPANCA' não encontrado", exception.getMessage());
  }

  @Test
  void testObterProdutosCompativeisFiltragemPorRisco() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Moderado")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO) // Cliente aceita apenas BAIXO
        .volumeTotalInvestido(50000.0)
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Baixo")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("CDB Alto")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.MODERADO);
    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosCompativeis(cliente, "CDB");

    // Assert
    assertEquals(1, result.size());
    assertEquals(produto1.getId(), result.get(0).getId());
  }

  @Test
  void testObterProdutosCompativeisSemProdutosFiltradosPorRisco() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Conservador")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_BAIXO) // Cliente aceita apenas MUITO_BAIXO
        .volumeTotalInvestido(5000.0)
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Baixo")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.CONSERVADOR);
    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1));

    // Act
    List<ProdutoEntity> result = motorRecomendacaoService.obterProdutosCompativeis(cliente, "CDB");

    // Assert - Deve retornar o produto mesmo que risco seja maior, pois não há
    // alternativas
    assertEquals(1, result.size());
    assertEquals(produto1.getId(), result.get(0).getId());
  }

  @Test
  void testRecomendarProduto() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB 1")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.05) // Muito baixa rentabilidade
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("CDB 2")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.15) // Alta rentabilidade
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.CONSERVADOR);
    when(produtoRepository.listAll()).thenReturn(Arrays.asList(produto1, produto2));

    // Act
    ProdutoEntity result = motorRecomendacaoService.recomendarProduto(cliente, "CDB", 12);

    // Assert
    assertNotNull(result);
    assertTrue(result.getId().equals(1L) || result.getId().equals(2L)); // Deve retornar um dos produtos
  }

  @Test
  void testRecomendarProdutoListaVazia() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    when(perfilRiscoService.classificarPerfil(cliente)).thenReturn(PerfilCliente.CONSERVADOR);
    when(produtoRepository.listAll()).thenReturn(Collections.emptyList());

    // Act & Assert
    ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, () -> {
      motorRecomendacaoService.recomendarProduto(cliente, "CDB", 12);
    });
    assertEquals("Produto do tipo 'CDB' não encontrado", exception.getMessage());
  }
}
