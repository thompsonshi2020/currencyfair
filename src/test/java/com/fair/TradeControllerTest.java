package com.fair;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import com.fair.model.TradeRec;
import com.fair.util.Common;
import com.fair.dal.TradeRepository;
import com.fair.controller.TradeController;
import com.fair.config.AppConfig;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    private final String URL = "http://localhost:8080/fair/trade";
    private final int RECORDS = 100;

    @MockBean
    private TradeRepository tradeRepository;
    
    @Autowired
    TradeController traderController;

    //@Autowired
    @MockBean
    AppConfig appConfig;

    @Autowired
    private MockMvc mockMvc;

    MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));

    @Test
    public void whenTradeControllerInjected_thenNotNull() throws Exception {
        assertThat(traderController).isNotNull();
    }

    @Test
    public void whenPostRequestToTrade_thenCorrectResponse() throws Exception {

        System.out.println("postTrade() - Start");

        RestTemplate restTemplate = new RestTemplate();
        List<TradeRec> tradeData = Common.data(RECORDS);

        long startTime = System.currentTimeMillis();

        for (TradeRec tradeEntity : tradeData) {
            ResponseEntity <String> response = restTemplate.postForEntity(URL, tradeEntity, String.class);
            Assert.assertEquals(200, response.getStatusCodeValue());
        }

        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("postTrade() - Spent " + runTime + "ms to process " + RECORDS + " records");
    }

    @Test
    public void whenPostRequestToTrade_thenMissingUserIdResponse() throws Exception {
        
        String trade = "{\"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}" ;
        
        mockMvc.perform(MockMvcRequestBuilders.post("/fair/trade")
                .content(trade)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("userId is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }    

    @Test
    public void whenPostRequestToTrade_thenMissingParamResponse() throws Exception {
        
        String trade = "{}" ;

        mockMvc.perform(MockMvcRequestBuilders.post("/fair/trade")
                .content(trade)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("userId is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().string(containsString("currencyFrom is mandatory")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("currencyTo is mandatory")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("amountSell is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().string(containsString("amountBuy is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().string(containsString("rate is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().string(containsString("originatingCountry is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().string(containsString("timePlaced is mandatory")))    
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    
        }       
    
}