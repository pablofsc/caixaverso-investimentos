package org.pablofsc.config;

import io.quarkus.runtime.StartupEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.InvestimentoEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.InvestimentoRepository;
import org.pablofsc.repository.ProdutoRepository;
import org.pablofsc.repository.UsuarioRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private InvestimentoRepository investimentoRepository;

  @Mock
  private UsuarioRepository usuarioRepository;

  @InjectMocks
  private DataInitializer dataInitializer;

  @BeforeEach
  void setUp() {
    // Setup mocks before each test
  }

  @Test
  void testOnStart_WhenNoDataExists() {
    // Arrange
    when(produtoRepository.count()).thenReturn(0L);
    when(clienteRepository.count()).thenReturn(0L);
    when(investimentoRepository.count()).thenReturn(0L);
    when(usuarioRepository.count()).thenReturn(0L);

    // Mock findById calls for investimentos
    ProdutoEntity produto1 = new ProdutoEntity(101L, "CDB Test", TipoProdutoEnum.CDB, 0.12, NivelRiscoEnum.BAIXO);
    ProdutoEntity produto2 = new ProdutoEntity(106L, "Fundo Test", TipoProdutoEnum.FUNDO, 0.18, NivelRiscoEnum.ALTO);
    ProdutoEntity produto3 = new ProdutoEntity(111L, "Tesouro Test", TipoProdutoEnum.RENDA_FIXA, 0.08, NivelRiscoEnum.MUITO_BAIXO);

    ClienteEntity maria = ClienteEntity.builder().id(2L).nome("Maria Santos").build();
    ClienteEntity carlos = ClienteEntity.builder().id(3L).nome("Carlos Oliveira").build();
    ClienteEntity pablo = ClienteEntity.builder().id(123L).nome("Pablo Felipe").build();

    when(clienteRepository.findById(2L)).thenReturn(maria);
    when(clienteRepository.findById(3L)).thenReturn(carlos);
    when(clienteRepository.findById(123L)).thenReturn(pablo);

    when(produtoRepository.findById(101L)).thenReturn(produto1);
    when(produtoRepository.findById(106L)).thenReturn(produto2);
    when(produtoRepository.findById(111L)).thenReturn(produto3);

    StartupEvent startupEvent = new StartupEvent();

    // Act
    dataInitializer.onStart(startupEvent);

    // Assert
    verify(usuarioRepository).count();
    verify(produtoRepository).count();
    verify(clienteRepository).count();
    verify(investimentoRepository).count();

    // Verify admin user was created
    verify(usuarioRepository).persist(any(UsuarioEntity.class));

    // Verify products were created (15 products)
    verify(produtoRepository, times(15)).persist(any(ProdutoEntity.class));

    // Verify clients were created (5 clients)
    verify(clienteRepository, times(5)).persist(any(ClienteEntity.class));

    // Verify investments were created (5 + 10 + 20 = 35 investments)
    verify(investimentoRepository, times(35)).persist(any(InvestimentoEntity.class));
  }

  @Test
  void testOnStart_WhenDataAlreadyExists() {
    // Arrange
    when(produtoRepository.count()).thenReturn(1L);
    when(clienteRepository.count()).thenReturn(1L);
    when(investimentoRepository.count()).thenReturn(1L);
    when(usuarioRepository.count()).thenReturn(1L);

    StartupEvent startupEvent = new StartupEvent();

    // Act
    dataInitializer.onStart(startupEvent);

    // Assert
    verify(usuarioRepository).count();
    verify(produtoRepository).count();
    verify(clienteRepository).count();
    verify(investimentoRepository).count();

    // Verify no data was created since it already exists
    verify(usuarioRepository, never()).persist(any(UsuarioEntity.class));
    verify(produtoRepository, never()).persist(any(ProdutoEntity.class));
    verify(clienteRepository, never()).persist(any(ClienteEntity.class));
    verify(investimentoRepository, never()).persist(any(InvestimentoEntity.class));
  }

  @Test
  void testOnStart_PartialDataExists() {
    // Arrange - some data exists, some doesn't
    when(produtoRepository.count()).thenReturn(1L); // Products exist
    when(clienteRepository.count()).thenReturn(0L); // Clients don't exist
    when(investimentoRepository.count()).thenReturn(0L); // Investments don't exist
    when(usuarioRepository.count()).thenReturn(1L); // User exists

    StartupEvent startupEvent = new StartupEvent();

    // Act
    dataInitializer.onStart(startupEvent);

    // Assert
    verify(usuarioRepository).count();
    verify(produtoRepository).count();
    verify(clienteRepository).count();
    verify(investimentoRepository).count();

    // Verify only missing data was created
    verify(usuarioRepository, never()).persist(any(UsuarioEntity.class)); // User exists
    verify(produtoRepository, never()).persist(any(ProdutoEntity.class)); // Products exist
    verify(clienteRepository, times(5)).persist(any(ClienteEntity.class)); // Clients created
    verify(investimentoRepository, times(35)).persist(any(InvestimentoEntity.class)); // Investments created
  }
}
