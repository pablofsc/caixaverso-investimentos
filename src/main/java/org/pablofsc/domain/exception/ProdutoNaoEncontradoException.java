package org.pablofsc.domain.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
  public ProdutoNaoEncontradoException(String tipo) {
    super("Produto do tipo '" + tipo + "' não encontrado");
  }

  public ProdutoNaoEncontradoException(String message, Throwable cause) {
    super(message, cause);
  }
}
