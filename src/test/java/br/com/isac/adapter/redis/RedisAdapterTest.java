package br.com.isac.adapter.redis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RedisAdapter.class})
class RedisAdapterTest {
  @Autowired
  RedisAdapter redisAdapter;

  @MockBean
  RedisRepository redisRepository;

  @MockBean
  RedisTemplate<String, String> redisTemplate;

  @Test
  void block() {
    String key = "598745154569548743";

    when(redisRepository.set(key)).thenReturn(key);

    redisAdapter.block(key);

    verify(redisRepository).set(key);
  }

  @Test
  void get() {
    String key = "59874515456954874";

    when(redisRepository.get(key)).thenReturn(key);

    redisAdapter.get(key);

    verify(redisRepository).get(key);
  }

  @Test
  void unlock() {
    String key = "59874515456954875";

    when(redisTemplate.boundValueOps(any())).thenReturn(any());

    redisAdapter.unlock(key);

    verify(redisRepository).delete(key);
  }

}