package br.com.isac.adapter.redis;

import br.com.isac.domain.port.RedisPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisAdapter implements RedisPort {

  @Autowired
  RedisRepository redisRepository;

  @Override
  public String block(String key) {
    return redisRepository.set(key);
  }

  @Override
  public String get(String key) {
    return redisRepository.get(key);
  }

  @Override
  public void unlock(String key) {
    redisRepository.delete(key);
  }
}
