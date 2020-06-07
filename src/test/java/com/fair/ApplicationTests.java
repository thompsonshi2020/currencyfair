package com.fair;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fair.model.TradeRec;
import com.fair.controller.TradeController;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private TradeController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();

	}
}
