package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.response.PerfilRiscoResponse;
import org.pablofsc.repository.ClienteRepository;

@ApplicationScoped
public class PerfilRiscoService {

  private static final int NIVEL_MUITO_BAIXO = 0;
  private static final int NIVEL_BAIXO = 1;
  private static final int NIVEL_ALTO = 2;
  private static final int NIVEL_MUITO_ALTO = 3;

  @Inject
  ClienteRepository clienteRepository;

  public PerfilRiscoResponse obterPerfilRisco(Long clienteId) {
    ClienteEntity cliente = clienteRepository.findById(clienteId);
    if (cliente == null) {
      return null;
    }

    PerfilCliente perfil = classificarPerfil(cliente);
    int pontuacao = calcularPontuacao(cliente);

    return new PerfilRiscoResponse(
        clienteId,
        perfil.name(),
        pontuacao,
        perfil.getDescricao());
  }

  private Integer calcularPontuacao(ClienteEntity cliente) {
    int pontos = 0;

    // Frequência: BAIXA=10, MEDIA=20, ALTA=30 (até 30)
    pontos += switch (cliente.getFrequenciaMovimentacoes()) {
      case "ALTA" -> 30;
      case "MEDIA" -> 20;
      case "BAIXA" -> 10;
      default -> 0;
    };

    // Preferência: LIQUIDEZ=10, EQUILIBRIO=15, RENTABILIDADE=25 (até 25)
    pontos += switch (cliente.getPreferenciaRentLiq()) {
      case "RENTABILIDADE" -> 25;
      case "EQUILIBRIO" -> 15;
      case "LIQUIDEZ" -> 10;
      default -> 0;
    };

    // Volume: 0-10k=5, 10k-100k=10, 100k-500k=15, 500k+=20 (até 20)
    if (cliente.getVolumeTotalInvestido() != null) {
      double volume = cliente.getVolumeTotalInvestido();
      pontos += volume >= 500000 ? 20
          : volume >= 100000 ? 15
              : volume >= 10000 ? 10
                  : 5;
    }

    // Risco: Muito Baixo=10, Baixo=15, Alto=20, Muito Alto=25 (até 25)
    pontos += switch (getNivelRisco(cliente.getRiscoMaximoAceitavel())) {
      case NIVEL_MUITO_BAIXO -> 10;
      case NIVEL_BAIXO -> 15;
      case NIVEL_ALTO -> 20;
      case NIVEL_MUITO_ALTO -> 25;
      default -> 0;
    };

    return pontos;
  }

  /**
   * Classifica o cliente em uma das três categorias baseado em seu perfil
   * Conservador: baixa movimentação, foco em liquidez
   * Moderado: equilíbrio entre liquidez e rentabilidade
   * Agressivo: busca por alta rentabilidade, maior risco
   */
  public PerfilCliente classificarPerfil(ClienteEntity cliente) {
    int score = 0;

    // Frequência: BAIXA=0, MEDIA=1, ALTA=2
    if ("ALTA".equals(cliente.getFrequenciaMovimentacoes())) {
      score += 2;
    } else if ("MEDIA".equals(cliente.getFrequenciaMovimentacoes())) {
      score += 1;
    }

    // Preferência: LIQUIDEZ=0, EQUILIBRIO=1, RENTABILIDADE=2
    if ("RENTABILIDADE".equals(cliente.getPreferenciaRentLiq())) {
      score += 2;
    } else if ("EQUILIBRIO".equals(cliente.getPreferenciaRentLiq())) {
      score += 1;
    }

    // Volume: 0-10k=0, 10k-100k=1, 100k-500k=1.5, 500k+=2
    if (cliente.getVolumeTotalInvestido() != null) {
      if (cliente.getVolumeTotalInvestido() >= 500000) {
        score += 2;
      } else if (cliente.getVolumeTotalInvestido() >= 100000) {
        score += 1.5;
      } else if (cliente.getVolumeTotalInvestido() >= 10000) {
        score += 1;
      }
    }

    // Risco: Muito Baixo/Baixo=0, Alto=1, Muito Alto=2
    int nivelRisco = getNivelRisco(cliente.getRiscoMaximoAceitavel());
    if (nivelRisco >= NIVEL_MUITO_ALTO) {
      score += 2;
    } else if (nivelRisco >= NIVEL_ALTO) {
      score += 1;
    }

    // Classificação final baseada no score total
    if (score >= 5) {
      return PerfilCliente.AGRESSIVO;
    } else if (score >= 2) {
      return PerfilCliente.MODERADO;
    } else {
      return PerfilCliente.CONSERVADOR;
    }
  }

  private Integer getNivelRisco(String risco) {
    return switch (risco) {
      case "Muito Baixo" -> NIVEL_MUITO_BAIXO;
      case "Baixo" -> NIVEL_BAIXO;
      case "Alto" -> NIVEL_ALTO;
      case "Muito Alto" -> NIVEL_MUITO_ALTO;
      default -> NIVEL_BAIXO;
    };
  }
}
