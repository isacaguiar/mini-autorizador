package br.com.isac.domain.exception;

public class CardAlreadyExistsException extends RuntimeException {

    private final String cardNumber;

    public CardAlreadyExistsException(String cardNumber) {
        super();
        this.cardNumber = cardNumber;
    }
}
