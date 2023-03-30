package br.com.isac.adapter.controller.response;

public abstract class TransactionStatusResponse {
  public static final String OK = "OK";
  public static final String INSUFFICIENT_FUNDS = "SALDO_INSUFICIENTE";
  public static final String INVALID_PASSWORD = "SENHA_INVALIDA";
  public static final String CARD_NOT_FOUND = "CARTAO_INEXISTENTE";
  public static final String LOCKED_TRANSACTION = "TRANSACAO_BLOQUEADA";

}
