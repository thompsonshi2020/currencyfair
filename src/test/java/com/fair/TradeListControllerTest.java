package com.fair;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class TradeListControllerTest {
    
    private final String URL = "http://localhost:8080/index.html";

    @Test
	public void loadData() throws Exception {

		System.out.println("TradeListControllerTest.loadData() - Start");

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());

        System.out.println("TradeListControllerTest.loadData() - Exit");
	}    
}