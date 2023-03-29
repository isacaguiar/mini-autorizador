package br.com.isac.domain.exception;

public class InvalidCardFormatNumberException extends RuntimeException {

  private String cardNumber;

  public InvalidCardFormatNumberException(String cardNumber) {
    super();
    this.cardNumber = cardNumber;
  }
}
