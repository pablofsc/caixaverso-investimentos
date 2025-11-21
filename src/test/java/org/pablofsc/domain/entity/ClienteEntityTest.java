package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.FrequenciaMovimentacoesEnum;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.PreferenciaRentLiqEnum;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    String nome = "João Silva";
    Integer prazoMedioPreferido = 12;
    PreferenciaRentLiqEnum preferencia = PreferenciaRentLiqEnum.EQUILIBRIO;
    NivelRiscoEnum risco = NivelRiscoEnum.BAIXO;
    Double volume = 50000.0;
    FrequenciaMovimentacoesEnum frequencia = FrequenciaMovimentacoesEnum.MEDIA;

    // Act
    ClienteEntity cliente = ClienteEntity.builder()
        .id(id)
        .nome(nome)
        .prazoMedioPreferido(prazoMedioPreferido)
        .preferenciaRentLiq(preferencia)
        .riscoMaximoAceitavel(risco)
        .volumeTotalInvestido(volume)
        .frequenciaMovimentacoes(frequencia)
        .build();

    // Assert
    assertEquals(id, cliente.getId());
    assertEquals(nome, cliente.getNome());
    assertEquals(prazoMedioPreferido, cliente.getPrazoMedioPreferido());
    assertEquals(preferencia, cliente.getPreferenciaRentLiq());
    assertEquals(risco, cliente.getRiscoMaximoAceitavel());
    assertEquals(volume, cliente.getVolumeTotalInvestido());
    assertEquals(frequencia, cliente.getFrequenciaMovimentacoes());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    ClienteEntity cliente = new ClienteEntity();

    // Assert
    assertNull(cliente.getId());
    assertNull(cliente.getNome());
    assertNull(cliente.getPrazoMedioPreferido());
    assertNull(cliente.getPreferenciaRentLiq());
    assertNull(cliente.getRiscoMaximoAceitavel());
    assertNull(cliente.getVolumeTotalInvestido());
    assertNull(cliente.getFrequenciaMovimentacoes());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    String nome = "Maria Oliveira";
    Integer prazo = 24;
    PreferenciaRentLiqEnum pref = PreferenciaRentLiqEnum.RENTABILIDADE;
    NivelRiscoEnum risco = NivelRiscoEnum.ALTO;
    Double vol = 100000.0;
    FrequenciaMovimentacoesEnum freq = FrequenciaMovimentacoesEnum.ALTA;

    // Act
    ClienteEntity cliente = new ClienteEntity(id, nome, prazo, pref, risco, vol, freq);

    // Assert
    assertEquals(id, cliente.getId());
    assertEquals(nome, cliente.getNome());
    assertEquals(prazo, cliente.getPrazoMedioPreferido());
    assertEquals(pref, cliente.getPreferenciaRentLiq());
    assertEquals(risco, cliente.getRiscoMaximoAceitavel());
    assertEquals(vol, cliente.getVolumeTotalInvestido());
    assertEquals(freq, cliente.getFrequenciaMovimentacoes());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    ClienteEntity cliente = new ClienteEntity();

    // Act
    cliente.setId(3L);
    cliente.setNome("Carlos Santos");
    cliente.setPrazoMedioPreferido(6);
    cliente.setPreferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ);
    cliente.setRiscoMaximoAceitavel(NivelRiscoEnum.BAIXO);
    cliente.setVolumeTotalInvestido(25000.0);
    cliente.setFrequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA);

    // Assert
    assertEquals(3L, cliente.getId());
    assertEquals("Carlos Santos", cliente.getNome());
    assertEquals(6, cliente.getPrazoMedioPreferido());
    assertEquals(PreferenciaRentLiqEnum.LIQUIDEZ, cliente.getPreferenciaRentLiq());
    assertEquals(NivelRiscoEnum.BAIXO, cliente.getRiscoMaximoAceitavel());
    assertEquals(25000.0, cliente.getVolumeTotalInvestido());
    assertEquals(FrequenciaMovimentacoesEnum.BAIXA, cliente.getFrequenciaMovimentacoes());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ClienteEntity cliente1 = ClienteEntity.builder()
        .id(1L)
        .nome("Test")
        .prazoMedioPreferido(12)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(1000.0)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .build();

    ClienteEntity cliente2 = ClienteEntity.builder()
        .id(1L)
        .nome("Test")
        .prazoMedioPreferido(12)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(1000.0)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .build();

    ClienteEntity cliente3 = ClienteEntity.builder()
        .id(2L)
        .nome("Different")
        .build();

    // Assert
    assertEquals(cliente1, cliente2);
    assertEquals(cliente1.hashCode(), cliente2.hashCode());
    assertNotEquals(cliente1, cliente3);
    assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Test").build();

    // Act & Assert
    assertFalse(cliente.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Test").build();

    // Act & Assert
    assertFalse(cliente.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Test").build();

    // Act & Assert
    assertTrue(cliente.equals(cliente));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    ClienteEntity cliente1 = ClienteEntity.builder()
        .id(null)
        .nome(null)
        .prazoMedioPreferido(null)
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(null)
        .volumeTotalInvestido(null)
        .frequenciaMovimentacoes(null)
        .build();

    ClienteEntity cliente2 = ClienteEntity.builder()
        .id(null)
        .nome(null)
        .prazoMedioPreferido(null)
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(null)
        .volumeTotalInvestido(null)
        .frequenciaMovimentacoes(null)
        .build();

    ClienteEntity cliente3 = ClienteEntity.builder()
        .id(1L)
        .nome(null)
        .build();

    // Assert
    assertEquals(cliente1, cliente2);
    assertEquals(cliente1.hashCode(), cliente2.hashCode());
    assertNotEquals(cliente1, cliente3);
    assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("Test")
        .build();

    // Act
    String toString = cliente.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("ClienteEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("nome=Test"));
  }

  @Test
  void testWithNullValues() {
    // Act
    ClienteEntity cliente = ClienteEntity.builder()
        .id(null)
        .nome(null)
        .prazoMedioPreferido(null)
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(null)
        .volumeTotalInvestido(null)
        .frequenciaMovimentacoes(null)
        .build();

    // Assert
    assertNull(cliente.getId());
    assertNull(cliente.getNome());
    assertNull(cliente.getPrazoMedioPreferido());
    assertNull(cliente.getPreferenciaRentLiq());
    assertNull(cliente.getRiscoMaximoAceitavel());
    assertNull(cliente.getVolumeTotalInvestido());
    assertNull(cliente.getFrequenciaMovimentacoes());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    Double largeVolume = Double.MAX_VALUE;
    Integer largePrazo = Integer.MAX_VALUE;

    // Act
    ClienteEntity cliente = ClienteEntity.builder()
        .volumeTotalInvestido(largeVolume)
        .prazoMedioPreferido(largePrazo)
        .build();

    // Assert
    assertEquals(largeVolume, cliente.getVolumeTotalInvestido());
    assertEquals(largePrazo, cliente.getPrazoMedioPreferido());
  }
}
