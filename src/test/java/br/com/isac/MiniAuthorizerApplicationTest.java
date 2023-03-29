package br.com.isac;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MiniAuthorizerApplicationTest {

  @Test
  void testConfigure() {
    String[] args = {""};
    Runnable runnable = () -> MiniAuthorizerApplication.main(args);
    assertNotNull(runnable);
  }

}