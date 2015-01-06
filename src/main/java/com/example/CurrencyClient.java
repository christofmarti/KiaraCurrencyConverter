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
 * @file CurrencyClient.java
 * This file contains the code to start the client.
 *
 */
 
 
package com.example;
import java.util.Date;

import org.fiware.kiara.client.Connection;
import org.fiware.kiara.Context;
import org.fiware.kiara.Kiara;
import org.fiware.kiara.serialization.Serializer;
import org.fiware.kiara.transport.Transport;

/**
 * Class that acts as the main client entry point. 
 *
 * @author Christof Marti.
 *
 */
public class CurrencyClient {
	public static void main (String [] args) throws Exception {
		System.out.println("CurrencyExchangeClient");
		
		java.lang.String from = "EUR";
		java.lang.String to = "CHF";
				
		ExchangeRate ret = null;
		
		Context context = Kiara.createContext();
		
        System.out.println("Get Exchange rate using FakeService");
		Transport transport = context.createTransport("tcp://127.0.0.1:8080");
		Serializer serializer = context.createSerializer("cdr");
		Connection connection = context.connect(transport, serializer);
		CurrencyExchange client = connection.getServiceProxy(CurrencyExchangeClient.class);		
		try {
			ret = client.lookup(from, to);
			printExchangeRate(ret);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			System.exit(-1);
		}
		System.out.println("Get Exchange rate using YahooService");
		Connection yahooCon = context.connect("tcp://127.0.0.1:9090/?serialization=cdr");
		CurrencyExchange yahooClient = yahooCon.getServiceProxy(CurrencyExchangeClient.class);
        try {
            ret = yahooClient.lookup(from, to);
            printExchangeRate(ret);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.exit(-1);
        }
		System.exit(0);
	}
	
	public static void printExchangeRate(ExchangeRate data) {
	    if (data==null) {
	        System.out.println("Exchange Rate not found");
	        return;
	    }
	    Date date = new Date(data.getTimestamp());
        System.out.printf("Exchange-Rate: %s to %s%n", data.getFrom(), data.getTo());
        System.out.printf("Date         : %td.%tm.%ty%n", date, date, date);
        System.out.printf("Exchange Rate: %.4f%n", data.getRate());
        System.out.printf("Bid Rate     : %.4f%n", data.getBid());
        System.out.printf("Ask Rate     : %.4f%n", data.getAsk());
	}
}
