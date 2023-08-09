package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
    public void oneEqualOne() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    public void oneEqualTwo() {
        Assertions.assertNotEquals(1, 2);
    }

}
