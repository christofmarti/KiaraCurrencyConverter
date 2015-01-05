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
		
		java.lang.String from = "";
		java.lang.String to = "";
				
		ExchangeRate ret = null;
		
		Context context = Kiara.createContext();
		
		Transport transport = context.createTransport("tcp://127.0.0.1:9090");
		Serializer serializer = context.createSerializer("cdr");
		
		Connection connection = context.connect(transport, serializer);
		CurrencyExchange client = connection.getServiceProxy(CurrencyExchangeClient.class);
		
		try {
			ret = client.lookup(from, to);
				
			// Print or check the operation result here
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return;
		}
	}
}
