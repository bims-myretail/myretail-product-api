package com.myretail.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myretail.product.controller.MyretailProductApiController;

@SpringBootTest
class MyretailProductApiApplicationTests {
	
	@Autowired
	MyretailProductApiController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void testProductResponse() throws Exception {
		Object product = controller.findProductDetail("1");
		
		assertThat(product).isNotNull();
	}

}
