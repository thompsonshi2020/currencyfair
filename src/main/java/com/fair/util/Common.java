package com.fair.util;

import java.util.List;
import java.util.Random;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Date;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fair.model.TradeRec;

public class Common {

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static List<TradeRec> data(int dataSize) {

        String[] currencyFrom = {
                "AED", "AUD", "CAD", "EUR", "EGP", "GBP", "IDR", "INR", "USD", "JPY",
                "CNY", "CHF", "SGD", "MYR", "DKK", "SAR", "RUB", "QAR", "TRY", "VEF"};
        String[] countries = {
                "NO", "AU", "NL", "US", "NZ", "CA", "IE", "LI", "DE", "SE",
                "CH", "JP", "HK", "IS", "KR", "DK", "IL", "BE", "AT", "FR",
                "SI", "FI", "ES", "IT", "LU", "SG", "CZ", "AE", "GR", "GB",
                "CY", "AD", "BN", "EE", "SK", "MT", "QA", "HU", "PL", "LT",
                "PT", "BH", "LV", "CL", "AR", "HR", "BB", "UY", "PW", "RO",
        };

        List<TradeRec> tradeData = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {

            // generate random numbers to get currencies
            Random random = new Random();
            int fci = random.nextInt(currencyFrom.length);
            int tci = random.nextInt(currencyFrom.length);
            if (tci == fci) {
                tci = tci == currencyFrom.length - 1 ? tci - 1 : tci + 1;
            }

            // generate random number to get country
            random = new Random();
            int ci = random.nextInt(countries.length);

            // generate random amounts & rate
            random = new Random();
            int sellAmount = random.nextInt(Integer.MAX_VALUE);
            int buyAmount = random.nextInt(Integer.MAX_VALUE);
            BigDecimal rate = new BigDecimal(Math.random());
            rate = rate.setScale(6, RoundingMode.HALF_UP);

            // generate random 6 digits user id
            random = new Random();
            StringBuilder userId = new StringBuilder(String.valueOf(100000 + random.nextInt(900000)));

            TradeRec tradeRec = new TradeRec();
            tradeRec.setUserId(userId.toString());
            tradeRec.setAmountBuy(new BigDecimal(String.valueOf(buyAmount)));
            tradeRec.setAmountSell(new BigDecimal(String.valueOf(sellAmount)));
            tradeRec.setRate(rate);
            tradeRec.setCurrencyFrom(currencyFrom[fci]);
            tradeRec.setCurrencyTo(currencyFrom[tci]);
            tradeRec.setOriginatingCountry(countries[ci]);
            tradeRec.setTimePlaced(new Date());
	        tradeRec.setCurrencyPair("");

            tradeData.add(tradeRec);
        }

        return tradeData;
    }    

}