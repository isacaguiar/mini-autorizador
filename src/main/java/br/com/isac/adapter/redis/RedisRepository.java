package br.com.isac.adapter.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

  private static final Logger logger = LogManager.getLogger(RedisRepository.class);

  @Autowired
  RedisTemplate<String, String> template;

  public String get(String key) {
    logger.info("Get key Redis for card {}", key);
    String definitiveKey = buildKey(key);
    return template.boundValueOps(definitiveKey).get();
  }

  public String set(String key) {
    logger.info("Set key Redis for card {}",key);
    String definitiveKey = buildKey(key);
    template.boundValueOps(definitiveKey).set(key);
    return key;
  }

  public void delete(String key) {
    logger.info("Remove key Redis for card {}", key);
    String definitiveKey = buildKey(key);
    template.boundValueOps(definitiveKey).getAndDelete();
  }

  private String buildKey(String key) {
    return "mini-authorizer:transaction:".concat(key);
  }

}
