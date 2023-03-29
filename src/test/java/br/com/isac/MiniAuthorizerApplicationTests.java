package br.com.isac;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiniAuthorizerApplicationTests {

	@Test
	void testMain() {
		MiniAuthorizerApplication.main(new String[] {});
		assertTrue(true);
	}

}
