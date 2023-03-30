package br.com.isac.domain.port;

public interface RedisPort {

  String block(String key);

  String get(String key);

  void unlock(String key);
}
