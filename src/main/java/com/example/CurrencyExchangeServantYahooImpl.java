/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 Proyectos y Sistemas de Mantenimiento S.L. (eProsima)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file CurrencyExchangeServantYahooImpl.java
 * This file contains the servant implementation.
 *
 */

package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//for http request
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Class where the actual implementation of the procedures is located.
 *
 * @author Christof Marti.
 *
 */
class CurrencyExchangeServantYahooImpl extends CurrencyExchangeServant {

    /*
     * !
     * 
     * @brief This method implements the proxy part of the protocol for the
     * operation lookup. It has to be implemented by the child classes.
     */
    
    private static ArrayList<String> supportedExchangeRates = new ArrayList<String>() {{
        add("EURCHF");
        add("EURUSD");
        add("CHFEUR");
        add("CHFUSD");
        add("USDEUR");
        add("USDCHF");
    }};
    
    private static Map<String, ExchangeRate> exchangeRateMap = new HashMap<String, ExchangeRate>();
    
    private static ExchangeRate createExchange(String from, String to, float rate, float ask, float bid, long timestamp) {
        ExchangeRate exchange = new ExchangeRate();
        exchange.setId(from+to);
        exchange.setFrom(from);
        exchange.setTo(to);
        exchange.setRate(rate);
        exchange.setAsk(ask);
        exchange.setBid(bid);
        exchange.setTimestamp(timestamp);
        return exchange;
    }
        
    private static String getExchangeRate(String currencies) throws Exception{
        
        String url = "http://download.finance.yahoo.com/d/quotes.csv?s="+ currencies + "=X&f=snl1d1t1ab&e=.csv";
         
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
        con.setRequestMethod("GET");
  
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();
        String httpResponse = response.toString();
        
        return httpResponse;
    }
    
    public ExchangeRate lookup(String fromCurrency, String toCurrency)
    {
        String currencies = fromCurrency.toUpperCase() + toCurrency.toUpperCase();
        
        ExchangeRate response = null;
        
        if(exchangeRateMap.containsKey(currencies)) {
            response = exchangeRateMap.get(currencies);
        }
        else if (supportedExchangeRates.contains(currencies)) {
            try {
                String httpExchangeRateResponse = getExchangeRate(currencies);
   
                // Response String will have format: "EURCHF=X","EUR to CHF",1.0543,"2/6/2015","5:01am",1.0547,1.0539
                String[] httpResponseParts = httpExchangeRateResponse.split(",");
                
                response = createExchange(  fromCurrency.toUpperCase(), 
                                            toCurrency.toUpperCase(), 
                                            Float.parseFloat(httpResponseParts[2]), // Rate
                                            Float.parseFloat(httpResponseParts[5]), // Ask
                                            Float.parseFloat(httpResponseParts[6]), // Bid
                                            new Date().getTime()
                                            );
                
                exchangeRateMap.put(currencies, response);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Return "Default" ExchangeRate
            response = createExchange("XXX", "XXX", 0f, 0f, 0f, new Date().getTime());
        }

        return response;
    }

    /*
     * !
     * 
     * @brief This method implements the proxy part of the protocol for the
     * operation convert. It has to be implemented by the child classes.
     */
    public float convert(String fromCurrency, String toCurrency, float amount) {
        ExchangeRate exchange = this.lookup(fromCurrency, toCurrency);
        if (exchange != null) {
            if (amount == 0) amount = 1;
            return (float)(exchange.getRate() * amount);
        }
        return (float) 0.0;
    }
}