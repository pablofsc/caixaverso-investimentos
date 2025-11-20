package org.pablofsc.domain.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException(Long clienteId) {
    super("Cliente com ID " + clienteId + " não encontrado");
  }

  public ClienteNaoEncontradoException(String message) {
    super(message);
  }
}
